# Project specification

* Topic: dungeon generator and solver
* Programming language: Java
* Language: English
* Degree programme: Degree Programme in Chemistry

## Algorithms

The goal is to implement two different dungeon generation algorithms and a "solver" algorithm;

* cellular automaton (generation)
* random walk (generation)
* flood fill ("solver")

### Cellular automaton

In a cellular automaton, we have a grid of cells with each cell having a certain state at time `t=0`. With each iteration of the cellular automaton (i.e. `t+=1`), the states of the cells will change according to some set of rules that depend on the state of the cell itself and the states of its neighboring cells.

For dungeon generation, the cellular automaton could work as follows:

1. Create a grid with all the cells having the state "floor" (walkable)
2. Change the state of some cells to "stone" (non-walkable)
3. Go through all the cells and
	a. if at least 5 neighbors are stone, change the cell to stone
	b. otherwise, change the cell to floor
4. Repeat step 3 as many times as wanted

Note that, unlike in some versions of cellular automata, the ruleset given above doesn't care about the state of the cell itself.

Since the grid is a 2D array, the cellular automaton's time and space complexities are `O(n^2)`, where `n=MAX(grid's width, grid's heigth)`.

As input, the algorithm will be given

* the grid's width
* the grid's heigth
* the percent of "stone" cells at `t=0`
* the amount of iterations

### Random walk

In random walk, we have one or more walkers that (as the name suggests) walk in random directions.

For dungeon generator, random walk algorithm could work as follows:

1. Create a grid with all the cells having the state "stone"
2. Create a random walker in a random place
3. Move (all the) walker(s) in a random direction
4. If a walker ends up on a cell that's stone, change it to floor
5. Randomly spawn a new random walker on top of an already existing walker
6. Repeat steps 3-5 as many times as wanted

Of course, the "randomness" of random walk can be tweaked by changing certain parameters

* increase the likelihood of a walker moving straight (long straight lines vs. winding corridors)
* kill a walker if it enters a cell that's floor
* tweak the likelihood of a new walker spawning

The time complexity of the algorithm will most likely be `O(n)`, where `n=MAX(walkers, iterations)`. This can be reasoned from the pseudocode:

	for i = 0, i < n, i++
		for each walker in walkers
			walker.walk_in_random_direction
			// change the state of the current cell
			// (maybe) spawn a new walker

As input, the algorithm will be given

* the grid's width
* the grid's heigth
* the amount of iterations
* the maximum amount of walkers
* the likelihood of a walker moving straight (in percents)
* whether a walker is killed when it enters a floor cell
* the likelihood of spawning a new walker

### Flood fill

Flood fill is an algorithm that determines what areas are connected to a certain node in a multidimensional array (literally the bucket fill tool from Paint, GIMP etc.). The algorithm is given a starting node, a target "color", and a replacement "color". Starting from the given node, it will look for nodes that are both connected to the given node and of the target color, change them to the replacement color, and add them to a queue or a stack of nodes. Once the current node has no more target nodes adjacent to it, the algorithm will take a new node from the stack/queue and start over.

The algorithm's purpose is to check whether a generated dungeon system is one continues system and not separated into several different areas. It will not be given any parameters at the start; it will simply find a random floor cell and begin filling from there.

Instead of a queue or a stack, the algorithm can be called recursively with each new target node. This, however, can be extremely memory intensive, which is why I plan on implementing the algorithm with a queue.

The algorithm's time and space complexities depend on the size of the fillable area, so they are `O(n^2)`, where `n=MAX(grid's width, grid's heigth)`.

## Sources

1. [Wikipedia, *Cellular automaton*](https://en.wikipedia.org/wiki/Cellular_automaton)
2. [Johnson, L. *et.al.*, *Cellular automata for real-time generationof infinite cave levels*](http://julian.togelius.com/Johnson2010Cellular.pdf)
3. [Wikipedia, *Random walk*](https://en.wikipedia.org/wiki/Random_walk)
4. [Noel Berry, *Procedural Generation - The Caves*](http://noelberry.ca/#thecaves)
5. [Wikipedia, *Flood fill*](https://en.wikipedia.org/wiki/Flood_fill)
