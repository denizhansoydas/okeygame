/**
 * This is the launcher of our Okey Game.
 * @author Denizhan Soydas
 * @version 1.0-SNAPSHOT
 */
public class Tester {
    public static void main(String[] args) {
        Game game = new Game();
        Integer[] tiles = game.getTiles();
        for(Integer i: tiles)
            System.out.print(i + " ");
        System.out.println();
        game.shuffle();
        System.out.println("Shuffled.");
        for(Integer i: tiles)
            System.out.print(i + " ");
    }
}
