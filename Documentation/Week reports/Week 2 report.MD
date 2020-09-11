# Week 2 report

This week, I must have used somewhere around 15-20 hours on the project - most of the time went to bug fixing and researching unit testing for RNG-dependent methods.

The work started with me hunting and fixing bugs in the cellular automaton code. For this, I quickly cobbled together a graphical user interface with JavaFX.

After my bug fixing marathon, I implemented random walk algorithm into the program. I made two versions of it; one that has the walkers moving in completely random directions (the `simpleWalk()` method) and one that gives the user a bit more control (the `walk()` method). I then updated the GUI so that you can choose between cellular automaton and random walk, and started experimenting with both algorithms to see how they perform and what kind of dungeons they generate.

I quickly realized that, depending on the parameters, the more complex walk algorithm can create either horrible-looking or quite interesting dungeon complexes. In contrast, the simpler walk algorithm works like a charm (after I managed to squish a few bugs).

After fooling around with the algorithms, I started working on unit testing and JavaDoc. I decided not to create JavaDocs for trivial methods for now and focused more on the not-so-trivial stuff.

Unit testing proved to be quite challenging because most of the algorithms rely on RNG. I managed to get instruction coverage to 83% and branch coverage to 76%.

Currently, both checkstyle and test coverage reports are available in "\DungeonGenerator\build\reports"