<h1>Welcome to BotBlueprints! 🚀</h1>
<hr>
<p>This repository is more than just a collection of code—it's a collaborative hub designed to empower teams of all sizes to build, refine, and compete with cutting-edge bots. Whether you're a seasoned pro or just starting out, BotBlueprints is here to provide the foundation and resources needed to level up your bot-building game.</p>
<p>Our goal is to create an open environment where teams can share ideas, iterate quickly, and learn from one another. By working together and contributing to the collective knowledge, we're not just here to play; we're here to compete at the highest level. Every line of code you contribute, every improvement you make, helps everyone push the boundaries of what's possible.</p>
<p>Join us in shaping the future of bot development. Together, we’ll craft bots that not only perform, but compete—and win. 💪</p>
<h3>Let's build something extraordinary—one blueprint at a time!</h3>
<img src="https://mail.google.com/mail/u/0?ui=2&ik=0a870f5b6e&attid=0.1&permmsgid=msg-a:r-5926800304120108626&th=19645f7a9467a9f4&view=fimg&fur=ip&permmsgid=msg-a:r-5926800304120108626&sz=s0-l75-ft&attbid=ANGjdJ8hv_t_zpq0M5k9Gt8lCXfeRGgVmoNOCYlGNPAKTvFXB66g_7ZkEIjkHIgYH-GIU3wYJ1msNWqU-eCTfeDVJ8GHp5xeRcuddnX6HwSoW8zweFhmA2OQvLaR8LE&disp=emb&realattid=ii_m9lylzzf0&zw" alt="BotBlueprints Logo">

<hr>
<h1>Code Structure and Permissions</h1>
<hr>
<p>The folder structure is organized to clearly separate general-purpose robot code from game-specific logic. The common folder is intended for shared, reusable code that handles hardware or robot functionality which remains consistent across different seasons. This might include drivetrain subsystems, sensor wrappers, utility classes, and general-purpose tools like PID controllers or motor helpers. By placing this code in common, it can be reused each year without needing modification. On the other hand, the games folder is dedicated to game-specific code tailored to the unique rules, strategies, and mechanisms of each season’s challenge. Within it, subfolders like reefscape2025 and mars2026 contain code and logic that are specific to those games, such as autonomous routines, game-object handling, or field navigation strategies. This structure enhances modularity and maintainability, allowing developers to update the robot's core functionality independently from the yearly strategy while supporting clean organization and code reuse.🚀🚀🚀</p>
<hr>
<p>This is an example of our code heirarchy - </p>
<ul>
  <li>java/
    <ul>
      <li>frc/
        <ul>
          <li>robot/
            <ul>
              <li>common/</li>
              <li>games/
                <ul>
                  <li>mars2026/</li>
                  <li>reefscape2025/</li>
                </ul>
              </li>
            </ul>
          </li>
        </ul>
      </li>
    </ul>
  </li>
</ul>

