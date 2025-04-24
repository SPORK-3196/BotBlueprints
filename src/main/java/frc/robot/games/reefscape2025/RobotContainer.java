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

package frc.robot.games.reefscape2025;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.util.PathPlannerLogging;

import static frc.robot.common.subsystems.vision.VisionConstants.*;

import java.util.Optional;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.common.subsystems.drive.*;
import frc.robot.common.subsystems.vision.*;
import frc.robot.games.reefscape2025.commands.SetElevatorHeightCommand;
import frc.robot.games.reefscape2025.commands.ShootCoralCommand;
import frc.robot.games.reefscape2025.subsystems.drive.DriveConstants;

import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.drivesims.SwerveDriveSimulation;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and button mappings) should be declared here.
 */


public class RobotContainer {
    // Subsystems
    public static Drive drive;
    public static Vision vision;
    private SwerveDriveSimulation driveSimulation = null;
    private final XboxController driverController = new XboxController(0);
    private final XboxController operatorController = new XboxController(1);
    private final LoggedDashboardChooser<Command> autoChooser;
    private final Field2d field = new Field2d();
    private ElevatorSubsystem elevator = new ElevatorSubsystem();
    private CoralOutputSubsystem coralouter = new CoralOutputSubsystem();


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        switch (Constants.currentMode) {
            case REAL:
                /* Real robot, instantiate hardware IO implementations */
                drive = new Drive(
                        new GyroIONavX(),
                        new ModuleIOSpark(0),
                        new ModuleIOSpark(1),
                        new ModuleIOSpark(2),
                        new ModuleIOSpark(3));
                                
                vision = new Vision(
                    drive,
                    new VisionIOLimelight(VisionConstants.camera0Name, drive::getRotation));
                    
                break;

            case SIM:
                // create a maple-sim swerve drive simulation instance
                this.driveSimulation = new SwerveDriveSimulation(DriveConstants.mapleSimConfig, new Pose2d(3, 3, new Rotation2d()));
                
                // add the simulated drivetrain to the simulation field
                SimulatedArena.getInstance().addDriveTrainSimulation(driveSimulation);
                
                // Sim robot, instantiate physics sim IO implementations
                drive = new Drive(
                        new GyroIOSim(driveSimulation.getGyroSimulation()),
                        new ModuleIOSim(driveSimulation.getModules()[0]),
                        new ModuleIOSim(driveSimulation.getModules()[1]),
                        new ModuleIOSim(driveSimulation.getModules()[2]),
                        new ModuleIOSim(driveSimulation.getModules()[3]));

                vision = new Vision(
                    drive,
                    new VisionIOPhotonVisionSim(
                            camera0Name, robotToCamera0, driveSimulation::getSimulatedDriveTrainPose));

                break;

            default:
                // Replayed robot, disable IO implementations
                drive = new Drive(
                        new GyroIO() {}, new ModuleIO() {}, new ModuleIO() {}, new ModuleIO() {}, new ModuleIO() {});
                vision = null; // No Vision in replay mode

                break;

           
        }

                // Register your commands in NamedCommands (which stores them as Supplier<Command>)
        NamedCommands.registerCommand("CoralOutput", new ShootCoralCommand(coralouter, 0.6, false));
        NamedCommands.registerCommand("ElevatorL1", new SetElevatorHeightCommand(elevator, 3.3, false));
        NamedCommands.registerCommand("ElevatorL2", new SetElevatorHeightCommand(elevator, 14, false));
        NamedCommands.registerCommand("ElevatorL3", new SetElevatorHeightCommand(elevator, 28.5, false));
        NamedCommands.registerCommand("ElevatorL4", new SetElevatorHeightCommand(elevator, 56, false));
        
        // Set up auto routines
        autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

