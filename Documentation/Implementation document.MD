# Implementation document

## Project structure

Below is an image of the package structure.

![package structure](/Documentation/Pictures/Diagram.png)

The `main` package contains the `Main` method. The `ui` package contains graphical user interface created with JavaFX that is used to run the program. The `domain` package contains all the algorithms and classes used in the algorithms and performance testing, and the `util` package contains `CellQueue` data structure used in two algorithms.

## Data structures

### CellQueue

#### Pseudocode
    
	CellQueue {
		int lastItem	// index of last item
		int firstItem	// index of first item
		int size		// how large the queue array is
		Cell[] queue
	
    void enqueue(Cell cell) {
        if queue is full
            growQueue()
        lastItem++
        queue[this.lastItem] = cell;
    }

    Cell dequeue() {
        if queue is quarter full && size >= 20
            reduceQueue()
        firstItem++
        return this.queue[firstItem]
    }
    
    void growQueue() {
        Cell[] oldQueue = queue;
        size *= 2;
        queue = new Cell[size];
		move all cells from oldQueue to the front of queue
        lastItem -= firstItem + 1
        firstItem = -1
    }

    void reduceQueue() {
        Cell[] oldQueue = queue;
        size /= 2;
        queue = new Cell[size];
        move all cells from oldQueue to the front of queue
        lastItem -= firstItem + 1;
        firstItem = -1;
    }

#### Complexity analysis

From pseudocode we can see that `enqueue()` and `dequeue()` are both `O(1)` in time complexities, whereas `growQueue()` and `reduceQueue()` are `O(n)` where `n` is the number of cells in the queue.

As only one array is reserved for the data structure, it's space complexity is `O(size)`. When `growQueue()` or `reduceQueue()` is run, the space complexity is temporarily `O(2*size)`, which simplifies to `O(size)`.

## Algorithms

**NOTE:** The algorithms depend heavily on RNG; therefore it is imperative that RNG is quick and without errors. While creating the algorithms, I experimented with self-made pseudorandom number generators, but the performance was markedly and staggeringly worse than with a prebuilt RNG. Therefore, the algorithms currently use `ThreadLocalRandom` for RNG. 

### Cellular automaton

#### Pseudocode

	// initialize dungeon
	while currentStonePercent < stonePercentAtStart
		Cell randomCell = random  cell
		if randomCell is not stone
			change randomCell to stone
			
	// algorithm
	for int i = 0; i < iterations; i++
		for int y = 0; y < dungeonY; y++
			for int x = 0; x < dungeonX; x++
				int stoneCellNeighbors = count all stone cells in a 3x3 grid around the cell
				if stoneCellNeighbors >= 5
					temporaryGrid[y][x] = stone
		
		dungeonGrid = temporaryGrid
		
#### Complexity analysis

In general, dungeon initialization is not a bottleneck as `stonePercentAtStart` should be between 40-55% (otherwise the generated dungeon will look bad). Therefore, we need to consider the algorithm itself.

The cellular automaton generally needs between 3 to 6 iterations until there are no changes between iterations. This means that the two inner for-loops dominate the time complexity, and therefore the time complexity is `O(dungeonX * dungeonY)`. We can approximate this as `O(n^2)`, where `n = max(dungeonX, dungeonY)`. Another way of saying this is that the time complexity is `O(m)`, where `m = dungeonX * dungeonY` is the size of the dungeon.

As the algorithm needs to store two arrays for the cells (`dungeonGrid` and `temporaryGrid`), the space complexity is `O(2n^2)` which simplifies to `O(n^2)` (or `O(m)`).

### Random walk

#### Pseudocode

	// add a new walker at the center of the dungeon
	queue.add(new Walker(dungeonY/2, dungeonX/2)
	
	while dugCells/allCells < digPercent
		nextWalker = queue.pop
		nextWalker.walk
		if nextWalker is still inside dungeon
			if walker is on stone cell
				change cell to floor
				dugCells++
				
			if nextWalker has stone neighbors or queue is empty
				queue.add(nextWalker)
				
				if RandomInteger < spawnChance
					queue.add(new Walker(nextWalker's location)
		
		// if the last walker walked out of the dungeon, spawn a new walker
		if queue is empty
			queue.add(new Walker(dungeonY/2, dungeonX/2)
			
#### Complexity analysis

As shown above, the queue's methods are `O(1)` in time complexity. Due to this and the fact that the algorithm will run until `dugCells/allCells < digPercent`, we can see that the algorithm's time complexity depends on the dungeon's size. Therefore the time complexity is `O(n^2)` (or `O(m)`).

The maximum value for `spawnChance` should be around 15%; any higher and the generated dungeon will become a large cave-like hole. A low `spawnChance` ensures that the queue will stay relatively small, and therefore the dungeon's size will dominate the space complexity. This means that the space complexity is `O(n^2)` (or `O(m)`).

### Forest fire (flood fill)

#### Pseudocode

	set startingCell's integer to replacement
	queue.add(startingCell)	
	while queue is not empty
		currentCell = queue.pop
		
		// "check left"
		if cell to the left of currentCell is in dungeon && cell is of target integer
			set the cell to replacement integer
			queue.add(cell)
			
		repeat "check left" for cells to the right, above, and below

#### Complexity analysis

The algorithm will run until there are no cells of target integer connected to the start cell via a path of the target integer. This means that, if the whole dungeon is connected, the algorithm will run for `n^2 = m` times, and the time complexity is therefore `O(n^2)` (or `O(m)`).

The space complexity is `O(n^2+q)` where `q` is the size of the queue. As cells are removed from the queue as they are iterated through, we know that `n^2 > q`, and therefore the space complexity simplifies to `O(n^2)` (or `O(m)`).

### Scan fill (flood fill)

#### Pseudocode

	set startingCell's integer to replacement
	queue.add(startingCell)	
	while queue is not empty
		currentCell = queue.pop
		leftX, rightX = currentCell.x
		
		move leftX to left until the next cell is no longer of target integer
		
		move rightX to right until the next cell is no longer of target integer
		
		boolean upperStartFound, lowerStartFound = false	// tell whether the starting cells for upper and lower lines have been found
		
		for all cells between leftX and rightX
			set cell to replacement integer
			
			if cell below is in dungeon
				if cell below is of target integer && not lowerStartFound
					lowerStartFound = true
					queue.add(cell below)
				else if cell below is stone		// stone cell(s) separates the lower line into two lines
					lowerStartFound = false
				
			if cell above is in dungeon
				if cell above is of target integer && not upperStartFound
					upperStartFound = true
					queue.add(cell above)
				else if cell above is stone		// stone cell(s) separates the upper line into two lines
					upperStartFound = false

#### Complexity analysis

The time and space complexities are the same as with the forest fire algorithm (same explanations), so both are `O(n^2)` (or `O(m)`).

### Finding the largest connected area (flood fill)

#### Pseudocode

    Cell largestAreaStart = null
    int largestAreaCells = 0
    int remainingFloorCells = iterate through the dungeon and count all floor cells
	
    loop:
    for (int y = 0; y < dungeonY; y++)
        for (int x = 0; x < dungeonX; x++)
            if dungeon[y][x] is floor
                int cells = cells found by forestFire or scanFill
                if (cells > largestAreaCells)
                    largestAreaCells = cells
                    largestAreaStart = new Cell(y, x)
                
                remainingFloorCells -= cells
                if remainingFloorCells == 0 or remainingFloorCells < largestAreaCells
                    break loop
        
    // if a largest area was found, flood fill it to a new integer and change everything else to stone
    if (largestAreaStart != null)
		start forestFire or scanFill at largestAreaStart and change cells to 3
        for (int y = 0; y < dungeonY; y++)
            for (int x = 0; x < dungeonX; x++)
                if (dungeon[y][x] is not 3)
                    change cell to stone

#### Complexity analysis

Let's start with time complexity analysis and break the algorithm down to several parts.

1. Counting all floor cells at the beginning means that the algorithm will iterate through the dungeon an additional time, which adds `O(n^2)` to the time complexity.

2. The loop part of the algorithm will run forestFire or scanFill until no floor cells remain or the largest found area's cell count is higher than the remaining floor cell count. This means we have two worst case scenarios:

* The largest connected area is most of the dungeon. This means that the flood fill algorithm's time complexity is `O(n^2)` and the two for-loops will run only a couple time; therefore, the total time complexity is `O(n^2)`
* The connected areas are small. This means that the two for loops will run many times, i.e. `O(n^2)`, and the flood fill algorithm will run several times but with small areas; i.e. `O(k j^2)` where `k` is the times the algorithm will run and `j^2` is the average size of the area. Since `n^2 > k j^2`, the time complexity is `O(n^2)`

3. In worst case scenario, the largest connected area is most of the dungeon, so running flood fill algorithm to fill it to the integer value 3 has time complexity of `O(n^2)`.

4. The two last for loops have time complexity of `O(n^2)`.

In total, the time complexity is a sum of the time complexities of parts 1 through 4. This means that, in worst case scenario, the time complexity is `O(4n^2)`, which simplifies down to `O(n^2)`.

The space complexity is `O(n^2)` (or `O(m)`).

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