package it.polimi.ingsw.Model;
import java.util.ArrayList;
import java.util.Random;

/**
 * Deck of Private Objectives, it's a list containing all of the possible objectives
 *
 * @author Andrea Migliorini
 */
public class DeckOfPrivateObjectives {
    private final ArrayList<PrivateObjective> privateObjectives;

    public DeckOfPrivateObjectives(ArrayList<PrivateObjective> privObj) {
        this.privateObjectives = new ArrayList<PrivateObjective>(privObj);
        }


        //this probably won't be used since the constructor already will include all the private objectives
    public void setPrivateObjectives(PrivateObjective privObj) { //adds the private objectives previously generated in main
        this.privateObjectives.add(privObj);
    }

    public ArrayList<PrivateObjective> getPrivateObjectives(int numPlayers) {
        //randomly  adds of the 12 private objectives the same number of players to a list of private objectives as output
        ArrayList<PrivateObjective> SingleRoundObjectives = new ArrayList<>();
        ArrayList<Integer> randomTempList = new ArrayList<>();
        Random rand = new Random();
        int a;
        Integer i;

        for (i = 0; i < 12; i++) {
            //this creates a list of 12 ints that range from 1 to 12 that get removed once you extract one
            // so that there are no repeated private objectives
            randomTempList.add(i);
        }

        for (int j=0; j<numPlayers;j++){
            a = rand.nextInt(randomTempList.size());
            i = randomTempList.remove(a);
            SingleRoundObjectives.add(this.privateObjectives.get(i));
        }

        return SingleRoundObjectives;
    }

}