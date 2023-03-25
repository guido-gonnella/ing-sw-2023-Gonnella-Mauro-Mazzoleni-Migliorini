package it.polimi.ingsw;

/**
 * Class that contains a common objective and its realization
 *
 * @author Pierantonio Mauro
 */
public class PublicObjective {
    private final CommonObj obj;

    PublicObjective(CommonObj obj){
        this.obj = obj;
    }

    public CommonObj getObjective(){
        return this.obj;
    }
}
