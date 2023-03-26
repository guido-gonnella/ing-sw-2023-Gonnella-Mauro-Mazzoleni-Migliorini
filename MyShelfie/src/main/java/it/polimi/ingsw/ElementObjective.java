package it.polimi.ingsw;
/**
 * basic element of the private objective to assign points, it has the coordinates and type of the virtual tile of the shelf
 *
 * @author Andrea Migliorini
 */
public class ElementObjective {
    private int x;
    private int y;
    private Type type;
    public ElementObjective(int i, int j, Type kind) // simple constructor method
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
