package exceptions;

public class ColumnAlreadyFullException extends Exception{
    /**
    *
    * Exception thrown when a column in a player's shelf is already full
    **/

    public ColumnAlreadyFullException(String message){
        super(message);
    }
}
