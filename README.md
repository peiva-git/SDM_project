# Software Development Methods course project
[![CircleCI](https://dl.circleci.com/status-badge/img/gh/peiva-git/SDM_project/tree/master.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/peiva-git/SDM_project/tree/master)

Exam project for the Software Development Methods course at the University of Trieste.
Written in Java, using [Gradle](https://gradle.org/), [CircleCI](https://circleci.com/) and [libGDX](https://libgdx.com/).
The template for this project has been generated using [libGDX's tool](https://libgdx.com/wiki/start/project-generation).

## The Freedom board game

This repository contains an implementation of the freedom board game. Below you can find a brief description,
which was taken from [here](https://boardgamegeek.com/boardgame/100480/freedom):

**Freedom** is a two-player abstract strategy board game invented by Veljko Cirovic and Nebojsa Sankovic in 2010. 
It is played with black and white stones on a square board. 
The game is related to Go-Moku and Slimetrail. It can be played with a Go set or with pen and paper.

### Rules:

- **Board**: Freedom is played on a 10×10 square board. Beginners can try the game on an 8×8 board. 
Other board sizes may be also used.

- **Objective**: The objective of Freedom is to have more "live" stones at the end of the game than your opponent has.
A stone is considered to be "live" if it is a part of some horizontal, vertical or diagonal row of exactly 4 stones of the same color.

- **Play**: A game begins with an empty board. 
Each player has an allocated color: White and Black. 
White plays first, putting one white stone anywhere on the board. 
After this move players take turns placing their stones on empty cells adjacent to the last opponent's stone. 
If all cells adjacent to the last opponent's stone are occupied then the player gets the right ("freedom") to place his stone on any empty cell of the board. 
The game ends when the board is filled with stones. 
The last player has the right to pass on his last turn (and leave the last cell empty) if placing his stone reduces his score.

## Project configuration and setup

The project is divided in four different modules:
1. A `core` module, containing the core classes and main GUI implementation
2. A `desktop` module, with a desktop launcher to execute the GUI as a desktop app
3. A `html` module, that builds the necessary files to execute the GUI on a server or as a webapp
4. A `terminal` module, to launch the application as a terminal-based game

### Minimum requirements

This project requires Java version 11 to run, as specified in the `gradle.build` configuration files.

### Code analysis

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

### Unit testing

Each module can have its own set of tests. The tests can be executed all together, by running the main `test` task:
```shell
./gradlew test
```
Or they can be executed separately for each module:
```shell
./gradlew [module_name]:test
```

## Run the project

To launch the desktop application, run:
```shell
./gradlew desktop:run
```

To launch the webapp, run:
```shell
./gradlew html:superDev
```
This command will also start a server on local port `8080`.

If you only wish to compile the project to Javascript and then use the generated files yourself, run:
```shell
./gradlew html:dist
```
More details on what to do next and where to find the generated files can be found
[here](https://libgdx.com/wiki/deployment/deploying-your-application#deploy-web).
If you want to give it a try without having to compile everything yourself,
you can take a look at the webapp version of the game
on our [GitHub Pages website](https://peiva-git.github.io/SDM_project/)!

To launch the terminal-based version of the game with gradle, run:
```shell
./gradlew terminal:run
```

As an alternative, you could also build the `jar` executable yourself by running:
```shell
./gradlew terminal:dist
```
The obtained `jar` file can be then executed by a JVM. You can find out the available command-line arguments
by specifying the `--help` option.

## Credits

This project was built by using the [libGDX library](https://libgdx.com/). All credits go to the authors.
The command line argument parsing was done by using the [JCommander tool](https://jcommander.org/). 
All credits go to the author Cédric Beust.
