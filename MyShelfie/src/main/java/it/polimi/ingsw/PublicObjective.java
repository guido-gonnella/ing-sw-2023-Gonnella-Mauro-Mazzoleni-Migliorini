package it.polimi.ingsw;

/**
 * Class that contains a common objective and its realization
 *
 * @author Pierantonio Mauro
 */
public class PublicObjective {
    private final CommonObj obj;

    public PublicObjective(CommonObj obj){
        this.obj = obj;
    }

    /**
     * This method take as a parameter a shelf and return a boolean
     * indicating if the shelf have reached or not the objective
     * @param shelf
     * @return true if the shelf have reached the common objective
     * false otherwise
     * @author Pierantonio Mauro
     */
    public boolean getResultObjective(Shelf shelf){
        return this.obj.reach(shelf);
    }
}