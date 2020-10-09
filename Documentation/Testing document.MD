# Testing document

## Unit testing

Unit testing was done with JUnit. A few different scenarios were used to ensure that the algorithms and the data structures behave properly even in some borderline cases. Different sized dungeons with different configurations were used in the tests.

Tests for the dungeon generation algorithms are challenging to create as most of the logic is based on random number generation. Therefore, only a few test cases were implemented, and the validity of the algorithms is best checked with running the algorithms with different inputs.

## Performance testing

The user has an option to run performance testing for both dungeon generation and flood filling once the program is executed. The results are written to .csv files located in `\DungeonGenerator\Test Results`.

### Dungeon generation

For dungeon generation, the user can choose how many runs will be averaged, and what the maximum size of the dungeon is.

Cellular automata are made to run with 4 iterations and initial stone percentage of 50. Random walks are made to run with dig percentage of 50, spawn chance for a new walker of 4%, and simple walk algorithm. The choice to use simple walk was due to higher complexity of complex walk brought by yet another parameter (turn chance). Also, in most cases, simple walk performs clearly better, so it is more fruitful to compare two quick algorithms than one quick and one slow algorithm.


### Flood fill

For flood fill, the user can choose how many runs will be averaged. A few different test patterns are implemented and shown below.

#### Dungeon 1

![dungeon 1](/Documentation/Pictures/Dungeons/FillPerformance1.PNG)

Dimensions: 10x10 cells

#### Dungeon 2

![dungeon 2](/Documentation/Pictures/Dungeons/FillPerformance2.PNG)

Dimensions: 16x16 cells

#### Dungeon 3

![dungeon 3](/Documentation/Pictures/Dungeons/FillPerformance3.PNG)

Dimensions: 100x150 cells

#### Dungeon 4

![dungeon 4](/Documentation/Pictures/Dungeons/FillPerformance4.PNG)

Dimensions: 500x500 cells

#### Dungeon 5

![dungeon 5](/Documentation/Pictures/Dungeons/FillPerformance5.PNG)

Dimensions: 500x500 cells

#### Dungeon 6

![dungeon 6](/Documentation/Pictures/Dungeons/FillPerformance6.PNG)

Dimensions: 800x800 cells
