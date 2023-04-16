package it.polimi.ingsw;
/**
 * basic element of the private objective to assign points, it has the coordinates and type of the virtual tile of the shelf
 *
 * @author Andrea Migliorini
 */
public class ElementObjective {
    private final int x;
    private final int y;
    private final Type type;
    public ElementObjective(int x, int y, Type kind) // simple constructor method
    {
        this.type=kind;
        this.x=x;
        this.y=y;
    }

    /**
     * basic getters
     */
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
