package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Enumeration.Type;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

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

    //tesed, working
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

        if(contCat>=8 || contBook>=8 || contGame>=8 ||
           contFrame>=8 || contPlant>=8 || contTrophy>=8)
            return true;
        else
            return false;
    };

    //tested, working
    CommonObj diag = (shelf) -> {
        int i=0;
        int contDiag = 0;
        int k=0;
        Optional<Tile>[][] tempShelf = shelf.getShelf();

        while(i<2 && contDiag==0){
            if (tempShelf[i][0].isPresent()){
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

    //tested, working
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
            for(rig=0; rig<6 && flag==1; rig++){
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
            if(flag==1 && contCat<=1 && contBook<=1 && contFrame<=1 &&
                    contGame<=1 && contPlant<=1 && contTrophy<=1)
                contCol++;
        }

        if(contCol>=2)
            return true;
        else
            return false;
    };

    //tested, working
    CommonObj diffRow = (shelf) -> {
        int col,rig;
        Optional<Tile>[][] tempShelf = shelf.getShelf();
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

    //tested, working
    CommonObj colThreeTypes = (shelf) -> {
        int col,rig;
        int flag;
        Optional<Tile>[][] tempShelf = shelf.getShelf();
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

    //tested working
    CommonObj rowThreeTypes = (shelf) -> {
        int col,rig;
        int flag;
        Optional<Tile>[][] tempShelf = shelf.getShelf();
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

    //tested, working
    CommonObj twoSquares = (shelf) -> {
        Optional<Tile>[][] temp = shelf.getShelf();
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

    //tested, working
    CommonObj stair = (shelf) -> {
        Optional<Tile>[][] temp = shelf.getShelf();
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

    //non funziona
    CommonObj SixTwoEqual = (shelf) -> {
        Optional<Tile>[][] temp = shelf.getShelf();
        boolean[][] check = new boolean[6][5];
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                check[i][j] = false;
            }
        }
        int count = 0;  //counter for the groups of four adjacent equal tiles

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (!check[i][j] && temp[i][j].isPresent()) {
                    Queue<Coords> q = new ArrayDeque<Coords>();
                    int nEqualTiles = 1;

                    q.add(new Coords(i, j));
                    check[i][j] = true;

                    while (!q.isEmpty() && nEqualTiles < 2) {
                        int x = q.peek().x, y = q.peek().y;
                        q.poll();

                        if ((temp[x + 1][y].isEmpty() || temp[x + 1][y].get().getType().equals(temp[x][y].get().getType())) && !check[x + 1][y] && x + 1 >= 0 && x + 1 < 6) {
                            q.add(new Coords(x + 1, y));
                            nEqualTiles++;
                            check[x + 1][j] = true;
                        }
                        if ((temp[x - 1][y].isEmpty() || temp[x - 1][y].get().getType().equals(temp[x][y].get().getType())) && !check[x - 1][y] && x - 1 >= 0 && x - 1 < 6) {
                            q.add(new Coords(x - 1, y));
                            nEqualTiles++;
                            check[x - 1][y] = true;
                        }
                        if ((temp[x][y + 1].isEmpty() || temp[x][y + 1].get().getType().equals(temp[x][y].get().getType())) && !check[x][y + 1] && y + 1 >= 0 && y + 1 < 5) {
                            q.add(new Coords(x, y + 1));
                            nEqualTiles++;
                            check[x][y + 1] = true;
                        }
                        if ((temp[x][y - 1].isEmpty() || temp[x][y - 1].get().getType().equals(temp[x][y].get().getType())) && !check[x][y - 1] && y - 1 >= 0 && y - 1 < 5) {
                            q.add(new Coords(x, y - 1));
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

    //non funziona
    CommonObj checkClustersFour = (shelf) -> {
    Optional<Tile>[][] temp = shelf.getShelf();
    int count = 0;
    boolean[][] visited = new boolean[6][5];

    for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 5; j++) {
            if (!visited[i][j] && temp[i][j].isPresent()) {
                Tile value = temp[i][j].get();
                visited[i][j] = true;

                boolean top = false;
                boolean bottom = false;
                boolean left = false;
                boolean right = false;
                if(temp[i-1][j].isPresent())
                    top = (i > 0 && temp[i-1][j].get().equals(value) && !visited[i-1][j]);
                if(temp[i+1][j].isPresent())
                    bottom = (i < 6-1 && temp[i+1][j].get().equals(value) && !visited[i+1][j]);
                if(temp[i][j-1].isPresent())
                    left = (j > 0 && temp[i][j-1].get().equals(value) && !visited[i][j-1]);
                if(temp[i][j+1].isPresent())
                    right = (j < 5-1 && temp[i][j+1].get().equals(value) && !visited[i][j+1]);

                if (top || bottom || left || right) {
                    count++;
                    checkAdjacent(temp, visited, i, j, value);
                }
            }
        }
    }

    return count >= 4;
};

private static void checkAdjacent(Optional<Tile>[][] matrix, boolean[][] visited, int i, int j, Tile value) {
    visited[i][j] = true;

    if (i > 0 && matrix[i-1][j].isPresent() && matrix[i-1][j].get().equals(value) && !visited[i-1][j]) {
        checkAdjacent(matrix, visited, i-1, j, value);
    }

    if (i < 6-1 && matrix[i-1][j].isPresent() && matrix[i+1][j].get().equals(value) && !visited[i+1][j]) {
        checkAdjacent(matrix, visited, i+1, j, value);
    }

    if (j > 0 && matrix[i-1][j].isPresent() && matrix[i][j-1].get().equals(value) && !visited[i][j-1]) {
        checkAdjacent(matrix, visited, i, j-1, value);
    }

    if (j < 5-1 && matrix[i-1][j].isPresent() && matrix[i][j+1].get().equals(value) && !visited[i][j+1]) {
        checkAdjacent(matrix, visited, i, j+1, value);
    }
}

}
