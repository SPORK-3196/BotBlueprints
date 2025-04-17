<h1>Welcome to BotBlueprints! 🚀</h1>
<hr>
<p>This repository is more than just a collection of code—it's a collaborative hub designed to empower teams of all sizes to build, refine, and compete with cutting-edge bots. Whether you're a seasoned pro or just starting out, BotBlueprints is here to provide the foundation and resources needed to level up your bot-building game.</p>
<p>Our goal is to create an open environment where teams can share ideas, iterate quickly, and learn from one another. By working together and contributing to the collective knowledge, we're not just here to play; we're here to compete at the highest level. Every line of code you contribute, every improvement you make, helps everyone push the boundaries of what's possible.</p>
<p>Join us in shaping the future of bot development. Together, we’ll craft bots that not only perform, but compete—and win. 💪</p>
<h3>Let's build something extraordinary—one blueprint at a time!</h3>
<img src="https://ci3.googleusercontent.com/mail-img-att/AGAZnRq92A75MjnEz0A49mzGNfldx8-i8-6lFxRVtkQ0P52BJPMpT74wu64ajx9ob87-SviEP-6z8E7-KgiKt0lxsAkNPSlcrsLixjJdmlE-B7ku9nw_-rlgXJEap7ZSb8Gu54v19XAFVmO05v_MB_pOmOmrBf8vfKf1nFn7yw9s6qN8EtM8DZsGasBmeHoiTFgkN_secutTBJpPq_3I4HXkCeQjm0NBNcHG7q95INyQVBLnBAD70uEKRGblG-tAKYh-IrhNp4NK9jSiFJOE8kClFH-5AvHaqCR2ry2nQJo9wfLq117Qhvw-Eb8nczbo7q4QUqsjKyubi7RBnCKV9ncSRdsnJJiEGaYrzuu7-q5Tf1x9oaW0cKaDcPzPOIsPXbQIitJFCznGRvse88hWiuRMHcg233atPzTBTSkPo_00CS7Yf8Lwvw0RMNoHt54o8-IP5JLi192u3RXzaZSEOItg0zRzrts_HbXpYsyenKBFBy7yQ894CdYBql_fp4NkJ2l1zqFm9KUQPI8fJAKJuP3K1PTiuHs8TpM_GZ56eTIqtwSnIJc9buXThn2C9lRkXpGMhsowwqkC0yAJlddGBpqhrOl05PO001cnS1TkbrNGRYXz275uYKTG6UtdfLQPpBkTtUS4DWJMF6Ox74MU0ArujFWLQ0ZkL5oaK3g16ymwpWvrIYY3Xu69UEOZWo0t5KtM44dWATz63hhV_7lJu0qKp7Iptyp4H2pTK13oxjFMe5ScrCbU2t2dZx1ozDC64X3OFHpDoFBv5TcXoCg6-HBHiALZ6dtcTWgeg1t4l5oTFM_K-SteQBkT-Fm2jZ94OnOVFc9NJYwet7f6CeUEnBrAN6gJI4_6NnR0jLW7fAiI3LmsnZYFRTbys5ktAF_FsrP3ZCOKGkA3wB6UfT66LAGoAdAUk1UQmNQ2-G_iMwCV7x5WYiKwekcJTBIMSMNAlNMB4fK1yamKORwBO2uy25GQ25LDEy91u2hIIsLMWp8GRAQJk57Cd3SySjUQwlv6KPjp0R3JP0OKFPH3wQZMUagRovAI=s0-l75-ft" alt="BotBlueprints Logo">

<hr>
<h1>Code Structure and Permissions</h1>
<hr>
<p>The folder structure is organized to clearly separate general-purpose robot code from game-specific logic. The common folder is intended for shared, reusable code that handles hardware or robot functionality which remains consistent across different seasons.</p>
<p>This might include drivetrain subsystems, sensor wrappers, utility classes, and general-purpose tools like PID controllers or motor helpers. By placing this code in common, it can be reused each year without needing modification.</p>
<p>On the other hand, the games folder is dedicated to game-specific code tailored to the unique rules, strategies, and mechanisms of each season’s challenge. Within it, subfolders like reefscape2025 and mars2026 contain code and logic that are specific to those games, such as autonomous routines, game-object handling, or field navigation strategies.</p>
<p>This structure enhances modularity and maintainability, allowing developers to update the robot's core functionality independently from the yearly strategy while supporting clean organization and code reuse.</p>
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

