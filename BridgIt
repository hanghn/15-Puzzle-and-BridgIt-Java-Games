import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.ArrayDeque;

// interface to hold the functionality for stack
interface ICollection<T> {

  //compute the size of this collection
  int size();

  //EFFECT: adds an item to this collection
  void add(T t);

  //EFFECT: removes an item from this collection
  //returns the item that was removed
  T remove();
}

// class to represent a stack of items (ArrayDeque)
class Stack<T> implements ICollection<T> {
  ArrayDeque<T> items;

  Stack() {
    this.items = new ArrayDeque<T>();
  }

  //compute the size of this stack
  public int size() {
    return this.items.size();
  }

  //EFFECT: adds an item to the front of this collection
  public void add(T t) {
    this.items.addFirst(t);
  }

  //EFFECT: removes an item from the front of this collection
  //returns the item that was removed
  public T remove() {
    return this.items.removeFirst();
  }
}

// class to represent cells with colors and linked neighbor cells
class Cell {
  Color color;
  ArrayList<Cell> neighborList;

  // cell constructor
  Cell(Color color) {
    this.color = color;
    this.neighborList = new ArrayList<Cell>();
  }

  // Method to draw a cell at a specific position on a background
  public WorldScene drawAt(int c, int r, WorldScene background) {
    int cellSize = 50;
    int x = c * cellSize + cellSize / 2;
    int y = r * cellSize + cellSize / 2;

    WorldImage cellImage = new RectangleImage(cellSize, cellSize, OutlineMode.SOLID, this.color);
    background.placeImageXY(cellImage, x, y);

    return background;
  }

  // determines if this cell is the same color as that cell
  public boolean sameColor(Cell that) {
    return this.color.equals(that.color);
  }

  // determines if this cell is a certain color
  public boolean colorEquals(Color c) {
    return this.color.equals(c);
  }

  // EFFECT: sets the color for a cell
  public void setColor(Color c) {
    this.color = c;
  }

  // adds a cell to this cell's neighbor list
  public void addNeighbor(Cell neighbor) {
    this.neighborList.add(neighbor);
  }
}

// class to represent a BridgIt game with a board and specification of player turn
class BridgItGame extends World {
  int size;
  ArrayList<ArrayList<Cell>> board;
  boolean playerOneTurn;

  // game constructor; throws an exception if board is smaller than 3 or has an even number size
  BridgItGame(int size) {
    if (size < 3 || size % 2 == 0) {
      throw new IllegalArgumentException("Size must be an odd number greater than or equal to 3.");
    }
    else {
      this.size = size;
    }
    this.board = new ArrayList<ArrayList<Cell>>();
    this.playerOneTurn = true;
    initializeBoard(size);
  }

  // EFFECT: Initialize the board with the correct layout of cells
  public void initializeBoard(int size) {
    for (int r = 0; r < size; r++) {
      ArrayList<Cell> row = new ArrayList<>();
      for (int c = 0; c < size; c++) {
        Color color = determineCellColor(r, c);
        row.add(new Cell(color)); 
      }
      this.board.add(row);
    }

    // links neighboring cells after board is initialized
    assignNeighbors(size);
  }

  // determines the color of a cell based on it's position on the board
  public Color determineCellColor(int r, int c) {
    if ((r % 2 == 0) && (c % 2 == 1)) {
      return Color.MAGENTA;
    }
    else if ((r % 2 == 1) && (c % 2 == 0)) {
      return Color.PINK;
    }
    else {
      return Color.WHITE;
    }
  }

  // EFFECT: assigns all neighbors for all cells by adding to their neighbor lists. The number
  // of neighbors is determined by the cell position.
  public void assignNeighbors(int size) {
    for (int r = 0; r < size; r++) {
      for (int c = 0; c < size; c++) {
        Cell current = this.board.get(r).get(c);

        if (r > 0) {
          current.addNeighbor(this.board.get(r - 1).get(c));
        }
        if (r < size - 1) {
          current.addNeighbor(this.board.get(r + 1).get(c));
        }
        if (c > 0) {
          current.addNeighbor(this.board.get(r).get(c - 1));
        }
        if (c < size - 1) {
          current.addNeighbor(this.board.get(r).get(c + 1));
        }
      }
    }
  }

