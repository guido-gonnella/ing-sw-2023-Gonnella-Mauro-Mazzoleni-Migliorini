package it.polimi.ingsw;
import java.util.ArrayList;
import java.util.Random;

/**
 * Deck of Private Objectives, it's a list containing all of the possible objectives
 *
 * @author Andrea Migliorini
 */
public class DeckOfPrivateObjectives {
    private ArrayList<PrivateObjective> privateObjectives;

    public DeckOfPrivateObjectives() {
        privateObjectives = new ArrayList<PrivateObjective>();
        }


    public void setPrivateObjectives(PrivateObjective privobj) { //adds the private objectives previously generated in main
        this.privateObjectives.add(privobj);
    }

    public ArrayList<PrivateObjective> getPrivateObjectives(int numplayers) { //randomly  adds of the 12 private objectives the same number of players to a list of private objectives as output
        ArrayList<PrivateObjective> SingleRoundObjectives;
        int a;
        SingleRoundObjectives= new ArrayList<>();
        ArrayList<Integer> randomTempList;
        randomTempList=new ArrayList<>();
        Random rand;
        rand=new Random();
        Integer i;
        for (i = 0; i < 12; i++) { //this creates a list of 12 ints that range from 1 to 12 that get removed once you extract one so that there are no repeated private objectives
            randomTempList.add(i);
        }
        for (int j=0; j<numplayers;j++){
            a= rand.nextInt(randomTempList.size());
            i= randomTempList.remove(a);
            SingleRoundObjectives.add(privateObjectives.get(i));

        }

        return(SingleRoundObjectives);
    }

}