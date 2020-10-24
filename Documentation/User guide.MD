# User guide 

## Installing

1. Make sure you have Java 11 installed
2. (Optional) Make sure you have Gradle installed
3. Download the latest release
4. Run the jar by double-clicking it or
5. In terminal, navigate to the `DungeonGenerator` folder and execute command `gradle run`

## Running the program

All the input for the algorithms is given via integer spinners, checkboxes, and drop-down menus that are named.

### Cellular automaton

The ideal input ranges for cellular automaton are

* dungeon width and heigth same value
* iterations: 3-6
* stone percent at start: 40-55

### Random walk

The ideal input ranges for random walk are

* dungeon width and heigth same value
* percent chance of spawning a new walker: 1-15
* percent of area to dig: 30-60
* uncheck complex walk (percent chance of walker turning has no effect at this point)

### Performance testing

Once you run the program, you have the option of running performance testing for the algorithms. Running a performance test will create a .csv file at the root folder.

Please note that large maximum dungeon size and number of runs to average can be demanding, and the program might crash depending on the hardwares specifications.

### Gradle commands

Other Gradle commands are

* Building a .jar file: `gradle build`
* Generating JaCoCo test coverage reports: `gradle jacocoTestReport`
* Generating JavaDoc: `gradle javadoc`
* Generating Checkstyle reports: `gradle checkstyleMain` and `gradle checkstyleTest`
* Running tests: `gradle test`

All generated files are found in `build` folder.
