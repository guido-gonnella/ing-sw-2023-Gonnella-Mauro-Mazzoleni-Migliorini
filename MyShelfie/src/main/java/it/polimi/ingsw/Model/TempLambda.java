package it.polimi.ingsw.Model;

import java.util.Optional;

public class TempLambda {
    CommonObj lambda;

    int HEIGHT = 6;
    int WIDTH = 5;



    public TempLambda(CommonObj lam){
        this.lambda = lam;
    }

    //tested, working
    CommonObj cross = (shelf) -> {
        int i,j;
        int contCross = 0;
        Optional<Tile>[][] tempShelf = shelf.getShelf();

        for(i=1; i<5 && contCross==0; i++){
            for(j=1; j<4 && contCross==0; j++){
                if(tempShelf[i][j].isPresent()){
                    Type tempType = tempShelf[i][j].get().getType();
                    if(tempShelf[i-1][j-1].isPresent() &&
                            tempShelf[i+1][j+1].isPresent() &&
                            tempShelf[i+1][j-1].isPresent() &&
                            tempShelf[i-1][j+1].isPresent()){
                        if(tempType.equals(tempShelf[i-1][j-1].get().getType()) &&
                                tempType.equals(tempShelf[i+1][j+1].get().getType()) &&
                                tempType.equals(tempShelf[i+1][j-1].get().getType()) &&
                                tempType.equals(tempShelf[i-1][j+1].get().getType())){contCross = 1;}
                    }
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
        Optional<Tile>[][] tempShelf = shelf.getShelf();

        for(i=0; i<6; i++){
            for(j=0; j<5; j++){
                if(tempShelf[i][j].isPresent()){
                    switch(tempShelf[i][j].get().getType()){
                        case CAT:
                            contCat++;
                            break;
                        case BOOK:
                            contBook++;
                            break;
                        case GAME:
                            contGame++;
                            break;
                        case FRAME:
                            contFrame++;
                            break;
                        case PLANT:
                            contPlant++;
                            break;
                        case TROPHY:
                            contTrophy++;
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        if(contCat>=8 || contBook>=8 || contGame>=8 || contFrame>=8 ||
           contPlant>=8 || contTrophy>=8)
            return true;
        else
            return false;
    };

    CommonObj diag = (shelf) -> {
        int i=0;
        int contDiag = 0;
        int k=0;
        Optional<Tile>[][] tempShelf = shelf.getShelf();

        while(i<2 && contDiag==0){
            if (tempShelf[i][0].isPresent()) {
                Type tempType = tempShelf[i][0].get().getType();
                int flag = 1;
                for (k = 1; k < 5 && flag == 1; k++) {
                    if (tempShelf[i + k][k].isEmpty())
                        flag = 0;
                    else if (tempType != tempShelf[i + k][k].get().getType()){
                        flag = 0;
                    }
                }
                if(flag==1)
                    contDiag=1;
            }
            i++;
        }

        i=0;
        while(i<2 && contDiag==0){
            if (tempShelf[i+4][0].isPresent()) {
                Type tempType = tempShelf[i+4][0].get().getType();
                int flag = 1;
                for (k = 1; k < 5 && flag == 1; k++) {
                    if (tempShelf[i + 4 - k][k].isEmpty())
                        flag = 0;
                    else if (tempType != tempShelf[i + 4 - k][k].get().getType()){
                        flag = 0;
                    }
                }
                if(flag==1)
                    contDiag=1;
            }
            i++;
        }

        if(contDiag==1)
            return true;
        else
            return false;
    };

    CommonObj diffCol = (shelf) -> {
        int col,rig;
        Optional<Tile>[][] tempShelf = shelf.getShelf();
        int contCol = 0;

        for(col=0; col<5; col++){
            int flag = 1;
            int contCat = 0;
            int contFrame = 0;
            int contGame = 0;
            int contPlant = 0;
            int contTrophy = 0;
            int contBook = 0;
            for(rig=0; rig<4 && flag==1; rig++){
                if(tempShelf[rig][col].isEmpty())
                    flag = 0;
                else{
                    switch(tempShelf[rig][col].get().getType()){
                        case CAT:
                            contCat++;
                            break;
                        case BOOK:
                            contBook++;
                            break;
                        case GAME:
                            contGame++;
                            break;
                        case FRAME:
                            contFrame++;
                            break;
                        case PLANT:
                            contPlant++;
                            break;
                        case TROPHY:
                            contTrophy++;
                            break;
                        default:
                            break;
                    }
                }
            }
            if(flag==1 && contCat<=1 & contBook<=1 && contFrame<=1 && contGame<=1 &&
               contPlant<=1 && contTrophy<=1)
               contCol++;
        }

        if(contCol>=2)
            return true;
        else
            return false;
    };

    //tested, working
    CommonObj angles = (shelf) -> {
        Optional<Tile>[][] temp = shelf.getShelf();

        if (temp[0][0].isPresent() && temp[HEIGHT-1][0].isPresent() &&
                temp[0][WIDTH-1].isPresent() & temp[HEIGHT-1][WIDTH-1].isPresent()){
            if     (temp[0][0].get().getType().equals(temp[HEIGHT-1][0].get().getType()) &&
                    temp[0][0].get().getType().equals(temp[0][WIDTH-1].get().getType()) &&
                    temp[0][0].get().getType().equals(temp[HEIGHT-1][WIDTH-1].get().getType())){
                return true;
            }
        }
        return false;
    };
}
