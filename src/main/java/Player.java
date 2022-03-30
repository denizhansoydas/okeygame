import java.util.LinkedList;

public class Player {
    private LinkedList<Tile> board;
    //private Tile left; //for future purposes
    //private Tile right; //for future purposes
    Player(){
        board = new LinkedList<>();
    }

    public LinkedList<Tile> getBoard() {
        return board;
    }

    public void setBoard(LinkedList<Tile> board) {
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
}
