package exceptions;

public class OutOfShelfException extends Exception{
    /*
    * Exception used when the user pass a wrong column number, outside the range of the shelf
    * */
    public OutOfShelfException(String message){
        super(message);
    }
}
