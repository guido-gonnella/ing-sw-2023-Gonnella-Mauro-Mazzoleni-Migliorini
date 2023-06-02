package it.polimi.ingsw.Model;

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

    private final int HEIGHT = 6;
    private final int WIDTH = 5;

    public PublicObjective(String obj){
        switch (obj) {
            case "cross" -> this.obj = (shelf) -> {
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
            case "eight" -> this.obj = (shelf) -> {
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
            case "diag" -> this.obj = (shelf) -> {
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
            case "diffCol" -> this.obj = (shelf) -> {
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
            case "diffRow" -> this.obj = (shelf) -> {
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
            case "colThreeTypes" -> this.obj = (shelf) -> {
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
                if(contCol>=3)
                    return true;
                else
                    return false;
            };
            case "rowThreeTypes" -> this.obj = (shelf) -> {
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
                if(contRow>=4)
                    return true;
                else
                    return false;
            };
            case "angles" -> this.obj = (shelf) -> {
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
            case "twoSquares" -> this.obj = (shelf) -> {
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
            case "stair" -> this.obj = (shelf) -> {
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
            case "sixCouples" -> this.obj = (shelf) -> {
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
            case "fourQuadruple" -> this.obj = (shelf) -> {
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

    private static void checkAdjacent(SerializableOptional<Tile>[][] matrix, boolean[][] visited, int i, int j, Tile value) {
        visited[i][j] = true;

        if (i > 0 && matrix[i - 1][j].isPresent() && matrix[i - 1][j].get().equals(value) && !visited[i - 1][j]) {
            checkAdjacent(matrix, visited, i - 1, j, value);
        }

        if (i < 6 - 1 && matrix[i - 1][j].isPresent() && matrix[i + 1][j].get().equals(value) && !visited[i + 1][j]) {
            checkAdjacent(matrix, visited, i + 1, j, value);
        }

        if (j > 0 && matrix[i - 1][j].isPresent() && matrix[i][j - 1].get().equals(value) && !visited[i][j - 1]) {
            checkAdjacent(matrix, visited, i, j - 1, value);
        }

        if (j < 5 - 1 && matrix[i - 1][j].isPresent() && matrix[i][j + 1].get().equals(value) && !visited[i][j + 1]) {
            checkAdjacent(matrix, visited, i, j + 1, value);
        }
    }
}

/*
example of lambda

    CommonObj FourFourequals = (shelf) -> {
        Optional<Tile>[][] temp = shelf.getShelf();
        boolean[][] check = new boolean[6][5];
        int count = 0;  //counter for the groups of four adjacent equal tiles

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (!check[i][j] && temp[i][j].isPresent()) {
                    Queue<coord> q = new ArrayDeque<coord>();
                    int nEqualTiles = 1;

                    q.add(new coord(i, j));
                    check[i][j] = true;

                    while (!q.isEmpty() && nEqualTiles < 4) {
                        int x = q.peek().x, y = q.peek().y;
                        q.poll();

                        if ((temp[x + 1][y].isEmpty() || temp[x + 1][j].get().getType().equals(temp[x][y].get().getType())) && !check[x + 1][y] && x + 1 >= 0 && x + 1 < 6) {
                            q.add(new coord(x + 1, y));
                            nEqualTiles++;
                            check[x + 1][j] = true;
                        }
                        if ((temp[x - 1][y].isEmpty() || temp[x - 1][j].get().getType().equals(temp[x][y].get().getType())) && !check[x - 1][y] && x - 1 >= 0 && x - 1 < 6) {
                            q.add(new coord(x - 1, y));
                            nEqualTiles++;
                            check[x - 1][y] = true;
                        }
                        if ((temp[x][y + 1].isEmpty() || temp[x][j + 1].get().getType().equals(temp[x][y].get().getType())) && !check[x][y + 1] && y + 1 >= 0 && y + 1 < 5) {
                            q.add(new coord(x, y + 1));
                            nEqualTiles++;
                            check[x][y + 1] = true;
                        }
                        if ((temp[x][y - 1].isEmpty() || temp[x][j - 1].get().getType().equals(temp[x][y].get().getType())) && !check[x][y - 1] && y - 1 >= 0 && y - 1 < 5) {
                            q.add(new coord(x, y - 1));
                            nEqualTiles++;
                            check[x][y - 1] = true;
                        }
                    }
                    if (nEqualTiles == 4) count++;
                }
            }
        }
        if (count >= 4) return true;
        return false;
    };

    CommonObj SixTwoEqual = (shelf) -> {
        Optional<Tile>[][] temp = shelf.getShelf();
        boolean[][] check = new boolean[6][5];
        int count = 0;  //counter for the groups of four adjacent equal tiles

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (!check[i][j] && temp[i][j].isPresent()) {
                    Queue<coord> q = new ArrayDeque<coord>();
                    int nEqualTiles = 1;

                    q.add(new coord(i, j));
                    check[i][j] = true;

                    while (!q.isEmpty() && nEqualTiles < 2) {
                        int x = q.peek().x, y = q.peek().y;
                        q.poll();

                        if ((temp[x + 1][y].isEmpty() || temp[x + 1][j].get().getType().equals(temp[x][y].get().getType())) && !check[x + 1][y] && x + 1 >= 0 && x + 1 < 6) {
                            q.add(new coord(x + 1, y));
                            nEqualTiles++;
                            check[x + 1][j] = true;
                        }
                        if ((temp[x - 1][y].isEmpty() || temp[x - 1][j].get().getType().equals(temp[x][y].get().getType())) && !check[x - 1][y] && x - 1 >= 0 && x - 1 < 6) {
                            q.add(new coord(x - 1, y));
                            nEqualTiles++;
                            check[x - 1][y] = true;
                        }
                        if ((temp[x][y + 1].isEmpty() || temp[x][j + 1].get().getType().equals(temp[x][y].get().getType())) && !check[x][y + 1] && y + 1 >= 0 && y + 1 < 5) {
                            q.add(new coord(x, y + 1));
                            nEqualTiles++;
                            check[x][y + 1] = true;
                        }
                        if ((temp[x][y - 1].isEmpty() || temp[x][j - 1].get().getType().equals(temp[x][y].get().getType())) && !check[x][y - 1] && y - 1 >= 0 && y - 1 < 5) {
                            q.add(new coord(x, y - 1));
                            nEqualTiles++;
                            check[x][y - 1] = true;
                        }
                    }
                    if (nEqualTiles == 2) count++;
                }
            }
        }
        if (count >= 6) return true;
        return false;
    };

    CommonObj FourRowNotEqual = (shelf) -> {
        Optional<Tile>[][] temp = shelf.getShelf();

        //number of different types in each row
        int[][] nTs = new int[6][6];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                nTs[i][j] = 0;
            }
        }

        int count = 0;

        boolean flag;

        for (int i = 0; i < 6; i++) {
            flag = true;
            for (int j = 0; j < 5 && flag; j++) {
                if (temp[i][j].isEmpty()) {
                    flag = false;
                    Arrays.fill(nTs[i], 0);
                } else {
                    switch (temp[i][j].get().getType()) {
                        case CAT:
                            nTs[j][0]++;
                        case GAME:
                            nTs[j][1]++;
                        case FRAME:
                            nTs[j][2]++;
                        case PLANT:
                            nTs[j][3]++;
                        case TROPHY:
                            nTs[j][4]++;
                        case BOOK:
                            nTs[j][5]++;
                    }
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            int nT = 0;
            for (int j = 0; j < 6; j++) {
                if (nTs[i][j] > 0) nT++;
            }

            if (nT <= 3) count++;
        }

        if (count >= 4) return true;
        else return false;
    };

    CommonObj ThreeColNotEqual = (shelf) -> {
        Optional<Tile>[][] temp = shelf.getShelf();
        int[][] nTs = new int[5][6];
        int count = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                nTs[i][j] = 0;
            }
        }

        boolean flag;
        for (int j = 0; j < 5; j++) {
            flag = true;
            for (int i = 0; i < 6 && flag; i++) {
                if (temp[i][j].isEmpty()) {
                    flag = false;
                    Arrays.fill(nTs[i], 0);
                } else {
                    switch (temp[i][j].get().getType()) {
                        case CAT:
                            nTs[j][0]++;
                        case GAME:
                            nTs[j][1]++;
                        case FRAME:
                            nTs[j][2]++;
                        case PLANT:
                            nTs[j][3]++;
                        case TROPHY:
                            nTs[j][4]++;
                        case BOOK:
                            nTs[j][5]++;
                    }
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            int nT = 0;
            for (int j = 0; j < 6; j++) {
                if (nTs[i][j] > 0) nT++;
            }
            if (nT <= 3) count++;
            {

                if (count >= 3) return true;
            }

        }

        return false;
    };

 */
