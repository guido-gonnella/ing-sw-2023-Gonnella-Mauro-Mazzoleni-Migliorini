package it.polimi.ingsw.Model;

import it.polimi.ingsw.Enumeration.PubObjType;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class that contains all the common objectives
 *
 * @author Pierantonio Mauro
 */

public class DeckOfPublicObjectives {
    private final ArrayList<PublicObjective> publicObjective;

    public DeckOfPublicObjectives(){
        this.publicObjective = new ArrayList<>();
        this.publicObjective.add(new PublicObjective(PubObjType.CROSS));
        this.publicObjective.add(new PublicObjective(PubObjType.EIGHT));
        this.publicObjective.add(new PublicObjective(PubObjType.DIAG));
        this.publicObjective.add(new PublicObjective(PubObjType.DIFF_COL));
        this.publicObjective.add(new PublicObjective(PubObjType.DIFF_ROW));
        this.publicObjective.add(new PublicObjective(PubObjType.COL_THREE_TYPES));
        this.publicObjective.add(new PublicObjective(PubObjType.ROW_THREE_TYPES));
        this.publicObjective.add(new PublicObjective(PubObjType.ANGLES));
        this.publicObjective.add(new PublicObjective(PubObjType.TWO_SQUARES));
        this.publicObjective.add(new PublicObjective(PubObjType.STAIR));
        this.publicObjective.add(new PublicObjective(PubObjType.SIX_COUPLES));
        this.publicObjective.add(new PublicObjective(PubObjType.FOUR_QUADRUPLES));
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
        }while(index1==index2);
        chosen.add(this.publicObjective.get(index2));

        return chosen;
    }
}
