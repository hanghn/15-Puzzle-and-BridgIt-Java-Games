import tester.Tester;
import javalib.impworld.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import javalib.worldimages.*;

//Represents an individual tile
class Tile {
  // The number on the tile.  Use 0 to represent the space
  int value;

  Tile(int value) {
    this.value = value;  
  }

  //Draws this tile onto the background at the specified logical coordinates
  WorldScene drawAt(int c, int r, WorldScene background) {
    if (this.value == 0) {
      return background;
    }

    int tileSize = 100;
    int textSize = 50;

    // Multiplying c by tileSize gives the left edge of the tile in the horizontal (x) direction
    // "tileSize/2" shifts the position from the top-left corner of the tile to its center
    int x = c * tileSize + tileSize / 2;
    int y = r * tileSize + tileSize / 2;

    // checking if the row is correct; to be in the correct row, the value should
    // be greater than r * 4, and less than or equal to that value + 4
    boolean correctRow = ((this.value > (r * 4)) && (this.value <= ((r * 4) + 4)));

    // checking if the column is correct; to be in the correct row, the value
    // should be equal to the row value * 4, + the column value + 1 (as we start from 0)
    boolean correctColumn = (this.value == ((r * 4) + (c + 1)));

    // changing the tile color to orange if in the correct position, or blue if not
    Color tileColor;
    if ((correctRow) && (correctColumn)) {
      tileColor = Color.ORANGE;
    }
    else {
      tileColor = Color.BLUE;
    }

    // values for tiles and texts as images, and modifying background to have tile images
    WorldImage tileImage = new RectangleImage(tileSize, tileSize, OutlineMode.SOLID, tileColor);
    WorldImage text = new TextImage(Integer.toString(this.value), textSize, Color.BLACK);
    WorldImage tileWithText = new OverlayImage(text, tileImage);
    background.placeImageXY(tileWithText, x, y);
    return background;
  }
}

// class to represent a fifteen game
class FifteenGame extends World {
  // represents the rows of tiles
  ArrayList<ArrayList<Tile>> tiles;

  // the constructor for the normal 4*4 grid of fifteen game
  FifteenGame(ArrayList<ArrayList<Tile>> tiles, boolean randomize) {
    this.tiles = tiles;

    // initializes a new 4x4 fifteen game grid
    initializeTiles(4);

    // a boolean indicating whether the tiles should be randomized
    if (randomize) {
      randomizeTiles();
    }
  }

  // the convenience constructor for testing with a smaller 2*2 grid
  FifteenGame() {
    this.tiles = new ArrayList<ArrayList<Tile>>();
    initializeTiles(3);
  }

  //the convenience constructor for testing with no tiles
  FifteenGame(ArrayList<ArrayList<Tile>> tiles) {
    this.tiles = new ArrayList<ArrayList<Tile>>();
    initializeTiles(0);
  }

  //list of the opposite values of previous moves for implementation in the
  //key handler method.
  ArrayList<String> prevMoveListOpp = new ArrayList<String>();

  // EFFECT: set up the initial state of the game's board; initialize the tiles by adding tiles 
  // into rows with increasing values, then adding rows to tiles
  public void initializeTiles(int size) {
    // start from 1 instead of 0 as this will be used for 
    // testing if all the tile are in correct position
    int value = 1; 

    for (int r = 0; r < size; r++) {
      ArrayList<Tile> row = new ArrayList<>();
      for (int c = 0; c < size; c++) {
        if (r == size - 1 && c == size - 1) {
          row.add(new Tile(0)); // Add an empty tile with value 0 for the last tile
        } 
        else {
          row.add(new Tile(value));
          value++; 
        }
      }
      this.tiles.add(row);
    }
  }

  // EFFECT: randomize the order of tiles iterating through each row and column and swapping
  // tiles with other tiles and random indexes
  public void randomizeTiles() {
    Random rand = new Random();

    for (int r = 0; r < this.tiles.size(); r++) {
      for (int c = 0; c < this.tiles.get(r).size(); c++) {

        swapTiles(r, c, rand.nextInt(4), rand.nextInt(4));
      }   
    }
  }


  // EFFECT: seeded randomize method for testing
  public void randomizeTilesForTesting() {
    Random seed = new Random(1);

    for (int r = 0; r < this.tiles.size(); r++) {
      for (int c = 0; c < this.tiles.get(r).size(); c++) {

        swapTiles(r, c, seed.nextInt(1), seed.nextInt(1));
      }   
    }
  }

