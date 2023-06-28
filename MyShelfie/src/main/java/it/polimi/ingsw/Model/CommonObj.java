package it.polimi.ingsw.Model;

//@FunctionalInterface

/**
 * Functional interface that implements the common objectives.
 * The interface takes a shelf and returns a boolean:
 * 1 (true) if the shelf has reached the common objective
 * 0 (false) otherwise
 *
 * @author Pierantonio Mauro
 */
public interface CommonObj {
    boolean reach(Shelf shelf);
}
