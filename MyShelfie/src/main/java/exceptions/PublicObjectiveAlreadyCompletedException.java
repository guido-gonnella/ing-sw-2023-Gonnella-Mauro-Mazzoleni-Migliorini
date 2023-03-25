package exceptions;

/**
 * Exception thrown when a player wants to set as true a public objective he has already completed
 *
 * @author Guido Gonnella
 */
public class PublicObjectiveAlreadyCompletedException extends Exception{
    public PublicObjectiveAlreadyCompletedException(String message){
        super(message);
    }
}
