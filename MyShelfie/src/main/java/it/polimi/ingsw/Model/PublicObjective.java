package it.polimi.ingsw.Model;

import it.polimi.ingsw.Enumeration.PubObjType;
import it.polimi.ingsw.Enumeration.Type;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that contains a common objective and its realization
 *
 * @author Pierantonio Mauro
 */
public class PublicObjective implements Serializable {
    private final CommonObj obj;
    private final PubObjType type;

    private final int HEIGHT = 6;
    private final int WIDTH = 5;

    /**
     * Constructor for the class. Based on the parameter, the obj attribute is set as one of the lambdas specified in the method.
     * @param obj the {@link PubObjType type} of the public objective
     */
    public PublicObjective(PubObjType obj){
        type = obj;
        switch (obj) {
            case CROSS -> this.obj = (shelf) -> {
                int i, j;
                int contCross = 0;
                SerializableOptional<Tile>[][] tempShelf = shelf.getShelf();

                for (i = 1; i < 5 && contCross == 0; i++) {
                    for (j = 1; j < 4 && contCross == 0; j++) {
                        if (tempShelf[i][j].isPresent()) {
                            Type tempType = tempShelf[i][j].get().getType();
                            if (tempShelf[i - 1][j - 1].isPresent() &&
                                    tempShelf[i + 1][j + 1].isPresent() &&
                                    tempShelf[i + 1][j - 1].isPresent() &&
                                    tempShelf[i - 1][j + 1].isPresent()) {
                                if (tempType.equals(tempShelf[i - 1][j - 1].get().getType()) &&
                                        tempType.equals(tempShelf[i + 1][j + 1].get().getType()) &&
                                        tempType.equals(tempShelf[i + 1][j - 1].get().getType()) &&
                                        tempType.equals(tempShelf[i - 1][j + 1].get().getType())) {
                                    contCross = 1;
                                }
                            }
                        }
                    }
                }
                if (contCross == 0)
                    return false;
                else
                    return true;
            };
            case EIGHT -> this.obj = (shelf) -> {
                int i, j;
                int contCat = 0;
                int contFrame = 0;
                int contGame = 0;
                int contPlant = 0;
                int contTrophy = 0;
                int contBook = 0;
                SerializableOptional<Tile>[][] tempShelf = shelf.getShelf();

                for (i = 0; i < 6; i++) {
                    for (j = 0; j < 5; j++) {
                        if (tempShelf[i][j].isPresent()) {
                            switch (tempShelf[i][j].get().getType()) {
                                case CAT -> contCat++;
                                case BOOK -> contBook++;
                                case GAME -> contGame++;
                                case FRAME -> contFrame++;
                                case PLANT -> contPlant++;
                                case TROPHY -> contTrophy++;
                                default -> {
                                }
                            }
                        }
                    }
                }

                if (contCat >= 8 || contBook >= 8 || contGame >= 8 ||
                        contFrame >= 8 || contPlant >= 8 || contTrophy >= 8)
                    return true;
                else
                    return false;
            };
            case DIAG -> this.obj = (shelf) -> {
                int i = 0;
                int contDiag = 0;
                int k = 0;
                SerializableOptional<Tile>[][] tempShelf = shelf.getShelf();

                while (i < 2 && contDiag == 0) {
                    if (tempShelf[i][0].isPresent()) {
                        Type tempType = tempShelf[i][0].get().getType();
                        int flag = 1;
                        for (k = 1; k < 5 && flag == 1; k++) {
                            if (tempShelf[i + k][k].isEmpty())
                                flag = 0;
                            else if (tempType != tempShelf[i + k][k].get().getType()) {
                                flag = 0;
                            }
                        }
                        if (flag == 1)
                            contDiag = 1;
                    }
                    i++;
                }

                i = 0;
                while (i < 2 && contDiag == 0) {
                    if (tempShelf[i + 4][0].isPresent()) {
                        Type tempType = tempShelf[i + 4][0].get().getType();
                        int flag = 1;
                        for (k = 1; k < 5 && flag == 1; k++) {
                            if (tempShelf[i + 4 - k][k].isEmpty())
                                flag = 0;
                            else if (tempType != tempShelf[i + 4 - k][k].get().getType()) {
                                flag = 0;
                            }
                        }
                        if (flag == 1)
                            contDiag = 1;
                    }
                    i++;
                }

                if (contDiag == 1)
                    return true;
                else
                    return false;
            };
            case DIFF_COL -> this.obj = (shelf) -> {
                int col, rig;
                SerializableOptional<Tile>[][] tempShelf = shelf.getShelf();
                int contCol = 0;

                for (col = 0; col < 5; col++) {
                    int flag = 1;
                    int contCat = 0;
                    int contFrame = 0;
                    int contGame = 0;
                    int contPlant = 0;
                    int contTrophy = 0;
                    int contBook = 0;
                    for (rig = 0; rig < 6 && flag == 1; rig++) {
                        if (tempShelf[rig][col].isEmpty())
                            flag = 0;
                        else {
                            switch (tempShelf[rig][col].get().getType()) {
                                case CAT -> contCat++;
                                case BOOK -> contBook++;
                                case GAME -> contGame++;
                                case FRAME -> contFrame++;
                                case PLANT -> contPlant++;
                                case TROPHY -> contTrophy++;
                                default -> {
                                }
                            }
                        }
                    }
                    if (flag == 1 && contCat <= 1 && contBook <= 1 && contFrame <= 1 &&
                            contGame <= 1 && contPlant <= 1 && contTrophy <= 1)
                        contCol++;
                }

                if (contCol >= 2)
                    return true;
                else
                    return false;
            };
            case DIFF_ROW -> this.obj = (shelf) -> {
                int col,rig;
                SerializableOptional<Tile>[][] tempShelf = shelf.getShelf();
                int contRow = 0;

                for(rig=0; rig<6; rig++){
                    int flag = 1;
                    int contCat = 0;
                    int contFrame = 0;
                    int contGame = 0;
                    int contPlant = 0;
                    int contTrophy = 0;
                    int contBook = 0;
                    for(col=0; col<5 && flag==1; col++){
                        if(tempShelf[rig][col].isEmpty())
                            flag = 0;
                        else{
                            switch (tempShelf[rig][col].get().getType()) {
                                case CAT -> contCat++;
                                case BOOK -> contBook++;
                                case GAME -> contGame++;
                                case FRAME -> contFrame++;
                                case PLANT -> contPlant++;
                                case TROPHY -> contTrophy++;
                                default -> {
                                }
                            }
                        }
                    }
                    if(flag==1 && contCat<=1 && contBook<=1 && contFrame<=1
                            && contGame<=1 && contPlant<=1 && contTrophy<=1)
                        contRow++;
                }

                if(contRow>=2)
                    return true;
                else
                    return false;
            };
            case COL_THREE_TYPES -> this.obj = (shelf) -> {
                int col,rig;
                int flag;
                SerializableOptional<Tile>[][] tempShelf = shelf.getShelf();
                int contCol = 0;
                int[] contType = new int[6];
                int contDiffTypes;

                for(col=0; col<5; col++){
                    flag = 1;
                    contDiffTypes = 0;
                    for(int i=0; i<6; i++)
                        contType[i] = 0;

                    for(rig=0; rig<6 && flag==1; rig++){
                        if(tempShelf[rig][col].isEmpty())
                            flag = 0;
                        else{
                            switch (tempShelf[rig][col].get().getType()) {
                                case CAT -> contType[0]++;
                                case BOOK -> contType[1]++;
                                case GAME -> contType[2]++;
                                case FRAME -> contType[3]++;
                                case PLANT -> contType[4]++;
                                case TROPHY -> contType[5]++;
                                default -> {
                                }
                            }
                        }
                    }
                    if(flag == 1){
                        for(int i=0; i<6; i++){
                            if(contType[i]>0)
                                contDiffTypes++;
                        }
                        if(contDiffTypes > 0 && contDiffTypes <= 3)
                            contCol++;

                    }
                }
                return contCol >= 3;
            };
            case ROW_THREE_TYPES -> this.obj = (shelf) -> {
                int col,rig;
                int flag;
                SerializableOptional<Tile>[][] tempShelf = shelf.getShelf();
                int contRow = 0;
                int[] contType = new int[6];
                int contDiffTypes;

                for(rig=0; rig<6; rig++){
                    flag = 1;
                    contDiffTypes = 0;
                    for(int i=0; i<6; i++)
                        contType[i] = 0;

                    for(col=0; col<5 && flag==1; col++){
                        if(tempShelf[rig][col].isEmpty())
                            flag = 0;
                        else{
                            switch (tempShelf[rig][col].get().getType()) {
                                case CAT -> contType[0]++;
                                case BOOK -> contType[1]++;
                                case GAME -> contType[2]++;
                                case FRAME -> contType[3]++;
                                case PLANT -> contType[4]++;
                                case TROPHY -> contType[5]++;
                                default -> {
                                }
                            }
                        }
                    }
                    if(flag == 1){
                        for(int i=0; i<6; i++){
                            if(contType[i]>0)
                                contDiffTypes++;
                        }
                        if(contDiffTypes > 0 && contDiffTypes <= 3)
                            contRow++;

                    }
                }
                return contRow >= 4;
            };
            case ANGLES -> this.obj = (shelf) -> {
                SerializableOptional<Tile>[][] temp = shelf.getShelf();

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
            case TWO_SQUARES -> this.obj = (shelf) -> {
                SerializableOptional<Tile>[][] temp = shelf.getShelf();
                boolean[][] check = new boolean[6][5];
                int countSquaresCat = 0;
                int countSquaresPlant = 0;
                int countSquaresTrophy = 0;
                int countSquaresFrame = 0;
                int countSquaresGame = 0;
                int countSquaresBook = 0;

                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 5; j++) {
                        check[i][j] = false;
                    }
                }

                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (temp[i][j].isPresent() && temp[i+1][j].isPresent() &&
                                temp[i][j+1].isPresent() && temp[i+1][j+1].isPresent()) {
                            if (temp[i][j].get().getType().equals(temp[i+1][j].get().getType()) &&
                                    temp[i][j].get().getType().equals(temp[i+1][j+1].get().getType()) &&
                                    temp[i][j].get().getType().equals(temp[i][j+1].get().getType()) &&
                                    !check[i][j] && !check[i+1][j] && !check[i][j+1] && !check[i+1][j+1]) {
                                switch (temp[i][j].get().getType()) {
                                    case CAT -> countSquaresCat++;
                                    case BOOK -> countSquaresBook++;
                                    case GAME -> countSquaresGame++;
                                    case FRAME -> countSquaresFrame++;
                                    case PLANT -> countSquaresPlant++;
                                    case TROPHY -> countSquaresTrophy++;
                                    default -> {
                                    }
                                }
                                check[i][j] = true;
                                check[i+1][j] = true;
                                check[i][j+1] = true;
                                check[i+1][j+1] = true;
                            }
                        }
                    }
                }