  // Render the game board
  // EFFECT: Add's all tiles with their respective color onto an empty scene
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(board.size() * 50, board.size() * 50);
    for (int r = 0; r < this.board.size(); r++) {
      for (int c = 0; c < this.board.get(r).size(); c++) {
        scene = this.board.get(r).get(c).drawAt(c, r, scene);
      }
    }
    return scene;
  }

  // EFFECT: Changes the color of a tile tile that has been clicked  based on whether or not 
  // the tile is valid, (is white and not an edge cell), and based on the player turn.
  public void onMousePressed(Posn p) {

    int cellSize = 50;
    int clickedRow = p.y / cellSize;
    int clickedColumn = p.x / cellSize;

    // checking if the click is on the board and not on an edge cell
    if (clickedRow >= 1 && clickedRow <= 9 
        && clickedColumn >= 1 && clickedColumn <= 9) {

      Cell clickedCell = this.board.get(clickedRow).get(clickedColumn);

      if (clickedCell.colorEquals(Color.WHITE)) {
        if (playerOneTurn) {
          clickedCell.setColor(Color.PINK);
        }
        else {
          clickedCell.setColor(Color.MAGENTA);
        }

        // swap player turn after completion of the valid click
        playerOneTurn = !playerOneTurn;

        // check for win condition after every click is complete
        if (this.checkForWin(Color.PINK)) {
          this.endOfWorld("Player one has won!");
        }
        else if (this.checkForWin(Color.MAGENTA)) {
          this.endOfWorld("Player two has won!");
        }
      }
    }
  }

  // determines whether there is a path between any two cells
  public boolean hasPathDFS(Cell from, Cell to, Stack<Cell> workList) {
    ArrayList<Cell> alreadySeen = new ArrayList<Cell>();

    workList.add(from);

    while (workList.size() > 0) {
      Cell next = workList.remove();

      // return true if path has been found (to was found in the worklist)
      if (next == to) {
        return true;
      }     
      else if (alreadySeen.contains(next)) { // don't check if next was already seen
      }
      else {
        for (Cell c : next.neighborList) {
          // only add neighboring cells if they have the same color as next
          if (next.sameColor(c)) {
            workList.add(c);
          }
        }
        alreadySeen.add(next);
      }
    }   
    return false;
  }

  // check if the game has been won for a certain player color
  public boolean checkForWin(Color playerColor) {

    // lists for either the top and bottom rows or the leftmost and rightmost columns
    ArrayList<Cell> startCells = new ArrayList<>();
    ArrayList<Cell> endCells = new ArrayList<>();

    if (playerColor == Color.MAGENTA) {
      // top to bottom rows for P1
      for (int i = 0; i < this.board.size(); i++) {
        startCells.add(this.board.get(0).get(i));
        endCells.add(this.board.get(this.board.size() - 1).get(i));
      }
    } else {
      // left to right columns for P2
      for (ArrayList<Cell> row : this.board) {
        startCells.add(row.get(0));
        endCells.add(row.get(this.board.size() - 1));
      }
    }

    // check for a path from any cell in startCells to any cell in endCells
    for (Cell startCell : startCells) {
      if (startCell.colorEquals(playerColor)) {
        for (Cell endCell : endCells) {
          if (hasPathDFS(startCell, endCell, new Stack<Cell>())) {
            return true;
          }
        }
      }
    }
    return false;
  }

  // creates the last scene with a winning message over the final scene of game
  public WorldScene lastScene(String msg) {
    WorldScene scene = this.makeScene();
    int sceneWidth = this.size * 50;
    int sceneHeight = this.size * 50;
    WorldImage message = new TextImage(msg, 30, FontStyle.BOLD, Color.BLACK);

    scene.placeImageXY(message, sceneWidth / 2, sceneHeight / 2);

    return scene;
  }
}

