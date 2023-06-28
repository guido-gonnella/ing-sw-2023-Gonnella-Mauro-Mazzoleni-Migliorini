package it.polimi.ingsw.Model;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PrivateObjectiveTest {

    @Test
    public void privateObjectiveConstructor_everyIndexPrinted() {
        ArrayList<PrivateObjective> privateObjectives = new ArrayList<>();

        for(int i=0; i<12; i++) privateObjectives.add(new PrivateObjective(i+1));
        for(int i=0; i<12; i++) assertEquals(privateObjectives.get(i).getId(), i+1);

        for(PrivateObjective privateObjective : privateObjectives) {
            System.out.print("-");
            for (int i = 0; i < 5; i++) {
                System.out.print("\u001B[30m" + "-" + "\u001B[0m" + i + "\u001B[30m" + "-" + "\u001B[0m");
            }
            System.out.print ("\n");
            //prima z era 0, adesso non da problemi nel mostrare gli obiettivi privati
            int z = 0;
            for (int i = 0; i < 6; i++) {
                System.out.print(i);
                for (int j = 0; j < 5; j++) {
                    if (privateObjective.getObjective().get(z).getROW() == i && privateObjective.getObjective().get(z).getCOL() == j){
                        switch (privateObjective.getObjective().get(z).getType()) {
                            case TROPHY -> System.out.print("\u001B[36m" + "[T]" + "\u001B[0m");
                            case FRAME -> System.out.print("\u001B[34m" + "[F]" + "\u001B[0m");
                            case PLANT -> System.out.print("\u001B[35m" + "[P]" + "\u001B[0m");
                            case GAME -> System.out.print("\u001B[33m" + "[G]" + "\u001B[0m");
                            case BOOK -> System.out.print("\u001B[37m" + "[B]" + "\u001B[0m");
                            case CAT -> System.out.print("\u001B[32m" + "[C]" + "\u001B[0m");
                            default -> System.out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                        }
                        if(z<5) z++;
                    }else {
                        System.out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                    }
                }
                System.out.print("\n");
            }
        }
    }
}

