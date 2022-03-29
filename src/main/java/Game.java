import java.util.*;

public class Game {
    //static properties and methods
    public static final int START_TILE_COUNT = 106;
    public static final int PLAYER_COUNT = 4;

    //properties

    private ArrayList<Tile> shuffledTiles;
    private Player[] players;
    //constructors
    Game(){
        Tile[] tiles;
        shuffledTiles = new ArrayList<>();
        players = new Player[PLAYER_COUNT];

        tiles = new Tile[START_TILE_COUNT];
        for(int i = 0; i < START_TILE_COUNT / 2; i++){
            //There are two instances of a tile. Therefore, the array will be like: [0,...52] [0,...,52]
            tiles[i] = new Tile(i);
            tiles[START_TILE_COUNT / 2 + i] = new Tile(i);
        }

        for(int i = 0; i < PLAYER_COUNT; i++)
            players[i] = new Player();
        shuffle(tiles);
        Collections.addAll(shuffledTiles, tiles);
    }

    //Method to shuffle the tiles. Aimed to be used only in constructor.
    private void shuffle(Tile[] tiles){
        List<Tile> tiles_temp = Arrays.asList(tiles);
        Collections.shuffle(tiles_temp);
        tiles_temp.toArray(tiles);
    }

    public ArrayList<Tile> getShuffledTiles() {
        return shuffledTiles;
    }

    public void setShuffledTiles(ArrayList<Tile> shuffledTiles) {
        this.shuffledTiles = shuffledTiles;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
}