        // Set up SysId routines
        // autoChooser.addDefaultOption("UDY_BA_Mid_L1_1", new PathPlannerAuto("UDY_BA_Mid_L1_1"));
        /*autoChooser.addOption("UDY_RA_MID_L1_1", new PathPlannerAuto("UDY_RA_MID_L1_1"));
        autoChooser.addOption("UDY_BA_LW_L1_3", new PathPlannerAuto("UDY_BA_LW_L1_3"));
        autoChooser.addOption("UDY_RA_LW_L1_3", new PathPlannerAuto("UDY_RA_LW_L1_3"));
        autoChooser.addOption("UDY_BA_Mid_L4_1", new PathPlannerAuto("UDY_BA_Mid_L4_1"));
        autoChooser.addOption("UDY_RA_Mid_L4_1", new PathPlannerAuto("UDY_RA_Mid_L4_1"));
        autoChooser.addOption("UDY_BA_LW_L2_3", new PathPlannerAuto("UDY_BA_LW_L2_3"));
        autoChooser.addOption("UDY_RA_LW_L2_3", new PathPlannerAuto("UDY_RA_LW_L2_3"));
        autoChooser.addOption("UDY_BA_LW_L4_3", new PathPlannerAuto("UDY_BA_LW_L4_3"));
        autoChooser.addOption("UDY_RA_LW_L4_3", new PathPlannerAuto("UDY_RA_LW_L4_3")); */
        autoChooser.addOption("BlueMoveBack", new PathPlannerAuto("BlueMoveBack"));
        autoChooser.addOption("RedMoveBack", new PathPlannerAuto("RedMoveBack"));
        autoChooser.addOption("POS_RA_Middle_1", new PathPlannerAuto("POS_RA_Middle_1"));
        autoChooser.addOption("POS_BA_Middle_1", new PathPlannerAuto("POS_BA_Middle_1"));
        autoChooser.addOption("180BlueAuto_L1_1Coral", new PathPlannerAuto("180BlueAuto_L1_1Coral"));
        autoChooser.addOption("180RedAuto_L1_1Coral", new PathPlannerAuto("180RedAuto_L1_1Coral"));


        // Configure the button bindings
        new ControllerBindings(driverController, operatorController, drive, elevator, coralouter);
    }

    public Command getAutonomousCommand() {
        return autoChooser.get();
    }
    public void resetSimulationField() {
        if (Constants.currentMode != Constants.Mode.SIM) return;

        driveSimulation.setSimulationWorldPose(new Pose2d(3, 3, new Rotation2d()));
        SimulatedArena.getInstance().resetFieldForAuto();
    }

    public void displaySimFieldToAdvantageScope() {
        if (Constants.currentMode != Constants.Mode.SIM) return;

        Logger.recordOutput("FieldSimulation/RobotPosition", driveSimulation.getSimulatedDriveTrainPose());
        Logger.recordOutput("FieldSimulation/Notes", SimulatedArena.getInstance().getGamePiecesArrayByType("Note"));
    }

    public void updateSimulation() {
        if (Constants.currentMode != Constants.Mode.SIM) return;

        SimulatedArena.getInstance().simulationPeriodic();
        Logger.recordOutput("FieldSimulation/RobotPosition", driveSimulation.getSimulatedDriveTrainPose());
        Logger.recordOutput(
                "FieldSimulation/Coral", SimulatedArena.getInstance().getGamePiecesArrayByType("Coral"));
        Logger.recordOutput(
                "FieldSimulation/Algae", SimulatedArena.getInstance().getGamePiecesArrayByType("Algae"));
    }

    public void calibrateGyroWithVisionDirect() {
        System.out.println("DIRECT CALIBRATION START");
        if (vision == null) {
            System.out.println("Vision subsystem not available");
            return;
        }
        Optional<VisionIOLimelight> limelightOptional = vision.getVisionIOLimelight();
        if (limelightOptional.isPresent()) {
            VisionIOLimelight limelight = limelightOptional.get();
            Optional<Rotation2d> visionYaw = limelight.getVisionYaw();
            System.out.println("Vision yaw present: " + visionYaw.isPresent());
            if (visionYaw.isPresent()) {
                Rotation2d currentGyroAngle = drive.getRotation();
                double offset = currentGyroAngle.minus(visionYaw.get()).getDegrees();
                if (drive.getGyro() instanceof GyroIONavX) {
                    ((GyroIONavX) drive.getGyro()).setAngleAdjustment(offset);
                    System.out.println("Gyro calibrated with offset: " + offset);
                } else {
                    System.out.println("Gyro is not GyroIONavX, cannot calibrate");
                }
            } else {
                System.out.println("No vision yaw available");
            }
        } else {
            System.out.println("Limelight not available");
        }}
        
    public void SmartDashboardLogging() {
        SmartDashboard.putData("Field", field);

        PathPlannerLogging.setLogCurrentPoseCallback((pose) -> {
            field.setRobotPose(pose);
        });

        PathPlannerLogging.setLogTargetPoseCallback((pose) -> {
            field.getObject("target pose").setPose(pose);
        });

        PathPlannerLogging.setLogActivePathCallback((poses) -> {
            field.getObject("path").setPoses(poses);
        });
    }
}

