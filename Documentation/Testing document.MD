# Testing document

## Unit testing

Unit testing was done with JUnit. A few different scenarios were used to ensure that the algorithms and the data structures behave properly even in some borderline cases. Different sized dungeons with different configurations were used in the tests.

Tests for the dungeon generation algorithms are challenging to create as most of the logic is based on random number generation. Therefore, only a few test cases were implemented, and the validity of the algorithms is best checked with running the algorithms with different inputs.

JaCoCo is used to track the unit testing coverage. The reports can be accessed from `\build\reports\jacoco\test\html\index.html` after running JaCoCo.

There are no tests for `GUI.java`, `Main.java` or `PerformanceTests.java` and therefore they are excluded from JaCoCo reports.

## Performance testing

The user has an option to run performance testing for both dungeon generation and flood filling once the program is executed. The results are written to .csv files that are created at the root folder.

For all the tests, the user can choose the maximum size of the dungeon and the number of runs to be averaged.

Performance testing for certain parameters was not implemented:

* cellular automaton's performance doesn't depend on initial stone percent, so no performance testing was implemented
* cellular automaton's performance depending on the number of iterations is linear, so no performance testing was implemented
* there are no performance tests for random walk's `complexWalk` as its behavior is unreliable

### Dungeon generation

#### Random walk

The user can choose between two different test cases: how spawn chance and how dig percent affects the performance.

##### Dig percent

For dig percent testing, the user can choose the spawn chance for new walkers.

Below is a table and a graphical representation of the test results. For this test, spawn chance was set to 2% and runs to average was 100.

| Dimension | Dig percent = 10% | Dig percent = 20% | Dig percent = 30% | Dig percent = 40% | Dig percent = 50% | Dig percent = 60% |
|-----------|-------------------|-------------------|-------------------|-------------------|-------------------|-------------------|
| 100       | 1920              | 7002              | 16248             | 29924             | 49616             | 67031             |
| 1024      | 13181             | 43491             | 95411             | 170239            | 280677            | 431222            |
| 4096      | 60838             | 198238            | 424328            | 758372            | 1251416           | 1960819           |
| 10000     | 158793            | 517835            | 1105052           | 2005093           | 3320066           | 5165396           |
| 40000     | 737376            | 2343678           | 5032306           | 9089796           | 15076381          | 23808205          |
| 90000     | 1729106           | 5494462           | 11894318          | 21444035          | 35764375          | 56770555          |
| 160000    | 3150334           | 10195835          | 21982579          | 40120999          | 66899657          | 1.06E+08          |
| 250000    | 5105948           | 16416588          | 35661353          | 65257719          | 1.1E+08           | 1.72E+08          |
| 360000    | 7560172           | 24563672          | 52972497          | 96974219          | 1.61E+08          | 2.54E+08          |
| 640000    | 13948154          | 45046746          | 96863502          | 1.78E+08          | 2.96E+08          | 4.71E+08          |
| 1000000   | 22064320          | 71653298          | 1.55E+08          | 2.84E+08          | 4.77E+08          | 7.61E+08          |

![Dig percent testing](/Documentation/Pictures/Random walk dig plot.png)

We can see that as the dig percent increases, the average time to run increases. This is understandable, as walkers need to find more cells to dig. The non-linearity is explained with the RNG nature of the algorithm; with walkers randomly choosing their directions, it is inevitable that they end up walking to a floor cell.

##### Spawn chance

For spawn chance testing, the user can choose the dig percent.

Below is a table and a graphical representation of the test results. For this test, dig percent was set to 45% and runs to average was 100.

| Dimension | Spawn chance = 10% | Spawn chance = 20% | Spawn chance = 30% | Spawn chance = 40% | Spawn chance = 50% | Spawn chance = 60% |
|-----------|--------------------|--------------------|--------------------|--------------------|--------------------|--------------------|
| 100       | 59071              | 78802              | 92369              | 106720             | 123686             | 139776             |
| 1024      | 130157             | 280848             | 448666             | 587990             | 780986             | 932624             |
| 4096      | 342012             | 699579             | 1101962            | 1608093            | 2131147            | 2885773            |
| 10000     | 798646             | 1683098            | 2654877            | 3957260            | 5254769            | 6769118            |
| 40000     | 3285480            | 7112889            | 11079646           | 15651258           | 21657822           | 27713677           |
| 90000     | 7446731            | 15349753           | 24439482           | 35094395           | 48032844           | 61907555           |
| 160000    | 13400111           | 27649258           | 44011903           | 62735205           | 84708457           | 1.1E+08            |
| 250000    | 21753743           | 44423154           | 70533694           | 1E+08              | 1.36E+08           | 1.76E+08           |
| 360000    | 31614589           | 64909583           | 1.04E+08           | 1.48E+08           | 1.98E+08           | 2.54E+08           |
| 640000    | 57946936           | 1.18E+08           | 1.87E+08           | 2.64E+08           | 3.53E+08           | 4.54E+08           |
| 1000000   | 92519584           | 1.88E+08           | 2.95E+08           | 4.17E+08           | 5.55E+08           | 7.14E+08           |

