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

CommonObj cross = (shelf) -> {
        int i,j;
        int contCross = 0;
        Optional<Tile>[][] tempShelf = shelf.getShelf();
        for(i=1; i<5 && contCross==0; i++){
            for(j=0; j<4 && contCross==0; j++){
                if(tempShelf[i][j].isPresent()){
                    Type tempType = tempShelf[i][j].get().getType();
                    if((tempType == tempShelf[i-1][j-1].get().getType()) &&
                       (tempType == tempShelf[i+1][j+1].get().getType()) &&
                       (tempType == tempShelf[i+1][j-1].get().getType()) &&
                       (tempType == tempShelf[i-1][j+1].get().getType()))
                    {contCross = 1;}
                }
            }
        }
        if(contCross==0)
            return false;
        else
            return true;
    };

CommonObj eight = (shelf) -> {
        int i,j;
        int contCat = 0;
        int contFrame = 0;
        int contGame = 0;
        int contPlant = 0;
        int contTrophy = 0;
        int contBook = 0;
        Type tempShelf[6][5] = shelf.getShelf().orElse(null);

        for(i=0; i<5;


    }
 */