                if (countSquaresCat >= 2 || countSquaresBook >= 2 || countSquaresFrame >= 2 ||
                        countSquaresPlant >= 2 || countSquaresGame >= 2 || countSquaresTrophy >= 2) return true;
                return false;
            };
            case STAIR -> this.obj = (shelf) -> {
                SerializableOptional<Tile>[][] temp = shelf.getShelf();
                int heights[] = new int[5];

                for(int i=0; i<5; i++){
                    heights[i] = 0;
                    for(int j=5; j>=0; j--){
                        if(temp[j][i].isPresent())
                            heights[i]++;
                    }
                }

                boolean stairLR = true;
                boolean stairRL = true;
                for(int i=0; i<4; i++){
                    if(heights[0] >= 1 && heights[4] >= 1) {
                        if(heights[i] + 1 != heights[i+1])
                            stairLR = false;
                        if(heights[i] != heights[i+1] + 1)
                            stairRL = false;
                    }
                    else
                        return false;
                }

                return stairRL || stairLR;
            };
            case SIX_COUPLES -> this.obj = (shelf) -> {
                int[][] gruppi = new int[6][5];
                for(int i = 0; i < 6; i++) {
                    for(int j = 0; j < 5; j++) {
                        gruppi[i][j] = -1;
                    }
                }
                int couples = 0;
                ArrayList<Integer> count = new ArrayList<Integer>();

                for(int i = 0; i < 6; i++) {
                    for(int j = 0; j < 5; j++) {
                        if(shelf.getShelf()[i][j].isPresent() && gruppi[i][j]==-1) {
                            if(j-1>=0 && shelf.getShelf()[i][j-1].isPresent() && shelf.getShelf()[i][j-1].get().getType().equals(shelf.getShelf()[i][j].get().getType())) {
                                gruppi[i][j]=gruppi[i][j-1];
                                count.set(gruppi[i][j], count.get(gruppi[i][j])+1);
                            }
                            else if(i-1>=0 && shelf.getShelf()[i-1][j].isPresent() && shelf.getShelf()[i-1][j].get().getType().equals(shelf.getShelf()[i][j].get().getType())) {
                                gruppi[i][j]=gruppi[i-1][j];
                                count.set(gruppi[i][j], count.get(gruppi[i][j])+1);
                            }
                            else {
                                gruppi[i][j]= count.size();
                                count.add(1);
                            }
                        }
                    }
                }

                for (Integer integer : count) {
                    if (integer >= 2) {
                        couples++;
                    }
                }

                return couples>=6;
            };
            case FOUR_QUADRUPLES -> this.obj = (shelf) -> {
                int[][] gruppi = new int[6][5];
                for(int i = 0; i < 6; i++) {
                    for(int j = 0; j < 5; j++) {
                        gruppi[i][j] = -1;
                    }
                }
                int quadruples = 0;
                ArrayList<Integer> count = new ArrayList<Integer>();

                for(int i = 0; i < 6; i++) {
                    for(int j = 0; j < 5; j++) {
                        if(shelf.getShelf()[i][j].isPresent() && gruppi[i][j]==-1) {
                            if(j-1>=0 && shelf.getShelf()[i][j-1].isPresent() && shelf.getShelf()[i][j-1].get().getType().equals(shelf.getShelf()[i][j].get().getType())) {
                                gruppi[i][j]=gruppi[i][j-1];
                                count.set(gruppi[i][j], count.get(gruppi[i][j])+1);
                            }
                            else if(i-1>=0 && shelf.getShelf()[i-1][j].isPresent() && shelf.getShelf()[i-1][j].get().getType().equals(shelf.getShelf()[i][j].get().getType())) {
                                gruppi[i][j]=gruppi[i-1][j];
                                count.set(gruppi[i][j], count.get(gruppi[i][j])+1);
                            }
                            else {
                                gruppi[i][j]= count.size();
                                count.add(1);
                            }
                        }
                    }
                }

                for (Integer integer : count) {
                    if (integer >= 4) {
                        quadruples++;
                    }
                }

                return quadruples>=4;
            };
            default -> this.obj = null;
        }
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

    /**
     * Getter for the type of the objective.
     * @return the type of the objective.
     */
    public PubObjType getObjectiveType(){return type;}

}
