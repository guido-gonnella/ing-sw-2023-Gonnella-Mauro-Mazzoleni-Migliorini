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

    /**
     * initialize the array list with one of each {@link PrivateObjective private objective}
     * @author Guido Gonnella
     */
    public DeckOfPrivateObjectives(){
        this.privateObjectives = new ArrayList<PrivateObjective>();

        for(int i = 0; i < 12; i++) {
            //add a new private objective in the arraylist, one of each kind
            privateObjectives.add(new PrivateObjective(i+1));
        }
    }

    /**
     * Return a list containing the {@link PrivateObjective private objectives}, which then are distributed to the players.<br>
     * There are as many elements as the number of players passed as a parameter.
     * @param numPlayers the number of player in the game
     * @return an arrayList containing the privateObjectives
     */
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

        for (int j=0; j<numPlayers; j++) {
            a = rand.nextInt(randomTempList.size());
            i = randomTempList.remove(a);
            SingleRoundObjectives.add(this.privateObjectives.get(i));
        }

        return SingleRoundObjectives;
    }
}