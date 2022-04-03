import java.util.ArrayList;
import java.util.Arrays;

public class TileGroup {
    private final ArrayList<Tile> tiles;
    public static final int PAIR_SIZE = 2;
    public static final int GROUP_MIN_COUNT_CONDITION = 3;
    public static final int GROUP_SAME_COLOR_MAX_COUNT_CONDITION = 4;

    private final Game game;
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

    TileGroup(Game game, ArrayList<Tile> tiles){
        this.tiles = tiles;
        this.game = game;
    }
    public int getScore(){
        ArrayList<Tile> tiles_copy = new ArrayList<>(tiles);
        short[][] grid = new short[Tile.Color.values().length - 1][Tile.TILES_PER_COLOR];
        int[] scores = new int[tiles_copy.size() + 1];
         scores[0] = 0;
        for(int i = 1; !tiles_copy.isEmpty(); i++){
            //scoring function of Dynamic Programming.
            scores[i] = scores[i - 1];
            Tile tile = tiles_copy.remove(0);



            short[][] grid_old = TileGroup.deepCopy(grid);
            int colorI;
            int valI;
            if(tile.getColor() == Tile.Color.FAKE_JOKER){
                colorI = Tile.getColorIndex(game.getJokerColor());
                valI = game.getJokerValue() - 1;
            }else{
                colorI = Tile.getColorIndex(tile.getColor());
                valI = tile.getValue() - 1;
            }
            grid[colorI][valI]++;
            short[][] grid_new = TileGroup.deepCopy(grid);

            if(formsASet(grid_new, colorI, valI)){
                int total_new = 0;
                boolean left = false;
                boolean right = false;
                while(formsASet(grid_new, colorI, valI)){
                    short[] bestSet = bestSet(grid_new, colorI, valI);
                    short[][] temp_grid = gridize(bestSet, colorI, valI);
                    int count = 0;
                    for(int j = 0; j <  grid_new.length; j++) {
                        for(int k = 0; k < grid_new[0].length; k++){
                            grid_new[j][k] -= temp_grid[j][k];
                            count += temp_grid[j][k];
                        }
                    }
                    left = valI > 0 ? grid_new[colorI][valI - 1] >= 1 : grid_new[colorI][Tile.TILES_PER_COLOR - 1] >= 1;
                    right = valI < Tile.TILES_PER_COLOR - 1 ? grid_new[colorI][valI + 1] >= 1 : grid_new[colorI][0] >= 1;
                    total_new += count;
                }
                if(total_new > GROUP_MIN_COUNT_CONDITION){ //Two cases. Case1: not forming a new group, just increasing the group's size. Case2: middle tile.
                    if(left && right){ //middle tile
                        scores[i] += total_new;
                    }
                    else{
                        scores[i] += 1; //widening the group, i.e. increasing the group size.
                    }
                }
                else{
                    scores[i] += total_new;
                }
            }
        }
        return scores[scores.length  - 1];
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

    public boolean formsASet(short[][] grid, int colorIndex, int valueIndex){
        if(grid[colorIndex][valueIndex] < 1)
            return false;

        boolean one_left = valueIndex > 0
                && grid[colorIndex][valueIndex - 1] >= 1;
        boolean two_left = valueIndex > 1
                && grid[colorIndex][valueIndex - 2] >= 1;
        boolean one_right = valueIndex < Tile.TILES_PER_COLOR - 1
                && grid[colorIndex][valueIndex + 1] >= 1;
        boolean two_right = valueIndex < Tile.TILES_PER_COLOR - 2
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
        if(grid[colorIndex][valueIndex] == 0) //return null if there is no tile in the given index.
            return null;
        int consecutiveTotal;
        int specialCaseTotal = 0; //Special Case: {..12,13,1}
        int colorCount = 0;
        int low = valueIndex;
        int high = valueIndex;
        while(low >= 1 && grid[colorIndex][low - 1] >= 1)
            low--;
        while(high < Tile.TILES_PER_COLOR - 1 && grid[colorIndex][high + 1] >= 1)
            high++;
        consecutiveTotal = high - low + 1;
        if (high == Tile.TILES_PER_COLOR - 1 && grid[colorIndex][0] >= 1){
            consecutiveTotal++;
            high = 0;
        }
        int specialCaseLow = -1;
        int specialCaseHigh = -1;
        if(grid[colorIndex][0] == 1 && grid[colorIndex][Tile.TILES_PER_COLOR - 1] == 1){ //minimal condition{12,13,1} for special case.
            specialCaseLow = Tile.TILES_PER_COLOR - 1;
            specialCaseHigh = 0;
            while(specialCaseLow >= 1 && grid[colorIndex][specialCaseLow - 1] >= 1)
                specialCaseLow--;
            specialCaseTotal = Tile.TILES_PER_COLOR - specialCaseLow + 1;
        }

        short[] colorGrid = new short[Tile.Color.values().length - 1];

        for(int i = 0; i < Tile.Color.values().length - 1; i++) {
            if (grid[i][valueIndex] == 1) {
                colorGrid[i] = 1;
                colorCount++;
            }
        }
        //Another special case: both horizontal and vertical.
        if(colorCount > GROUP_MIN_COUNT_CONDITION){
            if(consecutiveTotal >= specialCaseTotal) {
                if(consecutiveTotal >= GROUP_MIN_COUNT_CONDITION){
                    short[] res = new short[2 + colorGrid.length];
                    res[0] = (short)low;
                    res[1] = (short)high;
                    System.arraycopy(colorGrid, 0, res, 2, colorGrid.length);
                    return res;
                }
            }
            else if(specialCaseTotal >= GROUP_MIN_COUNT_CONDITION){
                short[] res = new short[2 + colorGrid.length];
                res[0] = (short)specialCaseLow;
                res[1] = (short)specialCaseHigh;
                System.arraycopy(colorGrid, 0, res, 2, colorGrid.length);
                return res;
            }
        }

        //Normal case: hortizontal or vertical.
        if(colorCount >= consecutiveTotal){
            if(colorCount >= specialCaseTotal){ //max: colorCount
                return colorGrid;
            }
        }else if(consecutiveTotal >= specialCaseTotal){ //max: consequtiveTotal
            return new short[]{(short) low, (short) high};
        }else{ //max:specialCaseTotal
            return new short[]{(short) specialCaseLow, (short) specialCaseLow};
        }
        return null;
        /*
        if(colorCount > GROUP_MIN_COUNT_CONDITION){
            if (consecutiveTotal > specialCaseTotal && consecutiveTotal >= GROUP_MIN_COUNT_CONDITION){
                short[] res = new short[2 + colorGrid.length];
                res[0] = (short)low;
                res[1] = (short)high;
                System.arraycopy(colorGrid, 0, res, 2, colorGrid.length);
                return res;
            }else if(specialCaseTotal >  consecutiveTotal && specialCaseTotal >= GROUP_MIN_COUNT_CONDITION){
                short[] res = new short[2 + colorGrid.length];
                res[0] = (short)specialCaseLow;
                res[1] = (short)specialCaseHigh;
                System.arraycopy(colorGrid, 0, res, 2, colorGrid.length);
                return res;
            }
        }
        if (consecutiveTotal > specialCaseTotal){
            if (consecutiveTotal > colorCount){
                return new short[]{(short) low, (short) high};
            }
            return new short[]{(short) specialCaseLow, (short) specialCaseLow};
        }
        */

    }

    /**
     * This is just an helper method. This method will be merged with bestSet() method, and discarded to be deprecated in the following versions.
     * @param info information about the grid.
     * @return the grid denoting all the tile locations.
     */
    public short[][] gridize(short[] info, int colorIndex, int valueIndex){
        short[][] res = new short[Tile.Color.values().length - 1][Tile.TILES_PER_COLOR];

        if(info == null){
            return res;
        }
        else if(info.length == 2){ //only low and high.
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
                res[i][valueIndex] = info[i];
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
