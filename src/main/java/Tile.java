import java.util.Arrays;

/**
 * This class is for a single Tile item in the game.
 * @author Denizhan Soydas
 * @version 1.2
 */
public class Tile{
    //static properties, methods and enumerations
    public static final int TILES_PER_COLOR = 13;

    /**
     * This method is to determine the max number a single tile can have.
     * @return the max number
     */
    private static int maxNumber(){
        return TILES_PER_COLOR * (Color.values().length - 1);
    }
    /**
     * gives the index of the color in the grids.
     * @param color is the given color
     * @return the index of color in the grids.
     */
    public static int getColorIndex(Color color){
        return Arrays.asList(Color.values()).indexOf(color);
    }
    public enum Color {
        YELLOW,
        BLUE,
        BLACK,
        RED,
        FAKE_JOKER
    }
    //properties
    private int no;

    //constructors
    public Tile(int no) throws WrongColorException{
        if (no >= 0 && no <= maxNumber())
            this.no = no;
        else
            throw new WrongColorException(no);
    }
    public Tile(Color color, int tileValue) throws WrongColorException{
        this(TILES_PER_COLOR * Arrays.asList(Color.values()).indexOf(color) + tileValue - 1);
    }

    //methods

    /**
     * Determines whether the tiles have the same color
     * @param tile the second tile.(first is "this")
     * @return truth value denoting they have the same color or not.
     */
    public boolean sameColor(Tile tile){
        return getColor() == tile.getColor();
    }

    /**
     * Determines whether the tiles are the same.(Deep comparison, not shallow!)
     * @param tile the second tile (first is "this")
     * @return truth value denoting they are the same.
     */
    public boolean equals(Tile tile){
        return getNo() == tile.getNo();
    }

    /**
     * Determines the tiles have the same value.
     * @param tile the second tile (first is "this")
     * @return truth value denoting they have the same value.
     */
    public boolean sameValue(Tile tile){
        return getValue() == tile.getValue();
    }
    /**
     * Determines whether the tile is a fake joker or not
     * @return truth value denoting whether it is a fake joker or not.
     */
    public boolean isFakeJoker(){
        return getColor() == Color.FAKE_JOKER;
    }



    public int getNo() {
        return no;
    }
    public void setNo(int no) {
        this.no = no;
    }

    /**
     * A simple method to get the Color of a tile.
     * @return Color of the tile.
     * @throws WrongColorException if the given tile has a wrong(that should not exist) color.
     */
    public Color getColor() throws WrongColorException{
        if (no == maxNumber())
            return Color.FAKE_JOKER;
        int colorNo = no / TILES_PER_COLOR;

        return switch (colorNo) {
            case 0 -> Color.YELLOW;
            case 1 -> Color.BLUE;
            case 2 -> Color.BLACK;
            case 3 -> Color.RED;
            default -> throw new WrongColorException(no);
        };
    }

    /**
     * A simple method to get the value of a tile.
     * @return value of the tile.
     */
    public int getValue(){
        return no != maxNumber() ? no % TILES_PER_COLOR + 1 : -1; //Fake joker does not have a value. Therefore, its value is denoted with -1.
    }

    /**
     * toString() method to convert a tile to a better format.
     * @return "COLOR-VALUE"
     */
    public String toString(){
        try {
            //Longer naming
            //return "No: " + no + " Color: " + getColor().toString() + " Value: " + getValue();
            //Shorter naming
            if(getValue() == -1) //fake joker
                return getColor().toString();
            return getColor().toString() + "-" + getValue();
        } catch (WrongColorException e) {
            return "No: " + no + " Color: " + "UNKNOWN" + " Value: " + getValue();
        }
    }
}
