// Copyright 2021-2024 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot.common.subsystems.drive;

import static edu.wpi.first.units.Units.*;

import java.util.Arrays;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.common.util.SparkUtil;
import org.ironmaple.simulation.drivesims.SwerveModuleSimulation;
import org.ironmaple.simulation.motorsims.SimulatedMotorController;

import frc.robot.GlobalConstants.driveConstants;

/** Physics sim implementation of module IO. */
public class ModuleIOSim implements ModuleIO {
    private final SwerveModuleSimulation moduleSimulation;
    private final SimulatedMotorController.GenericMotorController driveMotor;
    private final SimulatedMotorController.GenericMotorController turnMotor;

    private boolean driveClosedLoop = false;
    private boolean turnClosedLoop = false;
    private final PIDController driveController = new PIDController(driveConstants.driveSimP, 0, driveConstants.driveSimD);
    private final PIDController turnController = new PIDController(driveConstants.turnSimP, 0, driveConstants.turnSimD);
    private double driveFFVolts = 0.0;
    private double driveAppliedVolts = 0.0;
    private double turnAppliedVolts = 0.0;

    public ModuleIOSim(SwerveModuleSimulation moduleSimulation) {
        this.moduleSimulation = moduleSimulation;
        this.driveMotor =
                moduleSimulation.useGenericMotorControllerForDrive().withCurrentLimit(Amps.of(driveConstants.driveMotorCurrentLimit));
        this.turnMotor =
                moduleSimulation.useGenericControllerForSteer().withCurrentLimit(Amps.of(driveConstants.turnMotorCurrentLimit));

        // Enable wrapping for turn PID
        turnController.enableContinuousInput(-Math.PI, Math.PI);
    }

    @Override
    public void updateInputs(ModuleIOInputs inputs) {
        // Run closed-loop control
        if (driveClosedLoop) {
            driveAppliedVolts = driveFFVolts
                    + driveController.calculate(
                            moduleSimulation.getDriveWheelFinalSpeed().in(RadiansPerSecond));
        } else {
            driveController.reset();
        }
        if (turnClosedLoop) {
            turnAppliedVolts = turnController.calculate(
                    moduleSimulation.getSteerAbsoluteFacing().getRadians());
        } else {
            turnController.reset();
        }

        // Update simulation state
        driveMotor.requestVoltage(Volts.of(driveAppliedVolts));
        turnMotor.requestVoltage(Volts.of(turnAppliedVolts));

        // Update drive inputs
        inputs.driveConnected = true;
        inputs.drivePositionRad = moduleSimulation.getDriveWheelFinalPosition().in(Radians);
        inputs.driveVelocityRadPerSec =
                moduleSimulation.getDriveWheelFinalSpeed().in(RadiansPerSecond);
        inputs.driveAppliedVolts = driveAppliedVolts;
        inputs.driveCurrentAmps =
                Math.abs(moduleSimulation.getDriveMotorStatorCurrent().in(Amps));

        // Update turn inputs
        inputs.turnConnected = true;
        inputs.turnPosition = moduleSimulation.getSteerAbsoluteFacing();
        inputs.turnVelocityRadPerSec =
                moduleSimulation.getSteerAbsoluteEncoderSpeed().in(RadiansPerSecond);
        inputs.turnAppliedVolts = turnAppliedVolts;
        inputs.turnCurrentAmps =
                Math.abs(moduleSimulation.getSteerMotorStatorCurrent().in(Amps));

        // Update odometry inputs
        inputs.odometryTimestamps = SparkUtil.getSimulationOdometryTimeStamps();
        inputs.odometryDrivePositionsRad = Arrays.stream(moduleSimulation.getCachedDriveWheelFinalPositions())
                .mapToDouble(angle -> angle.in(Radians))
                .toArray();
        inputs.odometryTurnPositions = moduleSimulation.getCachedSteerAbsolutePositions();
    }

    @Override
    public void setDriveOpenLoop(double output) {
        driveClosedLoop = false;
        driveAppliedVolts = output;
    }

    @Override
    public void setTurnOpenLoop(double output) {
        turnClosedLoop = false;
        turnAppliedVolts = output;
    }

    @Override
    public void setDriveVelocity(double velocityRadPerSec) {
        driveClosedLoop = true;
        driveFFVolts = driveConstants.driveSimKs * Math.signum(velocityRadPerSec) + driveConstants.driveSimKv * velocityRadPerSec;
        driveController.setSetpoint(velocityRadPerSec);
    }

    @Override
    public void setTurnPosition(Rotation2d rotation) {
        turnClosedLoop = true;
        turnController.setSetpoint(rotation.getRadians());
    }
}