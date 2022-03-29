import java.util.*;

public class Game {
    //static properties and methods
    public static final int TILE_COUNT = 106;
    public static final int PLAYER_COUNT = 4;

    //properties
    private Tile[] tiles;
    private Player[] players;
    //constructors
    Game(){
        tiles = new Tile[TILE_COUNT];
        for(int i = 0; i < TILE_COUNT / 2; i++){
            //There are two instances of a tile. Therefore, the array will be like: [0,...52] [0,...,52]
            tiles[i] = new Tile(i);
            tiles[TILE_COUNT / 2 + i] = new Tile(i);
        }
        players = new Player[PLAYER_COUNT];
        for(int i = 0; i < PLAYER_COUNT; i++)
            players[i] = new Player();
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }
    public void shuffle(){
        List<Tile> tiles_temp = Arrays.asList(tiles);
        Collections.shuffle(tiles_temp);
        tiles_temp.toArray(tiles);
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
}
