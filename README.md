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

**Source:** [`fifteen-game/Fifteen.java`](./fifteen-game/Fifteen.java) · **Launcher:** [`fifteen-game/Launcher.java`](./fifteen-game/Launcher.java)

<img width="399" height="428" alt="Screenshot 2026-04-30 at 8 43 26 PM" src="https://github.com/user-attachments/assets/c09f3412-d101-46a7-a608-18d8dca7192f" />
Example game state: mid-game with tiles 1, 2, 3, 10, 14 (orange tiles) in the correct places. 

### 🌉 BridgIt (Two-Player Path Game)

A turn-based grid game where two players (Pink and Magenta) alternate placing pieces, each trying to build an unbroken chain of their color from one side of the board to the opposite side. The first player to complete an end-to-end path wins.

**Key features:**
- Click-based GUI for piece placement
- **Depth-first search (DFS)** path-checking after every move to detect a winning chain
- Custom generic `ICollection<T>` interface with a `Stack<T>` implementation backed by `ArrayDeque`
- Strict alternation enforced via game-state tracking

**Source:** [`bridgit/BridgIt.java`](./bridgit/BridgIt.java) · **Launcher:** [`bridgit/Launcher.java`](./bridgit/Launcher.java)

## Run locally

Each game lives in its own folder with a `Launcher.java` containing `main()`. Full instructions are in **[`HOW_TO_RUN.md`](./HOW_TO_RUN.md)**, but the quick version:

```bash
# Set the path to Northeastern's javalib + tester JARs
JARS="/path/to/javalib.jar:/path/to/tester.jar"

# 15-puzzle
cd fifteen-game
javac -cp "$JARS" Fifteen.java Launcher.java
java  -cp ".:$JARS" Launcher

# BridgIt
cd bridgit
javac -cp "$JARS" BridgIt.java Launcher.java
java  -cp ".:$JARS" Launcher
```

Requires **Java 11+** (tested with Java 21) and the course-distributed `javalib.jar` + `tester.jar`.

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

CS 2510 — Fundamentals of Computer Science 2 · Khoury College of Computer Sciences, Northeastern University · Fall 2023

The course centers on **the design recipe**, **OOP**, **abstract data types**, and **algorithmic problem solving** in Java.
