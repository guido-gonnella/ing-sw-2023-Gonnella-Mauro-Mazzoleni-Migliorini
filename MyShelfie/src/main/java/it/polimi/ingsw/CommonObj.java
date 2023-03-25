package it.polimi.ingsw;

//@FunctionalInterface

/**
 * Functional interface that implements the common objectives.
 * The interface takes a shelf and return a boolean:
 * 1 if the shelf have reached the common objective
 * 0 otherwise
 *
 * @author Pierantonio Mauro
 */
public interface CommonObj {
    boolean reach(Shelf shelf);
}
