/**
 * This is the launcher of our Okey Game.
 * @author Denizhan Soydas
 * @version 1.0-SNAPSHOT
 */
public class Tester {
    public static void main(String[] args) {
        Game game = new Game();
        Tile[] tiles = game.getTiles();
        for(Tile i: tiles)
            System.out.println(i + " ");
        System.out.println();
        game.shuffle();
        System.out.println("Shuffled.");
        for(Tile i: tiles)
            System.out.println(i + " ");
    }
}
