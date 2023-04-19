package it.polimi.ingsw;

/**
 * Class that contains a common objective and its realization
 *
 * @author Pierantonio Mauro
 */
public class PublicObjective {
    private final CommonObj obj;

    public PublicObjective(CommonObj obj){
        this.obj = obj;
    }

    /**
     * This method take as a parameter a shelf and return a boolean
     * indicating if the shelf have reached or not the objective
     * @param shelf
     * @return true if the shelf have reached the common objective
     * false otherwise
     * @author Pierantonio Mauro
     */
    public boolean getResultObjective(Shelf shelf){
        return this.obj.reach(shelf);
    }
}

/*
example of lambda

CommonObj cross = (shelf) ->{
    int i,j;
    int contCross = 0;
    Type tempShelf[6][5] = shelf.getShelf();
    for(i=1; i<5 && contCross == 0; i++){
        for(j=1; j<4 && contCross == 0; j++){
        Type tempType = tempShelf[i][j];
            if((tempType == tempShelf[i-1][j-1]) && (tempType == tempShelf[i+1][j+1]) &&
             (tempType == tempShelf[i+1][j-1]) && (tempType == tempShelf[i-1][j+1]))
                {contCross = 1;}
        }
    }
    return contCross;
}

 */
