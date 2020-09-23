# Week 3 report

Work hours: 15-20 h

At the start of week 3, I noticed that I had accidentally introduced a new bug to the cellular automaton, so I fixed that. Then I began my work on the flood fill algorithm.

First, I decided to create a clean up method for the dungeon following the idea from [Noel Berry's website](http://noelberry.ca/#thecaves). This would remove all lone stone cells and stone cells attached to a single stone cell, and it would continue to do so until nothing else could be removed. This makes the dungeons much cleaner.

Next, I refactored `Walker` class to `Cell` class, so that it could be used in flood fill to hold cell coordinates. Then coding the algorithm was rather simple. Following that, I created tests for flood fill.

With all the major things for all three algorithms done, I decided to, once again, experiment with the algorithms. I noticed that the cellular automaton was working rather strangely; in [Johnson's paper](http://julian.togelius.com/Johnson2010Cellular.pdf) they used 50% infil for stone, and in [this website](http://roguebasin.roguelikedevelopment.org/index.php?title=Cellular_Automata_Method_for_Generating_Random_Cave-Like_Levels) 45% was used. However, with my algorithm, such percentages would create large spaceous areas with small islets of stone. Reading the sources again, I noticed that they would count the actual given cell into the "neighbors"; this makes it so that a stone cell needs only four neighboring stone cells. Implementing this change fixed the problem I was experiencing.

Next up, I started working on the GUI as rendering the dungeons was slow. I read up on JavaFX's canvas and quickly applied that to the GUI. Now rendering the dungeons is almost instantaneous.

Finally, I coded a queue data structure and created tests for it.