![Spawn chance testing](/Documentation/Pictures/Random walk spawn plot.png)

We can see that as the spawn chance increases, the average time to run increases. This is explained with a higher chance of a walker walking into a floor cell.

#### Cellular automaton vs Random walk

Even though the two algorithms create highly different kinds of dungeons, it is still interesting to compare their performance.

For testing, the user can choose the iterations and the initial stone percent for the cellular automaton, and spawn chance and dig percent for the random walk algorithm.

Below is a table and a graphical representation of the test results. For this test, cellular automaton's iterations was 4 and was stone percent 45%, random walk's spawn chance was 2% and dig percent was 45%, and runs to average was 100. The parameters chosen represent the ideal values for running the generation algorithms.

| Dimension | Cellular automaton | Random walk |
|-----------|--------------------|-------------|
| 100       | 55364              | 37262       |
| 1024      | 191521             | 171325      |
| 4096      | 627055             | 569772      |
| 10000     | 1419998            | 1154330     |
| 40000     | 5602293            | 5053829     |
| 90000     | 12533102           | 12169606    |
| 160000    | 22245093           | 21935265    |
| 250000    | 34790226           | 35749208    |
| 360000    | 50376318           | 52705985    |
| 640000    | 89445190           | 97327602    |
| 1000000   | 1.4E+08            | 1.56E+08    |
| 1440000   | 2.03E+08           | 2.31E+08    |
| 2250000   | 3.24E+08           | 3.71E+08    |
| 3240000   | 4.79E+08           | 5.49E+08    |
| 4000000   | 5.96E+08           | 6.83E+08    |

![Generation testing](/Documentation/Pictures/Generation plot.png)

We can see that with small dungeon sizes, random walk performs slightly faster as the algorithm doesn't have to go through the whole dungeon. However, as the dungeon's size increases, cellular automaton begins to perform faster as walkers have a higher chance of walking into floor cells.

### Flood fill/Finding the largest connected area

Cellular automaton is used to generate dungeons for flood filling tests as it performs faster than random walk with large dungeons.

The user can choose the iterations and initial stone percent for the cellular automaton. For all the following tests, 4 iterations was used.

#### Forest fire

Below is a table and a graphical representation of the test results.

| Dimension | Forest fire with stone percent 45% | Forest fire with stone percent 50% | Forest fire with stone percent 55% |
|-----------|------------------------------------|------------------------------------|------------------------------------|
| 100       | 31110                              | 15926                              | 12502                              |
| 1024      | 78425                              | 52740                              | 16803                              |
| 4096      | 193898                             | 108083                             | 34927                              |
| 10000     | 321680                             | 213913                             | 86513                              |
| 40000     | 1362528                            | 690015                             | 352836                             |
| 90000     | 3380726                            | 1439175                            | 838856                             |
| 160000    | 4758296                            | 2196409                            | 1422151                            |
| 250000    | 7536439                            | 3392710                            | 2257403                            |
| 360000    | 11185025                           | 5012990                            | 3251108                            |
| 640000    | 21434491                           | 9324269                            | 5937701                            |
| 1000000   | 34698622                           | 15206432                           | 9660986                            |
| 1440000   | 51274731                           | 21905281                           | 14582940                           |
| 2250000   | 82811157                           | 35713088                           | 24248386                           |
| 3240000   | 1.25E+08                           | 51226434                           | 35053936                           |
| 4000000   | 1.58E+08                           | 63600902                           | 42680411                           |

![Generation testing](/Documentation/Pictures/Forest fire plot.png)

