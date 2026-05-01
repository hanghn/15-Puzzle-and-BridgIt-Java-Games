# CS 2510 — Java Game Projects

Two interactive Java game implementations built for **CS 2510 (Fundamentals of Computer Science 2)** at Northeastern University, Fall 2023.

**Collaborators:** Gavin Bond ([bond.g@northeastern.edu](mailto:bond.g@northeastern.edu)) and Hang Hang ([hang.h@northeastern.edu](mailto:hang.h@northeastern.edu))

## Projects

### 🧩 15-Puzzle (Sliding Tile Game)

A classic implementation of the **15-puzzle**: a 4×4 grid with 15 numbered tiles and one empty space. Players slide tiles into the empty space to arrange them in numerical order from 1 to 15.

**Key features:**
- Tile movement via keyboard input (up / down / left / right)
- Real-time visual feedback — tiles turn **orange** when in their correct position, **blue** otherwise
- Object-oriented design with separate `Tile` and game-state classes
- Randomized starting board (solvable shuffles)

**File:** [`15_Game`](./15_Game)

<img width="401" height="429" alt="Screenshot 2026-04-30 at 8 05 36 PM" src="https://github.com/user-attachments/assets/571dc764-349c-4966-979a-45efeebbd53f" />
Example game state: Mid-game with orange tiles (3, 7, 10, 15) in their correct positions. The empty space (bottom-right) has reached
its goal corner.  


--------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------
### 🌉 BridgIt (Two-Player Path Game)

A turn-based grid game where two players (Pink and Magenta) alternate placing pieces, each trying to build an unbroken chain of their color from one side of the board to the opposite side. The first player to complete an end-to-end path wins.

**Key features:**
- Click-based GUI for piece placement
- **Depth-first search (DFS)** path-checking after every move to detect a winning chain
- Custom generic `ICollection<T>` interface with a `Stack<T>` implementation backed by `ArrayDeque`
- Strict alternation enforced via game-state tracking

**File:** [`BridgIt`](./BridgIt)

<img width="552" height="579" alt="Screenshot 2026-04-30 at 8 12 32 PM" src="https://github.com/user-attachments/assets/dc502ace-bb67-4067-a752-25bceef3abd1" />
Example game state: End of game. Player 2 (magenta) wins by completing an unbroken top-to-bottom chain. The game's DFS path-detection over the cell graph automatically    
declared the win after the final move. 

## Tech stack

- **Java** (OOP-focused)
- [`javalib.impworld`](https://course.ccs.neu.edu/cs2510/) — Northeastern's interactive game/animation library (used for the windowed UI and event handling)
- [`tester`](https://course.ccs.neu.edu/cs2510/) — Northeastern's unit-testing framework

## OOP concepts demonstrated

- **Interfaces and polymorphism** — `ICollection<T>` interface with `Stack<T>` implementation
- **Generics** — type-parameterized collections
- **Encapsulation** — game state cleanly separated from rendering logic
- **Composition** — boards composed of cells, cells of references to neighbors
- **Algorithm integration** — DFS over a graph of cells for win-condition detection

## Course context

The course centers on **the design recipe**, **OOP**, **abstract data types**, and **algorithmic problem solving** in Java.
