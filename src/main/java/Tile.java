import java.util.Arrays;

/**
 * @author Denizhan Soydas
 * @version 1.0-SNAPSHOT
 */
public class Tile{
    //static properties, methods and enumerations
    public static final int TILES_PER_COLOR = 13;
    public static int maxNumber(){
        return TILES_PER_COLOR * (Color.values().length - 1);
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
        if (no >= 0 && no <= 52)
            this.no = no;
        else
            throw new WrongColorException(no);
    }
    public Tile(Color color, int tileValue) throws WrongColorException{
        int no = TILES_PER_COLOR * Arrays.asList(Color.values()).indexOf(color) + tileValue;
        if (no < 0 || no > maxNumber())
            throw new WrongColorException(no);
        this.no = no;
    }

    //methods
    public int getNo() {
        return no;
    }
    public void setNo(int no) {
        this.no = no;
    }
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
    //will be edited so that it will return Joker's number when it is a fake joker.
    public int getValue(){
        return no != maxNumber() ? no % TILES_PER_COLOR + 1 : -1;
    }
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
    public boolean sameColor(Tile tile){
        return getColor() == tile.getColor();
    }
    public boolean equals(Tile tile){
        return getNo() == tile.getNo();
    }
    public boolean sameValue(Tile tile){
        return getValue() == tile.getValue();
    }
    public boolean isFakeJoker(){
        return getColor() == Color.FAKE_JOKER;
    }
    public int getColorIndex(Color color){
        return Arrays.asList(Color.values()).indexOf(color);
    }
}