  // EFFECT: swaps tiles by taking two tile row and column indices and resetting them to 
  // each other's previous indices
  public void swapTiles(int r1, int c1, int r2, int c2) {
    //gets the first tile at row r1 and column c1
    Tile t1 = this.tiles.get(r1).get(c1);
    Tile t2 = this.tiles.get(r2).get(c2);

    // places the second tile (t2) at the position of the first tile 
    this.tiles.get(r1).set(c1, t2); 
    this.tiles.get(r2).set(c2, t1); 
  }

  // draws the game by iterating through the tiles and drawing them at their position by
  // mutating the scene with the draw at method
  public WorldScene makeScene() { 
    WorldScene scene = this.getEmptyScene();

    for (int r = 0; r < this.tiles.size(); r++) { // iterates over each row of the grid
      // iterates over each column within that row 
      for (int c = 0; c < this.tiles.get(r).size(); c++) { 
        scene = this.tiles.get(r).get(c).drawAt(c, r, scene); // add the tile image
      }
    }
    return scene;
  }

  //Helper method for makeScene testing that checks if a specific tile is at a given position
  boolean isTileAtPosition(int row, int col, int expectedValue) {
    // Check if the position is for the last tile and expected value is 0 (empty space)
    if (expectedValue == 0 && row == this.tiles.size() - 1 && col == this.tiles.get(0).size() - 1) {
      return this.tiles.get(row).get(col).value == 0;
    }
    return this.tiles.get(row).get(col).value == expectedValue;
  }

  //Helper method for makeScene testing that counts the number of non-empty tiles
  int countNonEmptyTiles() {
    int count = 0;
    for (ArrayList<Tile> row : this.tiles) {
      for (Tile tile: row) {
        if (tile.value != 0) {
          count++;
        }
      }
    }
    return count;
  }

  // EFFECT: handles keystrokes
  public void onKeyEvent(String k) {
    // find the position of the empty tile, -1 if not exist
    int emptyRow = -1;
    int emptyCol = -1;
    boolean foundEmptyTile = false;
    // find the empty tile
    for (int r = 0; r < this.tiles.size() && !foundEmptyTile; r++) {
      for (int c = 0; c < this.tiles.get(r).size() && !foundEmptyTile; c++) {
        if (this.tiles.get(r).get(c).value == 0) {
          emptyRow = r;
          emptyCol = c;
          foundEmptyTile = true;
        }
      }
    }

    // Swap the empty tile with an adjacent tile based on the given key event
    if (foundEmptyTile) {
      if (k.equals("up") && emptyRow < this.tiles.size() - 1) {
        swapTiles(emptyRow, emptyCol, emptyRow + 1, emptyCol);
        prevMoveListOpp.add("down");
      }
      else if (k.equals("down") && emptyRow > 0) {
        swapTiles(emptyRow, emptyCol, emptyRow - 1, emptyCol);
        prevMoveListOpp.add("up");
      }
      // make sure that the empty tile (the one with value 0) is not already at the
      // far left of the grid
      else if (k.equals("left") && emptyCol < this.tiles.get(0).size() - 1) {
        swapTiles(emptyRow, emptyCol, emptyRow, emptyCol + 1);
        prevMoveListOpp.add("right");
      }
      else if (k.equals("right") && emptyCol > 0) {
        swapTiles(emptyRow, emptyCol, emptyRow, emptyCol - 1);
        prevMoveListOpp.add("left");
      }
      else if (k.equals("u") && emptyCol > 0 && (prevMoveListOpp.size() - 1) > - 1) {
        onKeyEvent(prevMoveListOpp.remove(prevMoveListOpp.size() - 1));
        if ((prevMoveListOpp.size() - 1) > 0) {
          prevMoveListOpp.remove(prevMoveListOpp.size() - 1);
        }
      }
    }

    // update the scene
    this.makeScene();
    // check if the updated game satisfies the winning condition
    if (isGameWon()) {
      this.endOfWorld("You won!"); 
    }
  }

