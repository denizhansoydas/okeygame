import java.util.ArrayList;
import java.util.LinkedList;

public class Player {
    private ArrayList<Tile> board;
    private String name;
    //private Tile left; //for future purposes
    //private Tile right; //for future purposes
    Player(String name){
        board = new ArrayList<>();
        this.name = name;
    }

    public ArrayList<Tile> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<Tile> board) {
        this.board = board;
    }
    public void takeTile(Tile tile){
        board.add(tile);
    }
    public void discardTile(Tile tile){
        board.remove(tile);
    }
    public void discardTile(int tileIndex){
        board.remove(tileIndex);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
