# Implementation document

## Project structure

Below is an image of the package structure.

![package structure](/Documentation/Pictures/Diagram.png)

The `ui` package contains graphical user interface created with JavaFX that is used to run the program. The `domain` package contains all the algorithms and classes used in the algorithms, and the `util` package contains `CellQueue` data structure used in two algorithms.

## Complexities

### Cellular automaton

Below is the pseudocode for the cellular automaton.

	// initialize dungeon
	while currentStonePercent < stonePercentAtStart
		Cell randomCell = random  cell
		if randomCell is not stone
			change randomCell to stone
			
	// algorithm
	for int i = 0; i < iterations; i++
		for int y = 0; y < dungeonY; y++
			for int x = 0; x < dungeonX; x++
				count all stone cells in a 3x3 grid around the cell
				if stoneCellNeighbors > 5
					temporaryGrid[y][x] = stone
		
		dungeonGrid = temporaryGrid
		
In general, dungeon initialization is not a bottleneck as `stonePercentAtStart` should be between 40-55% (otherwise the generated dungeon will look bad). Therefore, we need to consider the algorithm itself.

The cellular automaton generally needs between 3 to 6 iterations until there are no changes between iterations. This means that the two inner for-loops dominate the time complexity, and therefore the time complexity is `O(dungeonX * dungeonY)`. We can approximate this as `O(n^2)`, where `n = max(dungeonX, dungeonY)`. Another way of saying this is that the time complexity is `O(m)`, where `m = dungeonX * dungeonY` is the size of the dungeon.

As the algorithm needs to store two arrays for the cells (`dungeonGrid` and `temporaryGrid`), the space complexity is `O(2n^2)` which simplifies to `O(n^2)` (or `O(m)`).

### Random walk

Below is the pseudocode for the random walk algorithm.

	// add a new walker at the center of the dungeon
	queue.add(new Walker(dungeonY/2, dungeonX/2)
	
	while dugCells/allCells < digPercent
		nextWalker = queue.pop
		nextWalker.walk
		if nextWalker is still inside dungeon
			if dungeonGrid[nextWalker.y][nextWalker.x] = stone
				dungeonGrid[nextWalker.y][nextWalker.x] = floor
				dugCells++
				
			if (nextWalker has stone neighbors) or (queue is empty)
				queue.add(nextWalker)
				
				if RandomInteger < spawnChance
					queue.add(new Walker(nextWalker.y, nextWalker.x)
		
		if queue is empty
			queue.add(new Walker(dungeonY/2, dungeonX/2)
			
The queue's methods are `O(1)` in time complexity. Due to this and the fact that the algorithm will run until `dugCells/allCells < digPercent`, we can see that the algorithm's time complexity depends on the dungeon's size. Therefore the time complexity is `O(n^2)` (or `O(m)`).

The maximum value for `spawnChance` should be around 15%; any higher and the generated dungeon will become a large cave-like hole. A low `spawnChance` ensures that the queue will stay relatively small, and therefore the dungeon's size will dominate the space complexity. This means that the space complexity is `O(n^2)` (or `O(m)`).

### Forest fire (flood fill)

Below is the pseudocode for the forest fire algorithm.

	set startingCell's integer to replacement
	queue.add(startingCell)	
	while queue is not empty
		currentCell = queue.pop
		
		// "check left"
		if cell to the left of currentCell is of target integer
			set the cell to replacement integer
			queue.add(cell)
			
		repeat "check left" for cells to the right, above, and below
		
The algorithm will run until there are no cells of target integer connected to the start cell via a path of the target integer. This means that, if the whole dungeon is connected, the algorithm will run for `n^2 = m` times, and the time complexity is therefore `O(n^2)` (or `O(m)`).

The space complexity is `O(n^2+q)` where `q` is the size of the queue. As cells are removed from the queue as they are iterated through, we know that `n^2 > q`, and therefore the space complexity is `O(n^2)` (or `O(m)`).

### Scan fill (flood fill)

Below is the pseudocode for the scan fill algorithm.

	set startingCell's integer to replacement
	queue.add(startingCell)	
	while queue is not empty
		currentCell = queue.pop
		leftX, rightX = currentCell.x
		
		move leftX to left until the next cell is no longer of target integer
		
		move rightX to right until the next cell is no longer of target integer
		
		for all cells between leftX and rightX
			set cell to replacement integer
			
			if cell above is of target integer
				queue.add(cell above)
				
			if cell below is of target integer
				queue.add(cell below)
				
The time and space complexities are the same as with the forest fire algorithm (same explanations), so both are `O(n^2)` (or `O(m)`).

## Shortcomings and possible improvements

- `complexWalk` is highly unreliable; needs more looking into to see what sort of parameters are best for running and if there are possible improvements to the algorithm
- `GUI.java` is bloated and needs cleaning up
- Cellular automaton's `initializeDungeon` could be improved with adding all the cells to a list, randomizing the order, and from the beginning start changing the cells to stone
- The project uses `ThreadLocalRandom` for RNG as performance with a self-made pseudorandom number generator would be too slow

## Sources

1. [Wikipedia, *Cellular automaton*](https://en.wikipedia.org/wiki/Cellular_automaton)
2. [Johnson, L. *et.al.*, *Cellular automata for real-time generationof infinite cave levels*](http://julian.togelius.com/Johnson2010Cellular.pdf)
3. [Wikipedia, *Random walk*](https://en.wikipedia.org/wiki/Random_walk)
4. [Noel Berry, *Procedural Generation - The Caves*](http://noelberry.ca/#thecaves)
5. [Wikipedia, *Flood fill*](https://en.wikipedia.org/wiki/Flood_fill)