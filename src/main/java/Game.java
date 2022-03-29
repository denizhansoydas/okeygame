import java.util.*;

public class Game {
    //static properties and methods
    public static int TILE_COUNT = 106;

    //properties
    //private Integer[] tiles;
    private Tile[] tiles;

    //constructors
    Game(){
        tiles = new Tile[TILE_COUNT];
        for(int i = 0; i < TILE_COUNT / 2; i++){
            //There are two instances of a tile. Therefore, the array will be like: [0,...52] [0,...,52]
            tiles[i] = new Tile(i);
            tiles[TILE_COUNT / 2 + i] = new Tile(i);
        }
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }
    public void shuffle(){
        List<Tile> tiles_temp = Arrays.asList(tiles);
//        List<Integer> intList = Arrays.asList(tiles);
//        Collections.shuffle(intList);
//        intList.toArray(tiles);
    }
}
