package it.polimi.ingsw;
import java.util.ArrayList;

public class ElementObjective { //basic element of the private objective to assign points
    private int x;
    private int y;
    private Type type;
    ElementObjective(int i, int j, Type kind) // simple constructor method
    {
        this.type=kind;
        this.x=i;
        this.y=j;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Type getType() {
        return type;
    }
}
