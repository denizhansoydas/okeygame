import java.util.*;

public class Game {
    //static properties and methods
    public static int TILE_COUNT = 106;

    //properties
    private Integer[] tiles;

    //constructors
    Game(){
        tiles = new Integer[TILE_COUNT];
    }

    public Integer[] getTiles() {
        return tiles;
    }

    public void setTiles(Integer[] tiles) {
        this.tiles = tiles;
    }
    public void shuffle(){
        List<Integer> intList = Arrays.asList(tiles);
        Collections.shuffle(intList);
        intList.toArray(tiles);
    }
}