We can see that the time to run decreases as the initial stone percent increases because the connected areas become smaller. With smaller areas, the largest area is found faster (#2 of complexity analysis, see "Implementation document") and it is filled faster (#3 of complexity analysis, see "Implementation document").

#### Scan fill

Below is a table and a graphical representation of the test results.

| Dimension | Scan fill with stone percent 45% | Scan fill with stone percent 50% | Scan fill with stone percent 55% |
|-----------|----------------------------------|----------------------------------|----------------------------------|
| 100       | 17319                            | 15096                            | 11916                            |
| 1024      | 54629                            | 38467                            | 14589                            |
| 4096      | 165021                           | 93689                            | 27862                            |
| 10000     | 208301                           | 115475                           | 69546                            |
| 40000     | 828236                           | 440697                           | 274167                           |
| 90000     | 1990692                          | 921504                           | 611850                           |
| 160000    | 3222368                          | 1604221                          | 1079580                          |
| 250000    | 5036731                          | 2411673                          | 1681438                          |
| 360000    | 7228562                          | 3525896                          | 2471747                          |
| 640000    | 13073386                         | 6416936                          | 4516184                          |
| 1000000   | 20651634                         | 10255326                         | 7328717                          |
| 1440000   | 29641056                         | 14595209                         | 11004316                         |
| 2250000   | 46778041                         | 22900148                         | 17396831                         |
| 3240000   | 67705735                         | 33161290                         | 24413481                         |
| 4000000   | 83419986                         | 40727818                         | 30942081                         |

![Generation testing](/Documentation/Pictures/Scan fill plot.png)

We can see that the time to run decreases as the initial stone percent increases because the connected areas become smaller. With smaller areas, the largest area is found faster (#2 of complexity analysis, see "Implementation document") and it is filled faster (#3 of complexity analysis, see "Implementation document").

#### Forest fire vs Scan fill

Below are tables and graphical representations for the test results. The initial stone percents were 45%, 50%, and 55% for the first, second, and third test respectively.

| Dimension | Forest fire | Scan fill |
|-----------|-------------|-----------|
| 100       | 31110       | 17319     |
| 1024      | 78425       | 54629     |
| 4096      | 193898      | 165021    |
| 10000     | 321680      | 208301    |
| 40000     | 1362528     | 828236    |
| 90000     | 3380726     | 1990692   |
| 160000    | 4758296     | 3222368   |
| 250000    | 7536439     | 5036731   |
| 360000    | 11185025    | 7228562   |
| 640000    | 21434491    | 13073386  |
| 1000000   | 34698622    | 20651634  |
| 1440000   | 51274731    | 29641056  |
| 2250000   | 82811157    | 46778041  |
| 3240000   | 1.25E+08    | 67705735  |
| 4000000   | 1.58E+08    | 83419986  |

![Generation testing](/Documentation/Pictures/Flood fill plot 45.png)

| Dimension | Forest fire | Scan fill |
|-----------|-------------|-----------|
| 100       | 15926       | 15096     |
| 1024      | 52740       | 38467     |
| 4096      | 108083      | 93689     |
| 10000     | 213913      | 115475    |
| 40000     | 690015      | 440697    |
| 90000     | 1439175     | 921504    |
| 160000    | 2196409     | 1604221   |
| 250000    | 3392710     | 2411673   |
| 360000    | 5012990     | 3525896   |
| 640000    | 9324269     | 6416936   |
| 1000000   | 15206432    | 10255326  |
| 1440000   | 21905281    | 14595209  |
| 2250000   | 35713088    | 22900148  |
| 3240000   | 51226434    | 33161290  |
| 4000000   | 63600902    | 40727818  |

![Generation testing](/Documentation/Pictures/Flood fill plot 50.png)

| Dimension | Forest fire | Scan fill |
|-----------|-------------|-----------|
| 100       | 12502       | 11916     |
| 1024      | 16803       | 14589     |
| 4096      | 34927       | 27862     |
| 10000     | 86513       | 69546     |
| 40000     | 352836      | 274167    |
| 90000     | 838856      | 611850    |
| 160000    | 1422151     | 1079580   |
| 250000    | 2257403     | 1681438   |
| 360000    | 3251108     | 2471747   |
| 640000    | 5937701     | 4516184   |
| 1000000   | 9660986     | 7328717   |
| 1440000   | 14582940    | 11004316  |
| 2250000   | 24248386    | 17396831  |
| 3240000   | 35053936    | 24413481  |
| 4000000   | 42680411    | 30942081  |

![Generation testing](/Documentation/Pictures/Flood fill plot 55.png)

We can see that scan fill performs faster in all the cases as it examines a line of cells at one step instead of a singular cell, and only cells that start a line are added to the queue.

### General notes

From all the plots, we can see that the algorithms depend linearly on the size of the dungeon. This means that the time complexities of all the algorithms are `O(m)` as predicted in pseudocode analyses.