// class for examples and test methods
class ExampleBridgItGame {

  // examples
  BridgItGame game5 = new BridgItGame(5);
  BridgItGame game3 = new BridgItGame(3);
  WorldScene mtScene1 = game5.getEmptyScene();
  WorldScene mtScene2 = game5.getEmptyScene();
  Cell cell1 = new Cell(Color.WHITE);
  Cell cell2 = new Cell(Color.PINK);

  // initializes data
  void initData() {
    this.game5 = new BridgItGame(5);
    this.game3 = new BridgItGame(3);
    this.mtScene1 = game5.getEmptyScene();
    this.mtScene2 = game5.getEmptyScene();
    this.cell1 = new Cell(Color.WHITE);
    this.cell2 = new Cell(Color.PINK);
  }

  // big bang to run the game
  public void testGame(Tester t) {
    int gridSize = 11;
    BridgItGame game = new BridgItGame(gridSize);
    game.bigBang(gridSize * 50, gridSize * 50);
  }

  // test the size method of the class stack
  void testStackAdd(Tester t) {
    Stack<Integer> stack = new Stack<>();

    t.checkExpect(stack.size(), 0);

    stack.add(2);

    t.checkExpect(stack.size(), 1);
  }

  //test the size method of the class stack
  void testStackSize(Tester t) {
    Stack<Integer> stack = new Stack<>();

    t.checkExpect(stack.size(), 0);

    stack.add(2);

    t.checkExpect(stack.size(), 1);
  }

  //test the size method of the class stack
  void testStackRemove(Tester t) {
    Stack<Integer> stack = new Stack<>();

    stack.add(1);
    stack.add(2);

    t.checkExpect(stack.remove(), 2);
  }

  // testing the initialize game method effects
  void testInitializeGame(Tester t) {
    initData();

    // the initialize game method is called upon the construction of a game
    BridgItGame initGame = new BridgItGame(5);

    // testing the cells are properly set with the correct colors based on their location
    t.checkExpect(initGame.board.get(0).get(0).colorEquals(Color.WHITE), true);
    t.checkExpect(initGame.board.get(2).get(3).colorEquals(Color.MAGENTA), true);
    t.checkExpect(initGame.board.get(3).get(4).colorEquals(Color.PINK), true);
    // testing the cells have the correct amount of neighbors based on location
    // (corner cells have 2,  edge cells have 3, and middle cells have 4)
    t.checkExpect(initGame.board.get(0).get(0).neighborList.size(), 2);
    t.checkExpect(initGame.board.get(1).get(0).neighborList.size(), 3);
    t.checkExpect(initGame.board.get(1).get(1).neighborList.size(), 4);
    // further neighbor lists tests are located in the testAssignNeighbors method
  }

  // testing the determine cell color method
  void testDetermineCellColor(Tester t) {
    initData();

    t.checkExpect(game5.determineCellColor(0, 0), Color.WHITE);
    t.checkExpect(game5.determineCellColor(1, 0), Color.PINK);
    t.checkExpect(game5.determineCellColor(0, 1), Color.MAGENTA);
    t.checkExpect(game5.determineCellColor(2, 0), Color.WHITE);
    t.checkExpect(game5.determineCellColor(0, 2), Color.WHITE);
    t.checkExpect(game5.determineCellColor(2, 1), Color.MAGENTA);
    t.checkExpect(game5.determineCellColor(1, 2), Color.PINK);
  }

