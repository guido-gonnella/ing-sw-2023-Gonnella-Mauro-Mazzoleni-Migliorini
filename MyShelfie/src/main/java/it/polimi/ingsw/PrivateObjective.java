package it.polimi.ingsw;
import java.util.ArrayList;
/**
 * the Private objective a solution to "hardcoding" the ElementObjectives inside the constructor it adds predefined elements according to an id
 *
 * @author Andrea Migliorini
 */
public class PrivateObjective {
    private List<ElementObjective> objective;
    public PrivateObjective(int id) throws ImpossiblePrivateObjException{
        objective=  new ArrayList<ElementObjective>;
        switch(id){
            case 1:
                objective.add(new ElementObjective(x,x,CAT));
                objective.add(new ElementObjective(x,x,TROPHY));
                objective.add(new ElementObjective(x,x,GAME));
                objective.add(new ElementObjective(x,x,PLANT));
                objective.add(new ElementObjective(x,x,FRAME));
                objective.add(new ElementObjective(x,x,BOOK));
                break;
            case 2:
                objective.add(new ElementObjective(x,x,CAT));
                objective.add(new ElementObjective(x,x,TROPHY));
                objective.add(new ElementObjective(x,x,GAME));
                objective.add(new ElementObjective(x,x,PLANT));
                objective.add(new ElementObjective(x,x,FRAME));
                objective.add(new ElementObjective(x,x,BOOK));
                break;
            case 3:
                objective.add(new ElementObjective(x,x,CAT));
                objective.add(new ElementObjective(x,x,TROPHY));
                objective.add(new ElementObjective(x,x,GAME));
                objective.add(new ElementObjective(x,x,PLANT));
                objective.add(new ElementObjective(x,x,FRAME));
                objective.add(new ElementObjective(x,x,BOOK));
                break;
            case 4:
                objective.add(new ElementObjective(x,x,CAT));
                objective.add(new ElementObjective(x,x,TROPHY));
                objective.add(new ElementObjective(x,x,GAME));
                objective.add(new ElementObjective(x,x,PLANT));
                objective.add(new ElementObjective(x,x,FRAME));
                objective.add(new ElementObjective(x,x,BOOK));
                break;
            case 5:
                objective.add(new ElementObjective(x,x,CAT));
                objective.add(new ElementObjective(x,x,TROPHY));
                objective.add(new ElementObjective(x,x,GAME));
                objective.add(new ElementObjective(x,x,PLANT));
                objective.add(new ElementObjective(x,x,FRAME));
                objective.add(new ElementObjective(x,x,BOOK));
                break;
            case 6:
                objective.add(new ElementObjective(x,x,CAT));
                objective.add(new ElementObjective(x,x,TROPHY));
                objective.add(new ElementObjective(x,x,GAME));
                objective.add(new ElementObjective(x,x,PLANT));
                objective.add(new ElementObjective(x,x,FRAME));
                objective.add(new ElementObjective(x,x,BOOK));
                break;
            case 7:
                objective.add(new ElementObjective(x,x,CAT));
                objective.add(new ElementObjective(x,x,TROPHY));
                objective.add(new ElementObjective(x,x,GAME));
                objective.add(new ElementObjective(x,x,PLANT));
                objective.add(new ElementObjective(x,x,FRAME));
                objective.add(new ElementObjective(x,x,BOOK));
                break;
            case 8:
                objective.add(new ElementObjective(x,x,CAT));
                objective.add(new ElementObjective(x,x,TROPHY));
                objective.add(new ElementObjective(x,x,GAME));
                objective.add(new ElementObjective(x,x,PLANT));
                objective.add(new ElementObjective(x,x,FRAME));
                objective.add(new ElementObjective(x,x,BOOK));
                break;
            case 9:
                objective.add(new ElementObjective(x,x,CAT));
                objective.add(new ElementObjective(x,x,TROPHY));
                objective.add(new ElementObjective(x,x,GAME));
                objective.add(new ElementObjective(x,x,PLANT));
                objective.add(new ElementObjective(x,x,FRAME));
                objective.add(new ElementObjective(x,x,BOOK));
                break;
            case 10:
                objective.add(new ElementObjective(x,x,CAT));
                objective.add(new ElementObjective(x,x,TROPHY));
                objective.add(new ElementObjective(x,x,GAME));
                objective.add(new ElementObjective(x,x,PLANT));
                objective.add(new ElementObjective(x,x,FRAME));
                objective.add(new ElementObjective(x,x,BOOK));
                break;
            case 11:
                objective.add(new ElementObjective(x,x,CAT));
                objective.add(new ElementObjective(x,x,TROPHY));
                objective.add(new ElementObjective(x,x,GAME));
                objective.add(new ElementObjective(x,x,PLANT));
                objective.add(new ElementObjective(x,x,FRAME));
                objective.add(new ElementObjective(x,x,BOOK));
                break;
            case 12:
                objective.add(new ElementObjective(x,x,CAT));
                objective.add(new ElementObjective(x,x,TROPHY));
                objective.add(new ElementObjective(x,x,GAME));
                objective.add(new ElementObjective(x,x,PLANT));
                objective.add(new ElementObjective(x,x,FRAME));
                objective.add(new ElementObjective(x,x,BOOK));
                break;
            default:
                throw new ImpossiblePrivateObjException();

        }
    }

    public List<ElementObjective> getObjective() {
        return objective;
    }
}
