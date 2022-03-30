import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    //static properties and methods
    public static final int START_TILE_COUNT = 106;
    public static final int PLAYER_COUNT = 4;

    //properties
    private ArrayList<Tile> shuffledTiles;
    private Player[] players;
    private Tile faceUpTile; //will determine the Joker.

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
        chooseJoker();
        distribute();

        for (Player player : players) {
            System.out.println("A player has:" + player.getBoard());
        }
    }

    /**
     * Method to shuffle the tiles. Aimed to be used only in constructor.
     * @param tiles is the array of not shuffled tiles.
     */
    private void shuffle(Tile[] tiles){
        List<Tile> tiles_temp = Arrays.asList(tiles);
        Collections.shuffle(tiles_temp);
        tiles_temp.toArray(tiles);
        Collections.addAll(shuffledTiles, tiles);
        System.out.println("Shuffled.");
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
    public void chooseJoker(){ // does not roll a die like in real okey game.
        int i = 0;
        //noinspection StatementWithEmptyBody (Supressed.)
        for(; shuffledTiles.get(i).isFakeJoker(); i++); //find a not fake joker index.
        faceUpTile = shuffledTiles.remove(i);
        System.out.println("FaceUpTile is now determined as: " + faceUpTile + " and Joker is now:" + new Tile(getJokerNo()));
    }
    public Tile getFaceUpTile() {
        return faceUpTile;
    }
    public void setFaceUpTile(Tile faceUpTile) {
        this.faceUpTile = faceUpTile;
    }
    public int getJokerNo(){
        return faceUpTile.getValue() == Tile.TILES_PER_COLOR ? faceUpTile.getNo() - Tile.TILES_PER_COLOR + 1 : faceUpTile.getNo() + 1; //if faceUpTile is the last one of that color, return same color. Otherwise, return one more. 1. E.g. If faceUpTile is red-13, return red-1.
    }
    public int getJokerValue(){
        return faceUpTile.getValue() == Tile.TILES_PER_COLOR ? 1 : faceUpTile.getValue() + 1;
    }
    public Tile.COLOR getJokerColor(){
        return faceUpTile.getColor();
    }
    public void distribute(){
        for(int i = 0; i < Tile.TILES_PER_COLOR; i++){
            for(Player player: players){
                player.takeTile(shuffledTiles.remove(0));
            }
        }
        int randomNumber = ThreadLocalRandom.current().nextInt(0, players.length); //See: Java 1.7+ Random Number Generation.
        players[randomNumber].takeTile(shuffledTiles.remove(0));
        System.out.println("Distributed.");
    }


}
