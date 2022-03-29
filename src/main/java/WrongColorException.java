public class WrongColorException extends RuntimeException{
    WrongColorException(int number){
        System.err.println("Wrong coloring with number: " + number);
    }
}