  //detect if the tiles are in correct order to satisfy the winning condition
  public boolean isGameWon() {
    int expectedValue = 1;
    for (int row = 0; row < this.tiles.size(); row++) {
      for (int col = 0; col < this.tiles.get(row).size(); col++) {
        Tile tile = this.tiles.get(row).get(col);
        if (tile.value != 0 && tile.value != expectedValue) {
          return false;
        }
        if (tile.value != 0) {
          expectedValue++;
        }
      }
    }
    // Check if the last tile is the empty space
    return this.tiles.get(this.tiles.size() - 1).get(this.tiles.get(0).size() - 1).value == 0;
  }

  //display the last scene of the game
  public WorldScene lastScene(String msg) {
    WorldScene scene = this.getEmptyScene(); 
    int sceneWidth = 400; 
    int sceneHeight = 400; 

    WorldImage message = new TextImage("You won!", 30, FontStyle.BOLD, Color.green);
    scene.placeImageXY(message, sceneWidth / 2, sceneHeight / 2);

    return scene;   
  }
}

// examples class for example fifteen games, tests, and running the game
class ExampleFifteenGame {

  Tile t0 = new Tile(0);
  Tile t1 = new Tile(1);
  Tile t15 = new Tile(15);
  ArrayList<ArrayList<Tile>> mtList = new ArrayList<ArrayList<Tile>>();
  FifteenGame fg1 = new FifteenGame(new ArrayList<ArrayList<Tile>>(), false);
  FifteenGame fg2 = new FifteenGame();
  FifteenGame fg3 = new FifteenGame(new ArrayList<ArrayList<Tile>>());
  WorldScene mtScene = new FifteenGame(new ArrayList<ArrayList<Tile>>(), false).getEmptyScene();
  WorldScene mtScene2 = new FifteenGame(new ArrayList<ArrayList<Tile>>(), false).getEmptyScene();

  // initialize the Tile, fifteenGame
  void initData() {
    this.t0 = new Tile(0);
    this.t1 = new Tile(1);
    this.t15 = new Tile(15);
    this.mtScene = new FifteenGame(new ArrayList<ArrayList<Tile>>(), false).getEmptyScene();
    this.mtScene2 = new FifteenGame(new ArrayList<ArrayList<Tile>>(), false).getEmptyScene();
    this.fg1 = new FifteenGame(new ArrayList<ArrayList<Tile>>(), false);
    this.fg2 = new FifteenGame();

  }

  // running the game with big bang
  void testGame(Tester t) {
    FifteenGame g = new FifteenGame(new ArrayList<ArrayList<Tile>>(), true);
    g.bigBang(400, 400);
  }

  // test the initialize tiles method
  void testInitializeTiles(Tester t) {
    FifteenGame dataToInitialize = new FifteenGame();

    t.checkExpect(dataToInitialize.tiles.get(0).get(0), new Tile(1));
    t.checkExpect(dataToInitialize.tiles.get(2).get(2), new Tile(0));
    t.checkExpect(dataToInitialize.tiles.get(0).get(1), new Tile(2));
    t.checkExpect(dataToInitialize.tiles.get(2).get(1), new Tile(8));
  }

  // test the randomizeTiles method
  void testRandomizeTilesForTesting(Tester t) {
    initData();
    FifteenGame game = new FifteenGame();
    game.randomizeTilesForTesting();

    t.checkExpect(game.isTileAtPosition(0, 0, 0), true);
    t.checkExpect(game.isTileAtPosition(1, 0, 3), true);
    t.checkExpect(game.isTileAtPosition(2, 2, 8), true); 
  }

  // test the make scene method
  void testMakeScene(Tester t) {
    initData();

    // compare the actual components of the world scene
    // Verify that fg1 is an empty scene, fg1 & 2 are non-empty scene
    t.checkExpect(fg1.countNonEmptyTiles(), 15); 
    t.checkExpect(fg2.countNonEmptyTiles(), 8); 
    t.checkExpect(fg3.countNonEmptyTiles(), 0);

    // Verify that fg2 has the correct tiles in the right positions
    // Assuming the tiles are ordered starting from 1 (not 0) and the last tile is the empty space
    t.checkExpect(fg2.isTileAtPosition(0, 0, 1), true);
    t.checkExpect(fg2.isTileAtPosition(0, 1, 2), true);
    t.checkExpect(fg2.isTileAtPosition(0, 2, 3), true);
    t.checkExpect(fg2.isTileAtPosition(1, 0, 4), true);
    t.checkExpect(fg2.isTileAtPosition(1, 1, 5), true);
    t.checkExpect(fg2.isTileAtPosition(1, 2, 6), true);
    t.checkExpect(fg2.isTileAtPosition(2, 0, 7), true);
    t.checkExpect(fg2.isTileAtPosition(2, 1, 8), true);
    t.checkExpect(fg2.isTileAtPosition(2, 2, 0), true);

    // determine if the actual return type is a world scene same as the expected one
    t.checkExpect(fg1.makeScene(), fg1.getEmptyScene()); 
    t.checkExpect(fg2.makeScene(), new WorldScene(0, 0));
  }

