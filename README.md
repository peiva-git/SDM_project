# Software Development Methods course project
[![CircleCI](https://dl.circleci.com/status-badge/img/gh/peiva-git/SDM_project/tree/master.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/peiva-git/SDM_project/tree/master)

Exam project for the Software Development Methods course at the University of Trieste.
Written in Java, using [Gradle](https://gradle.org/) and [CircleCI](https://circleci.com/).

## Freedom

From: https://boardgamegeek.com/boardgame/100480/freedom

**Freedom** is a two-player abstract strategy board game invented by Veljko Cirovic and Nebojsa Sankovic in 2010. It is played with black and white stones on a square board. The game is related to Go-Moku and Slimetrail. It can be played with a Go set or with pen and paper.

### Rules:

- **FreedomBoard**: Freedom is played on a 10×10 square board. Beginners can try the game on a 8×8 board. Other board sizes may be also used.

- **Objective**: The objective of Freedom is to have more "live" stones at the end of the game, than your opponent. A stone is considered to be "live" if it is a part of some horizontal, vertical or diagonal row of exactly 4 stones of the same color.

- **Play**: A game begins with an empty board. Each player has an allocated color: White and Black. White plays first, putting one white stone anywhere on the board. After this move players take turns placing their stones on empty cells adjacent to the last opponent's stone. If all cells adjacent to the last opponent's stone are occupied then the player gets the right ("freedom") to place his stone on any empty cell of the board. The game ends when the board is filled with stones. The last player has the right to pass on his last turn (and leave the last cell empty) if placing his stone reduces his score.

## Project configuration

### Unit testing and code analysis

A dockerized SonarQube server with persistent storage is provided to enable code analysis.
To start the server, simply run the following command:
```shell
docker compose -f path/to/docker-compose.yml up -d
```

To use the Sonarqube Scanner run:
```shell
./gradlew -Dsonar.login=[auth_token] sonar
```

To generate a code coverage report with [JaCoCo](https://github.com/jacoco/jacoco) run:
```shell
./gradlew -Dsonar.login=[auth_token] test jacocoTestReport sonar
```