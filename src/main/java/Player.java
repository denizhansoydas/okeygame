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
}
