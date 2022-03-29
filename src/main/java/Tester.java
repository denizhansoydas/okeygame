import java.util.ArrayList;

/**
 * This is the launcher of our Okey Game.
 * @author Denizhan Soydas
 * @version 1.0-SNAPSHOT
 */
public class Tester {
    public static void main(String[] args) {
        Game game = new Game();
        ArrayList<Tile> shuffledTiles = game.getShuffledTiles();
        for(Tile i: shuffledTiles)
            System.out.println(i + " ");
    }
}
