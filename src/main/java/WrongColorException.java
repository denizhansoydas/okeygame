public class WrongColorException extends Exception{
    WrongColorException(int number){
        System.err.println("Wrong coloring with number: " + number);
    }
}
