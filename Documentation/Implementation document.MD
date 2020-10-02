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

The cellular automaton generally needs between 3 to 6 iterations until there are little changed between iterations. This means that the two inner for-loops dominate the time complexity, and therefore the time complexity is `O(dungeonX * dungeonY)`. We can approximate this as `O(n^2)`, where `n = max(dungeonX, dungeonY)`. Another way of saying this is that the time complexity is `O(m)`, where `m = dungeonX * dungeonY` is the size of the dungeon.

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