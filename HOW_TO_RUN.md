# How to Run the Games

Both games are written in Java and use Northeastern's `javalib` and `tester` libraries (the JARs distributed in the CS 2510 course).

## Prerequisites

- **Java 11+** (tested with Java 21)
- **`javalib.jar`** and **`tester.jar`** — the course-provided libraries

```bash
java -version    # confirm Java is installed
```

## Folder layout

Each game is self-contained in its own folder:

```
fifteen-game/
├── Fifteen.java     # the puzzle source code
└── Launcher.java    # entry point with main()

bridgit/
├── BridgIt.java     # the BridgIt source code
└── Launcher.java    # entry point with main()
```

## Run the 15-puzzle

```bash
cd fifteen-game

# Set your local path to the course JARs
JARS="/path/to/javalib.jar:/path/to/tester.jar"

javac -cp "$JARS" Fifteen.java Launcher.java
java -cp ".:$JARS" Launcher
```

A 400×400 window opens with a shuffled 4×4 grid. **Use arrow keys** to slide tiles into the empty space. Tiles turn **orange** when correctly placed, **blue** otherwise. The goal is 1–15 in order with the empty space in the bottom-right.

## Run BridgIt

```bash
cd bridgit

JARS="/path/to/javalib.jar:/path/to/tester.jar"

javac -cp "$JARS" BridgIt.java Launcher.java
java -cp ".:$JARS" Launcher
```

A 550×550 window opens with an 11×11 board. **Click cells** to claim them — players alternate turns. Player 1 wins by connecting left edge to right edge; Player 2 wins by connecting top to bottom. The DFS-based path detection automatically declares a winner when an unbroken chain is formed.

## Notes

- The `Launcher.java` files were added on top of the original course-submitted source so the games can be launched directly via `java`. The original sources rely on Northeastern's `tester.Tester` framework (no `main()` method), which only runs inside Eclipse with the tester plugin.
- The legacy files at the repo root (`15_Game` and `BridgIt`, no extensions) are the originally submitted versions — kept for historical reference.
