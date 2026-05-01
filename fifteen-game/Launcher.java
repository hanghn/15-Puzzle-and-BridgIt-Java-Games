import java.util.ArrayList;

public class Launcher {
    public static void main(String[] args) {
        new FifteenGame(new ArrayList<ArrayList<Tile>>(), true).bigBang(400, 400);
    }
}
