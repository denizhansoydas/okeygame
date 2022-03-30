import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static java.lang.Math.max;

public class TileGroup {
    private ArrayList<Tile> tiles;
    public static final int GROUP_MIN_COUNT_CONDITION = 3;
    public static final int GROUP_SAME_COLOR_MAX_COUNT_CONDITION = 4;
    TileGroup(ArrayList<Tile> tiles){
        this.tiles = tiles;
    }
    public int getScore(ArrayList<Tile> tiles){
        int[] scores = new int[tiles.size()];
        scores[0] = 0;
        for(int i = 1; i < tiles.size(); i++){
            //scoring function of Dynamic Programming.
            //case 1: the tile does not have a duplicate.
            //case1.1: New tile may be the leftest one in consecutive tiles. E.g. [7],8,9,10


            //case1.2: New tile may be the rightest one in consecutive tiles. E.g. 7,8,9,[10]

            //case1.3: New tile may be a middle tile in consecutive tiles. E.g. 1,2,[3],4,5



            //case 2: the tile has a duplicate.
            //case2.4: New tile may be a separator middle tile in consecutive tiles. E.g. 1,2,[3] ; 3,4,5

            //case2.5: New tile may be a part of duplicate consecutive tiles. E.g. 1,2,[3] ; 1,2,3

            //case1.6 & 2.6: New tile may be another tile in color group E.g. Example1: Red 4, Black 4, [Blue 4] OR Example2: Red 4, Black 4, Blue 4, [Yellow 4]
        }
        int pair_score = 0; //will be updated to be the score of pairs.
        return max(scores[scores.length - 1], pair_score);
//        if (tiles.size() >= GROUP_MIN_COUNT_CONDITION){
//            if(tiles.size() <= GROUP_SAME_COLOR_MAX_COUNT_CONDITION){
//                boolean sameColorExists = false;
//                for(int i = 0; i < tiles.size(); i++){
//                    for(int j = i + 1; j < tiles.size(); j++){
//                        if(tiles.get(i).getColor() == tiles.get(j).getColor())
//                            sameColorExists = true;
//                    }
//                }
//                if(sameColorExists)
//                    return true;
//            }
//            System.out.println("hi");
//        }
//        return false;
    }
    public Tile[] findTile(ArrayList<Tile> tiles, Tile.COLOR color, int value){
        Tile[] results = new Tile[2];
        for (Tile tile : tiles) {
            if (tile.getColor() == color && tile.getValue() == value) {
                if (results[0] != null)
                    results[1] = tile;
                else
                    results[0] = tile;
            }
        }
        return results;
    }
    public void sort(){

    }
    public int getSpecificSetScore(ArrayList<Tile> tiles){
        //NOTE: exceptional case will be added : if there is a joker in tiles.
        //too few tiles.
        if (tiles.size() < 2)
            return 0;
        //pair
        if (tiles.size() == 2)
            return tiles.get(0).equals(tiles.get(1)) ? 2 : 0;
        //same number, different color group.
        //Tile tile = tiles.get(0);
        int[] colorsFound = new int[Tile.COLOR.values().length];
        int duplicateColorFound = 0;
        for(Tile tile: tiles){
            colorsFound[Arrays.asList(Tile.COLOR.values()).indexOf(tile.getColor())] += 1;
            if (colorsFound[Arrays.asList(Tile.COLOR.values()).indexOf(tile.getColor())] >= 1)
                duplicateColorFound += 1;
        }
        int colorCount = 0;
        for(int color : colorsFound)
            colorCount += color >= 1 ? 1 : 0;

        return 0;


    }
}