  // testing the assign neighbors method effects. No method call because they are assigned
  // upon initialization
  void testAssignNeighbors(Tester t) {
    initData();

    // the assignNeighbors method is called upon the construction of a game
    BridgItGame initGame = new BridgItGame(5);

    // testing the cells have the correct amount of neighbors based on location
    // (corner cells have 2,  edge cells have 3, and middle cells have 4)
    t.checkExpect(initGame.board.get(0).get(4).neighborList.size(), 2);
    t.checkExpect(initGame.board.get(1).get(4).neighborList.size(), 3);
    t.checkExpect(initGame.board.get(3).get(3).neighborList.size(), 4);
    // testing that cells neighbor lists contain the correct cells
    // (0, 0)
    t.checkExpect(initGame.board.get(0).get(0).neighborList.contains(
        initGame.board.get(1).get(0)), true);
    t.checkExpect(initGame.board.get(0).get(0).neighborList.contains(
        initGame.board.get(0).get(1)), true);
    t.checkExpect(initGame.board.get(0).get(0).neighborList.contains(
        initGame.board.get(1).get(1)), false);
    // (1, 1)
    t.checkExpect(initGame.board.get(1).get(1).neighborList.contains(
        initGame.board.get(1).get(0)), true);
    t.checkExpect(initGame.board.get(1).get(1).neighborList.contains(
        initGame.board.get(0).get(1)), true);
    t.checkExpect(initGame.board.get(1).get(1).neighborList.contains(
        initGame.board.get(2).get(1)), true);
    t.checkExpect(initGame.board.get(1).get(1).neighborList.contains(
        initGame.board.get(1).get(2)), true);
    t.checkExpect(initGame.board.get(1).get(1).neighborList.contains(
        initGame.board.get(0).get(0)), false);
    // testing that a cell is not it's own neighbor
    t.checkExpect(initGame.board.get(1).get(1).neighborList.contains(
        initGame.board.get(1).get(1)), false); 
  }

  // testing the draw at method
  void testDrawAt(Tester t) {
    initData();

    cell1.drawAt(0, 0, mtScene1);
    cell1.drawAt(3, 7, mtScene2);

    WorldScene expectedWorldScene1 = game5.getEmptyScene();
    WorldScene expectedWorldScene2 = game5.getEmptyScene();

    expectedWorldScene1.placeImageXY(new RectangleImage(
        50, 50, OutlineMode.SOLID, Color.WHITE), 0, 0);
    expectedWorldScene2.placeImageXY(new RectangleImage(
        50, 50, OutlineMode.SOLID, Color.WHITE), 3 * 50, 7 * 50);

    t.checkExpect(mtScene1, expectedWorldScene1);
    t.checkExpect(mtScene2, expectedWorldScene2);   
  }

  // testing the make scene method
  void testMakeScene(Tester t) {
    initData();

    // created empty worldscene
    WorldScene expectedWorldScene3 = new WorldScene(150, 150);


    // add all tiles with respective colors to the empty worldscene
    new Cell(Color.WHITE).drawAt(2, 2,
        new Cell(Color.MAGENTA).drawAt(1, 2,
            new Cell(Color.WHITE).drawAt(0, 2,
                new Cell(Color.PINK).drawAt(2, 1,
                    new Cell(Color.WHITE).drawAt(1, 1,
                        new Cell(Color.PINK).drawAt(0, 1,
                            new Cell(Color.WHITE).drawAt(2, 0,
                                new Cell(Color.MAGENTA).drawAt(1, 0,
                                    new Cell(Color.WHITE).drawAt(0, 0,
                                        expectedWorldScene3)))))))));

    t.checkExpect(game3.makeScene(), expectedWorldScene3);
  }

  // testing if incorrect BridgIt construction throws an exception
  void testBridgItConstructor(Tester t) {

    t.checkConstructorException(
        new IllegalArgumentException("Size must be an odd number greater than or equal to 3."),
        "BridgItGame", 2);
    t.checkConstructorException(
        new IllegalArgumentException("Size must be an odd number greater than or equal to 3."),
        "BridgItGame", 6);
  }

