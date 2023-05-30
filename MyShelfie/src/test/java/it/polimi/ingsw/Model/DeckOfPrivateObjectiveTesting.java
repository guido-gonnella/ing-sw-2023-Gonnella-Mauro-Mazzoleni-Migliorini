package it.polimi.ingsw.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DeckOfPrivateObjectiveTesting {
    DeckOfPrivateObjectives deckOfPrivateObjectives = new DeckOfPrivateObjectives();
    @After
    public void reset() {
        deckOfPrivateObjectives = new DeckOfPrivateObjectives();
    }
    @Test
    public void getPrivateObjectives_with3Players() {
        ArrayList<PrivateObjective> privateObjectives = deckOfPrivateObjectives.getPrivateObjectives(3);

        assertEquals(privateObjectives.size(), 3);

        assertNotEquals(privateObjectives.get(0).getObjective(), privateObjectives.get(1).getObjective());
        assertNotEquals(privateObjectives.get(0).getObjective(), privateObjectives.get(2).getObjective());
        assertNotEquals(privateObjectives.get(1).getObjective(), privateObjectives.get(2).getObjective());
    }
}
