public class Launcher {
    public static void main(String[] args) {
        int gridSize = 11;
        new BridgItGame(gridSize).bigBang(gridSize * 50, gridSize * 50);
    }
}