  // testing the onMousePressed method
  void testonMousePressed(Tester t) {
    initData();

    //simulate a valid click on a white cell by player 1
    game5.onMousePressed(new Posn(50, 50));
    //detect if the clicked cell changed to pink and the current player is now 2
    t.checkExpect(game5.board.get(1).get(1).colorEquals(Color.PINK), true);
    t.checkExpect(game5.playerOneTurn, false);
    //simulate a valid click on a white cell by player 2
    game5.onMousePressed(new Posn(150, 50));
    t.checkExpect(game5.board.get(1).get(3).colorEquals(Color.MAGENTA), true);
    t.checkExpect(game5.playerOneTurn, true);
    // simulate an invalid click on a white cell
    // (color should stay white and player shouldn't switch)
    game5.onMousePressed(new Posn(0, 0));
    t.checkExpect(game5.board.get(0).get(0).colorEquals(Color.WHITE), true);
    t.checkExpect(game5.playerOneTurn, true);
    // simulate an invalid click on opponents cell
    // (color should stay the same and player shouldn't switch) 
    game5.onMousePressed(new Posn(150, 50));
    t.checkExpect(game5.board.get(1).get(3).colorEquals(Color.MAGENTA), true);
    t.checkExpect(game5.playerOneTurn, true);
    // simulate an invalid click on player's already clicked cell
    // (color should stay the same and player shouldn't switch) 
    game5.onMousePressed(new Posn(50, 50));
    t.checkExpect(game5.board.get(1).get(1).colorEquals(Color.PINK), true);
    t.checkExpect(game5.playerOneTurn, true);
  }

  // testing the has path method depth first search method
  void testHasPathDFS(Tester t) {
    initData();

    // checks for path between two pink cells with a white cell between them
    t.checkExpect(game5.hasPathDFS(
        game5.board.get(1).get(0), game5.board.get(1).get(2), new Stack<Cell>()), false);

    // changes white gap to pink to complete the path between the previous 2 cells
    game5.board.get(1).get(1).setColor(Color.PINK);

    // checks if there is a path between the previous 2 cells after making gap pink
    t.checkExpect(game5.hasPathDFS(
        game5.board.get(1).get(0), game5.board.get(1).get(2), new Stack<Cell>()), true);

    // changes the previous gap cell to magenta
    // (can't happen in game, solely for testing purposes)
    game5.board.get(1).get(1).setColor(Color.MAGENTA);

    // checks if there is a path after mutating connecting cell color
    t.checkExpect(game5.hasPathDFS(
        game5.board.get(1).get(0), game5.board.get(1).get(2), new Stack<Cell>()), false);

    // changes the (2, 2) and (3, 3) cell to magenta to complete path between (0, 1) and (4, 2)
    game5.board.get(2).get(2).setColor(Color.MAGENTA);
    game5.board.get(3).get(3).setColor(Color.MAGENTA);

    // checks if there is a path between (0, 1) and (4, 3)
    // to ensure hasPath method works with a non-linear path and with magenta
    t.checkExpect(game5.hasPathDFS(
        game5.board.get(0).get(1), game5.board.get(4).get(3), new Stack<Cell>()), true);
  }

  // testing the check for win method
  void testCheckForWin(Tester t) {
    initData();

    // game should not be won for either player initially
    t.checkExpect(game5.checkForWin(Color.PINK), false);
    t.checkExpect(game5.checkForWin(Color.MAGENTA), false);

    // simulating a win for player 1
    game5.board.get(1).get(1).setColor(Color.PINK);
    game5.board.get(1).get(3).setColor(Color.PINK);

    // checking that player 1 has won and player 2 has not
    t.checkExpect(game5.checkForWin(Color.PINK), true);
    t.checkExpect(game5.checkForWin(Color.MAGENTA), false);

    // resetting data and repeating simulated win for player 2
    initData();
    game5.board.get(1).get(1).setColor(Color.MAGENTA);
    game5.board.get(3).get(1).setColor(Color.MAGENTA);

    // checking that player 2 has won and player 2 has not
    t.checkExpect(game5.checkForWin(Color.MAGENTA), true);
    t.checkExpect(game5.checkForWin(Color.PINK), false);
  }

