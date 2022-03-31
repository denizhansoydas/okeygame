import java.util.ArrayList;

import static java.lang.Math.max;

public class TileGroup {
    private ArrayList<Tile> tiles;
    public static final int PAIR_SIZE = 2;
    public static final int GROUP_MIN_COUNT_CONDITION = 3;
    public static final int GROUP_SAME_COLOR_MAX_COUNT_CONDITION = 4;
    TileGroup(ArrayList<Tile> tiles){
        this.tiles = tiles;
    }
    public int getScore(ArrayList<Tile> tiles){
        short[][] grid = new short[Tile.Color.values().length - 1][Tile.TILES_PER_COLOR];
        int[] scores = new int[tiles.size()];
        scores[0] = 0;
        for(int i = 1; i < tiles.size(); i++){
            //scoring function of Dynamic Programming.
            Tile tile = tiles.remove(0);
            //case 0: there is not enough tile to form a group.
            boolean duplicateExists = false;
            if(i < GROUP_MIN_COUNT_CONDITION){
                short colorIndex = (short)Tile.getColorIndex(tile.getColor());
                short valueIndex = (short)tile.getValue();
                grid[colorIndex][valueIndex] += 1;
                duplicateExists = grid[colorIndex][valueIndex] >= 2;
            }
        }
        return 0; //will be re-implemented
    }
    public Tile[] findTile(ArrayList<Tile> tiles, Tile.Color color, int value){
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

    public boolean formsASet(short[][] grid, int colorIndex, int valueIndex){ //will be enhanced to handle sets of {...,12,13,1}
        if(grid[colorIndex][valueIndex] < 1)
            return false;

        boolean one_left = valueIndex > 0
                && grid[colorIndex][valueIndex - 1] >= 1;
        boolean two_left = valueIndex > 1
                && grid[colorIndex][valueIndex - 2] >= 1;
        boolean one_right = valueIndex < Tile.TILES_PER_COLOR
                && grid[colorIndex][valueIndex + 1] >= 1;
        boolean two_right = valueIndex < Tile.TILES_PER_COLOR - 1
                && grid[colorIndex][valueIndex + 2] >= 1;
        boolean consequtiveSet = (two_left && one_left)
                || (one_right && two_right)
                || (one_left && one_right);
        if(consequtiveSet)
            return true;
        int count  = 0;
        for (short[] rows : grid) {
            if (rows[valueIndex] >= 1)
                count++;
        }
        return count >= GROUP_MIN_COUNT_CONDITION;
    }
    public short[] bestSet(short[][] grid, int colorIndex, int valueIndex){
        //returns an array representing the indexes of best set.
        //for consequtive sets, its length is 2, first one denotes the first value, while the second one denotes the last value. For example, for the set 6,7,8,9; it will return [6,9]
        //for color sets, its length is 4, each one denotes the colors involved. For example, for the set Yellow-4, Blue-4, Red-4; it will return [1,1,0,1] because there is no black.
        return null;
    }
}
