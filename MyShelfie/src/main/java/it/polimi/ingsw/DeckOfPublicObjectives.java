package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class that contains all the common objectives
 *
 * @author Pierantonio Mauro
 */

public class DeckOfPublicObjectives {
    private final ArrayList<PublicObjective> publicObjective;

    DeckOfPublicObjectives(ArrayList<PublicObjective> pubObj){
        this.publicObjective = pubObj;
    }

    /**
     * This method choose randomly two PublicObjective
     * @return two random PublicObjective
     * @author Pierantonio Mauro
     */
    public ArrayList<PublicObjective> getPublicObjective() {
        int index1, index2;
        ArrayList<PublicObjective> chosen = new ArrayList<PublicObjective>();
        Random rand = new Random();
        index1 = rand.nextInt(this.publicObjective.size());
        chosen.add(this.publicObjective.get(index1));

        do{
            index2 = rand.nextInt(this.publicObjective.size());
        }while(chosen.contains(this.publicObjective.get(index2)));
        chosen.add(this.publicObjective.get(index2));

        return chosen;
    }
}