  // testing the last scene method
  void testLastScene(Tester t) {
    initData();

    // creating won message and simulating win for player 1
    WorldImage messageP1 = new TextImage("Player one has won!", 30, FontStyle.BOLD, Color.BLACK);
    game5.board.get(1).get(1).setColor(Color.PINK);
    game5.board.get(1).get(3).setColor(Color.PINK);
    WorldScene expectedWorldSceneP1 = game5.makeScene();
    expectedWorldSceneP1.placeImageXY(messageP1, 125, 125);

    t.checkExpect(game5.lastScene("Player one has won!"), expectedWorldSceneP1);

    // resetting data and creating won message/simulated win for player 2
    initData();
    WorldImage messageP2 = new TextImage("Player two has won!", 30, FontStyle.BOLD, Color.BLACK);
    game5.board.get(1).get(1).setColor(Color.MAGENTA);
    game5.board.get(3).get(1).setColor(Color.MAGENTA);
    WorldScene expectedWorldSceneP2 = game5.makeScene();
    expectedWorldSceneP2.placeImageXY(messageP2, 125, 125);

    t.checkExpect(game5.lastScene("Player two has won!"), expectedWorldSceneP2);
  }

  // testing the same color method
  void testSameColor(Tester t) {
    initData();

    t.checkExpect(cell1.sameColor(game5.board.get(0).get(0)), true);
    t.checkExpect(cell1.sameColor(game5.board.get(0).get(1)), false);
    t.checkExpect(cell1.sameColor(game5.board.get(0).get(0)), true);
    t.checkExpect(game5.board.get(1).get(0).sameColor(
        game5.board.get(1).get(2)), true);
    t.checkExpect(game5.board.get(0).get(1).sameColor(
        game5.board.get(2).get(1)), true);
    t.checkExpect(game5.board.get(1).get(2).sameColor(
        game5.board.get(2).get(1)), false);
  }

  // testing the color equals method
  void testColorEquals(Tester t) {
    initData();

    t.checkExpect(game5.board.get(0).get(0).colorEquals(Color.WHITE), true);
    t.checkExpect(game5.board.get(0).get(0).colorEquals(Color.PINK), false);
    t.checkExpect(game5.board.get(0).get(0).colorEquals(Color.MAGENTA), false);
    t.checkExpect(game5.board.get(0).get(1).colorEquals(Color.WHITE), false);
    t.checkExpect(game5.board.get(0).get(1).colorEquals(Color.PINK), false);
    t.checkExpect(game5.board.get(0).get(1).colorEquals(Color.MAGENTA), true);
    t.checkExpect(game5.board.get(1).get(0).colorEquals(Color.PINK), true);
    t.checkExpect(game5.board.get(1).get(0).colorEquals(Color.MAGENTA), false);
    t.checkExpect(game5.board.get(1).get(0).colorEquals(Color.WHITE), false);
  }

  // testing the set color method
  void setColor(Tester t) {
    initData();

    // check initial color is white
    t.checkExpect(cell1.colorEquals(Color.WHITE), true);

    // set color to pink
    cell1.setColor(Color.PINK);

    // check that cell is no longer white and is now pink
    t.checkExpect(cell1.colorEquals(Color.WHITE), false);
    t.checkExpect(cell1.colorEquals(Color.PINK), true);
  }

  // testing the add neighbor method
  void addNeighbor(Tester t) {
    initData();

    // check initial size of neighborlist is 0 for cell1
    t.checkExpect(cell1.neighborList.size(), 0);

    // add a neighbor to cell1
    cell1.addNeighbor(cell2);

    // check that the neighbor list size has increased and cell2 is in cell1's neighbor list
    t.checkExpect(cell1.neighborList.size(), 1);
    t.checkExpect(cell1.neighborList.contains(cell2), true);
  }
}




