package it.polimi.ingsw.Model;

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
        this.publicObjective.add(new PublicObjective("cross"));
        this.publicObjective.add(new PublicObjective("eight"));
        this.publicObjective.add(new PublicObjective("diag"));
        this.publicObjective.add(new PublicObjective("diffCol"));
        this.publicObjective.add(new PublicObjective("diffRow"));
        this.publicObjective.add(new PublicObjective("colThreeTypes"));
        this.publicObjective.add(new PublicObjective("rowThreeTypes"));
        this.publicObjective.add(new PublicObjective("angles"));
        this.publicObjective.add(new PublicObjective("twoSquares"));
        this.publicObjective.add(new PublicObjective("stair"));
      //this.publicObjective.add(new PublicObjective("sixCouples"));
      //this.publicObjective.add(new PublicObjective("fourQuadruple"));
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
