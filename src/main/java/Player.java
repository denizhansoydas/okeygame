import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class is to denote players in the game.
 * @author Denizhan Soydas
 * @version 1.1
 */
public class Player {
    private ArrayList<Tile> board;
    private String name;
    private final Game game;
    Player(Game game, String name){
        board = new ArrayList<>();
        this.game = game;
        this.name = name;
    }
    /**
     *This method is for the player to take a tile.
     * @param tile the new tile to be taken.
     */
    public void takeTile(Tile tile){
        board.add(tile);
    }
    /**
     * The method to discard any tile from the board.
     * @param tile the tile to be discarded from the board.
     */
    public void discardTile(Tile tile){
        board.remove(tile);
    }
    /**
     * The method to discard any tile from the board
     * @param tileIndex index of the tile to be discarded from the board.
     */
    public void discardTile(int tileIndex){
        board.remove(tileIndex);
    }
    public ArrayList<Tile> getBoard() {
        return board;
    }
    public void setBoard(ArrayList<Tile> board) {
        this.board = board;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
