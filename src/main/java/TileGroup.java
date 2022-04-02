import java.util.ArrayList;
import java.util.Arrays;

public class TileGroup {
    private ArrayList<Tile> tiles;
    public static final int PAIR_SIZE = 2;
    public static final int GROUP_MIN_COUNT_CONDITION = 3;
    public static final int GROUP_SAME_COLOR_MAX_COUNT_CONDITION = 4;
    public static short[][] deepCopy(short[][] original) {
        if (original == null) {
            return null;
        }
        final short[][] result = new short[original.length][];
        for (int i = 0; i < original.length; i++) {

            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    TileGroup(ArrayList<Tile> tiles){
        this.tiles = tiles;
    }
    public int getScore(ArrayList<Tile> tiles){
        short[][] grid = new short[Tile.Color.values().length - 1][Tile.TILES_PER_COLOR];
        int[] scores = new int[tiles.size() + 1];
        scores[0] = 0;
        for(int i = 1; !tiles.isEmpty(); i++){
            //scoring function of Dynamic Programming.
            scores[i] = scores[i - 1];
            Tile tile = tiles.remove(0);
            //case 0: there is not enough tile to form a group.
            if(!formsASet(grid,Tile.getColorIndex(tile.getColor()), tile.getValue() - 1)){
                return scores[i - 1];
            }
            short[][] grid_new = TileGroup.deepCopy(grid);
            short[][] grid_old = TileGroup.deepCopy(grid);

//            if(i < GROUP_MIN_COUNT_CONDITION){
//                short colorIndex = (short)Tile.getColorIndex(tile.getColor());
//                short valueIndex = (short)tile.getValue();
//                grid[colorIndex][valueIndex] += 1;
//                duplicateExists = grid[colorIndex][valueIndex] >= 2;
//            }
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

    /**
     * This method finds the best set a particular tile may make.
     * @param grid all the tiles in the board of the player.
     * @param colorIndex index of the Tile's color in grid.
     * @param valueIndex index of Tile's value in grid.
     * @return a summary array denoting the best set.
     * For consec. sets, return length is 2, [0]->lowestIndex, [1]=highestIndex; For color sets, it's length of 4, each index denoting involved colors with 0/1. For bidirectional(both conseq. and color) sets, it's length is 6; which is [0,1]->horizontal, [2..5]->vertical
     */
    public short[] bestSet(short[][] grid, int colorIndex, int valueIndex){
        if(grid[colorIndex][valueIndex] == 0)
            return null;
        int low = valueIndex;
        int high = valueIndex;
        while(low >= 1 && grid[colorIndex][low - 1] >= 1)
            low--;
        while(high < Tile.TILES_PER_COLOR - 1 && grid[colorIndex][high + 1] >= 1)
            high++;
        int consequtiveTotal = high - low + 1;
        if (high == Tile.TILES_PER_COLOR - 1 && grid[colorIndex][0] >= 1){
            consequtiveTotal++;
            high = 0;
        }
        int specialCaseLow = Tile.TILES_PER_COLOR - 1;
        int specialCaseHigh = 0;
        while(specialCaseLow >= 1 && grid[colorIndex][specialCaseLow - 1] >= 1)
            specialCaseLow--;
        int specialCaseTotal = Tile.TILES_PER_COLOR - specialCaseLow + 1;
        short[] colorGrid = new short[Tile.Color.values().length - 1];
        int colorCount = 0;
        for(int i = 0; i < Tile.Color.values().length - 1; i++) {
            if (grid[i][valueIndex] == 1) {
                colorGrid[i] = 1;
                colorCount++;
            }
        }
        if(colorCount > GROUP_MIN_COUNT_CONDITION){
            if (consequtiveTotal > specialCaseTotal && consequtiveTotal >= GROUP_MIN_COUNT_CONDITION){
                short[] res = new short[2 + colorGrid.length];
                res[0] = (short)low;
                res[1] = (short)high;
                System.arraycopy(colorGrid, 0, res, 2, colorGrid.length);
                return res;
            }else if(specialCaseTotal >  consequtiveTotal && specialCaseTotal >= GROUP_MIN_COUNT_CONDITION){
                short[] res = new short[2 + colorGrid.length];
                res[0] = (short)specialCaseLow;
                res[1] = (short)specialCaseHigh;
                System.arraycopy(colorGrid, 0, res, 2, colorGrid.length);
                return res;
            }
        }
        if (consequtiveTotal > specialCaseTotal){
            if (consequtiveTotal > colorCount){
                return new short[]{(short) low, (short) high};
            }
            return new short[]{(short) specialCaseLow, (short) specialCaseLow};
        }
        //There will be another case here. For example [Red (3,4,5)] + [(Yellow, Black,Blue)5] it will be implemented later!
        return colorGrid;
    }

    /**
     * This is just an helper method. This method will be merged with bestSet() method, and discarded to be deprecated in the following versions.
     * @param info information about the grid.
     * @return the grid denoting all the tile locations.
     */
    public short[][] gridize(short[] info, int colorIndex, int valueIndex){
        short[][] res = new short[Tile.Color.values().length - 1][Tile.TILES_PER_COLOR];

        if(info.length == 2){ //only low and high.
            short low = info[0];
            short high = info[1];
            if(low < high){
                for(int i = low; i <= high; i++){
                    res[colorIndex][i] = 1;
                }
            }else{
                res[colorIndex][0] = 1;
                for(int i = 0; i < Tile.TILES_PER_COLOR; i++) {
                    res[colorIndex][i] = 1;
                }
            }
        }else if(info.length == Tile.Color.values().length - 1){
            for(int i = 0; i < info.length; i++){
                res[i][valueIndex] = 1;
            }
        }else if(info.length == Tile.Color.values().length - 1 + 2){
            short[][] res_1 = gridize(Arrays.copyOfRange(info, 0, 2), colorIndex, valueIndex);
            short[][] res_2 = gridize(Arrays.copyOfRange(info, 2, info.length), colorIndex, valueIndex);
            for(int i = 0; i < res.length; i++) {
                for(int j = 0; j < res[0].length; j++){
                    res[i][j] = (short)(res_1[i][j] + res_2[i][j]);
                }
            }
        }
        return res;
    }
}
