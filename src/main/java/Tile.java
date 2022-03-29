import java.util.Arrays;

/**
 * This class is deprecated.
 */
public class Tile{
    //static properties, methods and enumerations
    public static final int TILE_PER_COLOR = 13;

    public static COLOR getColor(int no) throws WrongColorException{
        if (no == maxNumber())
            return COLOR.FAKE_JOKER;
        int colorNo = no / TILE_PER_COLOR;

        return switch (colorNo) {
            case 0 -> COLOR.YELLOW;
            case 1 -> COLOR.BLUE;
            case 2 -> COLOR.BLACK;
            case 3 -> COLOR.RED;
            default -> throw new WrongColorException(no);
        };
    }
    public static int maxNumber(){
        return TILE_PER_COLOR * (COLOR.values().length - 1);
    }
    public enum COLOR{
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
        if (no >= 0 && no <= 52)
            this.no = no;
        else
            throw new WrongColorException(no);
    }
    public Tile(int tileValue, COLOR color) throws WrongColorException{
        int no = TILE_PER_COLOR * Arrays.asList(COLOR.values()).indexOf(color) + tileValue;
        if (no < 0 || no > maxNumber())
            throw new WrongColorException(no);
        this.no = no;
    }

    //methods
    public int getNo() {
        return no;
    }
    public int getValue(){
        return no % TILE_PER_COLOR + 1;
    }
    public void setNo(int no) {
        this.no = no;
    }
    public String toString(){
        try {
            return "No: " + no + " Color: " + getColor(no).toString() + " Value: " + getValue();
        } catch (WrongColorException e) {
            return "No: " + no + " Color: " + "UNKNOWN" + " Value: " + getValue();
        }
    }
}
