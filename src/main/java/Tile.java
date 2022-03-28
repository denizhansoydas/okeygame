public class Tile {
    private int no;
    private static final int COLOR_PER_COLOR = 13;
    enum COLOR{
        YELLOW,
        BLUE,
        BLACK,
        RED,
        FAKE_JOKER
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
    public COLOR getColor() throws WrongColorException{
        int colorNo = no / COLOR_PER_COLOR;
        return switch (colorNo) {
            case 0 -> COLOR.YELLOW;
            case 1 -> COLOR.BLUE;
            case 2 -> COLOR.BLACK;
            case 3 -> COLOR.RED;
            default -> throw new WrongColorException(no);
        };

    }
}