  // test the draw at method
  void testDrawAt(Tester t) {
    initData();

    t0.drawAt(0, 0, mtScene);
    t1.drawAt(0, 1, mtScene2);
    WorldScene expectedWorldScene = fg1.getEmptyScene();

    expectedWorldScene.placeImageXY(
        new OverlayImage(new TextImage(
            Integer.toString(1), 50, Color.BLACK), new RectangleImage(
                100, 100, OutlineMode.OUTLINE, Color.BLUE)), 0, 1);

    t.checkExpect(mtScene, mtScene);
    t.checkExpect(mtScene2, expectedWorldScene); 
  }

  // testing the swap tiles method
  void testSwapTiles(Tester t) {
    Tile og00 = fg1.tiles.get(0).get(0);
    Tile og01 = fg1.tiles.get(0).get(1);
    Tile og02 = fg1.tiles.get(0).get(2);
    Tile og33 = fg1.tiles.get(3).get(3);

    initData();

    // store original tiles
    fg1.swapTiles(0, 0, 0, 1);
    fg1.swapTiles(0, 2, 3, 3);

    t.checkExpect(fg1.tiles.get(0).get(0), og01);
    t.checkExpect(fg1.tiles.get(0).get(1), og00);
    t.checkExpect(fg1.tiles.get(0).get(2), og33);
    t.checkExpect(fg1.tiles.get(3).get(3), og02);
  }

  //Test moving tiles with key events
  void testOnKeyEvent(Tester t) {
    initData();
    FifteenGame game = new FifteenGame();
    // manually set up a known non-winning configuration
    // make sure that the configuration allows for movement of tiles without winning the game
    // as new FifteenGame() will construct the game with all the tiles in correct orders
    game.tiles.get(0).set(0, new Tile(1));
    game.tiles.get(0).set(1, new Tile(2));
    game.tiles.get(0).set(2, new Tile(3));
    game.tiles.get(1).set(0, new Tile(4));
    game.tiles.get(1).set(1, new Tile(0));
    game.tiles.get(1).set(2, new Tile(5)); 
    game.tiles.get(2).set(0, new Tile(7));
    game.tiles.get(2).set(1, new Tile(8));
    game.tiles.get(2).set(2, new Tile(6));

    game.onKeyEvent("up");
    t.checkExpect(game.isTileAtPosition(1, 1, 8), true);
    game.onKeyEvent("down");
    t.checkExpect(game.isTileAtPosition(2, 1, 8), true);
    game.onKeyEvent("left");
    t.checkExpect(game.isTileAtPosition(1, 1, 5), true);
    game.onKeyEvent("right");
    t.checkExpect(game.isTileAtPosition(1, 2, 5), true);
    game.onKeyEvent("x");
    t.checkExpect(game.isTileAtPosition(1, 2, 5), true);
    game.onKeyEvent("u");
    t.checkExpect(game.isTileAtPosition(1, 1, 5), true);

  }

  //testing the is game won method
  void testIsGameWon(Tester t) {
    initData();
    fg2.swapTiles(2, 1, 0, 0);
    // tiles are initialized in the "winning" order
    t.checkExpect(fg1.isGameWon(), true);
    // tiles were swapped, should no longer be in "winning" order
    t.checkExpect(fg2.isGameWon(), false);  
  }


  // test on the lastScene world
  void testLastScene(Tester t) {
    initData();

    WorldImage message = new TextImage("You won!", 30, FontStyle.BOLD, Color.green);
    WorldScene expectedWorldScene = fg1.getEmptyScene();
    expectedWorldScene.placeImageXY(message, 200, 200);

    t.checkExpect(fg1.lastScene("You won!"), expectedWorldScene); 
  }

}
