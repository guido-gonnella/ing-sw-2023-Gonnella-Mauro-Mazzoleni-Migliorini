package it.polimi.ingsw.Model;
import it.polimi.ingsw.Model.Enumeration.Type;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * the Private objective a solution to "hardcoding" the ElementObjectives inside the constructor it adds
 * predefined elements according to an id
 *
 * @author Andrea Migliorini
 */
public class PrivateObjective implements Serializable {
    private final ArrayList<ElementObjective> objective;
    private int id;

    /**
     * Initialize the private objective with one of the twelve cases.
     * @param index choose the "card"
     * @author Guido Gonnella
     */
    public PrivateObjective(int index) {
        //initializing an array list of six element objectives
        objective = new ArrayList<ElementObjective>(6);

        //then add the chosen objective, adding the coordinates and the type of the tile

        switch (index) {
            case 1 -> {
                this.id=1;
                objective.add(new ElementObjective(0, 0, Type.PLANT));
                objective.add(new ElementObjective(0, 2, Type.FRAME));
                objective.add(new ElementObjective(1, 4, Type.CAT));
                objective.add(new ElementObjective(2, 3, Type.BOOK));
                objective.add(new ElementObjective(3, 1, Type.GAME));
                objective.add(new ElementObjective(5, 2, Type.TROPHY));
            }
            case 2 -> {
                this.id=2;
                objective.add(new ElementObjective(1, 1, Type.PLANT));
                objective.add(new ElementObjective(2, 0, Type.CAT));
                objective.add(new ElementObjective(2, 2, Type.GAME));
                objective.add(new ElementObjective(3, 4, Type.BOOK));
                objective.add(new ElementObjective(4, 3, Type.TROPHY));
                objective.add(new ElementObjective(5, 4, Type.FRAME));
            }
            case 3 -> {
                this.id=3;
                objective.add(new ElementObjective(1, 0, Type.FRAME));
                objective.add(new ElementObjective(1, 3, Type.GAME));
                objective.add(new ElementObjective(2, 2, Type.PLANT));
                objective.add(new ElementObjective(3, 1, Type.CAT));
                objective.add(new ElementObjective(3, 4, Type.TROPHY));
                objective.add(new ElementObjective(5, 0, Type.BOOK));
            }
            case 4 -> {
                this.id=4;
                objective.add(new ElementObjective(0, 4, Type.GAME));
                objective.add(new ElementObjective(2, 0, Type.TROPHY));
                objective.add(new ElementObjective(2, 2, Type.FRAME));
                objective.add(new ElementObjective(3, 3, Type.PLANT));
                objective.add(new ElementObjective(4, 1, Type.BOOK));
                objective.add(new ElementObjective(4, 2, Type.CAT));
            }
            case 5 -> {
                this.id=5;
                objective.add(new ElementObjective(1, 1, Type.TROPHY));
                objective.add(new ElementObjective(3, 1, Type.FRAME));
                objective.add(new ElementObjective(3, 2, Type.BOOK));
                objective.add(new ElementObjective(4, 4, Type.PLANT));
                objective.add(new ElementObjective(5, 0, Type.GAME));
                objective.add(new ElementObjective(5, 3, Type.CAT));
            }
            case 6 -> {
                this.id=6;
                objective.add(new ElementObjective(0, 2, Type.TROPHY));
                objective.add(new ElementObjective(0, 4, Type.CAT));
                objective.add(new ElementObjective(2, 3, Type.BOOK));
                objective.add(new ElementObjective(4, 1, Type.GAME));
                objective.add(new ElementObjective(4, 3, Type.FRAME));
                objective.add(new ElementObjective(5, 0, Type.PLANT));
            }
            case 7 -> {
                this.id=7;
                objective.add(new ElementObjective(0, 0, Type.CAT));
                objective.add(new ElementObjective(1, 3, Type.FRAME));
                objective.add(new ElementObjective(2, 1, Type.PLANT));
                objective.add(new ElementObjective(3, 0, Type.TROPHY));
                objective.add(new ElementObjective(4, 4, Type.GAME));
                objective.add(new ElementObjective(5, 2, Type.BOOK));
            }
            case 8 -> {
                this.id=8;
                objective.add(new ElementObjective(0, 4, Type.FRAME));
                objective.add(new ElementObjective(1, 1, Type.CAT));
                objective.add(new ElementObjective(2, 2, Type.TROPHY));
                objective.add(new ElementObjective(3, 0, Type.PLANT));
                objective.add(new ElementObjective(4, 3, Type.BOOK));
                objective.add(new ElementObjective(5, 3, Type.GAME));
            }
            case 9 -> {
                this.id=9;
                objective.add(new ElementObjective(0, 2, Type.GAME));
                objective.add(new ElementObjective(2, 2, Type.CAT));
                objective.add(new ElementObjective(3, 4, Type.BOOK));
                objective.add(new ElementObjective(4, 1, Type.TROPHY));
                objective.add(new ElementObjective(4, 4, Type.PLANT));
                objective.add(new ElementObjective(5, 0, Type.FRAME));
            }
            case 10 -> {
                this.id=10;
                objective.add(new ElementObjective(0, 4, Type.TROPHY));
                objective.add(new ElementObjective(2, 0, Type.BOOK));
                objective.add(new ElementObjective(3, 3, Type.CAT));
                objective.add(new ElementObjective(4, 1, Type.FRAME));
                objective.add(new ElementObjective(5, 3, Type.PLANT));
            }
            case 11 -> {
                this.id=11;
                objective.add(new ElementObjective(0, 2, Type.PLANT));
                objective.add(new ElementObjective(1, 1, Type.BOOK));
                objective.add(new ElementObjective(2, 0, Type.GAME));
                objective.add(new ElementObjective(3, 2, Type.FRAME));
                objective.add(new ElementObjective(4, 4, Type.CAT));
                objective.add(new ElementObjective(5, 3, Type.TROPHY));
            }
            case 12 -> {
                this.id=12;
                objective.add(new ElementObjective(0, 2, Type.BOOK));
                objective.add(new ElementObjective(1, 1, Type.PLANT));
                objective.add(new ElementObjective(2, 2, Type.FRAME));
                objective.add(new ElementObjective(3, 3, Type.TROPHY));
                objective.add(new ElementObjective(4, 4, Type.GAME));
                objective.add(new ElementObjective(5, 0, Type.CAT));
            }
        }
    }

    /**
     * basic getter
     */
    public ArrayList<ElementObjective> getObjective() {
        return this.objective;
    }

    public int getId() {return id;}
}
