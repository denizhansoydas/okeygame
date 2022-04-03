/**
 * This is an exception class to report that the given tile number can not form a tile.
 * @version 1.1
 *
 */
public class WrongColorException extends RuntimeException{
    WrongColorException(int number){
        System.err.println("Wrong coloring with number: " + number);
    }
}
