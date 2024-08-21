# CS2510-Project
These two game projects were collaboratively developed by Gavin Bond (bond.g@northeastern.edu) and myself (hang.h@northeastern.edu) as part of our CS2510 Fundamentals of Computer Science 2 course.

Fifteen Game:
- Overview: A simple implementation of the classic sliding puzzle game known as the "15-puzzle" where players slide puzzles on a 4x4 grid with 15 numbered tiles and one empty space.
- Objective: Arrange the tiles in numerical order from 1 to 15, with the empty space in the bottom-right corner.
- Gameplay: Tiles can be moved by sliding them into the empty space, either up, down, left, or right.

BridgIt Game:
- Overview: A two-player grid-based game where players create a path of their color.
Components:
- Cell: Represents a square on the board with a color and neighbors.
- Stack: Used in depth-first search (DFS) to find paths.
- BridgItGame: Manages the board, player turns, and win conditions.
- Gameplay: Players take turns clicking cells to form a continuous path. The game checks for a winning path after each turn.
