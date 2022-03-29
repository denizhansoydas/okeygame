import java.util.*;

public class Game {
    //static properties and methods
    public static int TILE_COUNT = 106;

    //properties
    private Integer[] tiles;

    //constructors
    Game(){
        tiles = new Integer[TILE_COUNT];
        for(int i = 0; i < TILE_COUNT / 2; i++){
            tiles[i] = tiles[TILE_COUNT / 2 + i] = i; //There are two instances of a tile. Therefore, the array will be like: [0,...52] [0,...,52]
        }
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
