package it.polimi.ingsw;
import java.util.ArrayList;
/**
 * Deck of Private Objectives, its a list of 4 private objectives
 *
 * @author Andrea Migliorini
 */
public class DeckOfPrivateObjectives {
    private ArrayList<PrivateObjective> privateObjectives;

    public DeckOfPrivateObjectives() {
        ArrayList<Integer> randomTempList;
        objective = new ArrayList<PrivateObjective>;
        Integer id;
        for (Integer i = 1; i < 13; i++) { //this creates a list of 12 ints that range from 1 to 12 that get removed once you extract one so that there are no repeated privateobjectives
            randomTempList.add(i);
        }
        for (int j = 0; j < 4; j++) {
            i = ThreadLocalRandom.current().nextInt(randomTempList.size());
            id = randomTempList.remove(i);
            try {
                objective.add(new PrivateObjective(id));
            } catch (ImpossiblePrivateObjException) {
                System.out.println(impossiblePrivateobj);

            }


        }
    }

    public ArrayList<PrivateObjective> getPrivateObjectives() {
        return privateObjectives;
    }
}