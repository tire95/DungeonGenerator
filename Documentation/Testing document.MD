# Testing document

## Unit testing

Unit testing was done with JUnit. A few different scenarios were used to ensure that the algorithms and the data structures behave properly even in some borderline cases. Different sized dungeons with different configurations were used in the tests.

Tests for the dungeon generation algorithms are challenging to create as most of the logic is based on random number generation. Therefore, only a few test cases were implemented, and the validity of the algorithms is best checked with running the algorithms with different inputs.

## Performance testing

The user has an option to run performance testing once the program is executed. The user can give how many runs will be averaged, and what the maximum size of the dungeon is. The results are displayed in an array.