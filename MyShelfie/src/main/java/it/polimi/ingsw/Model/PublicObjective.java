package it.polimi.ingsw.Model;

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


    CommonObj angles = (shelf) -> {
        Optional<Tile>[][] temp = shelf.getShelf();

        if (temp[0][0].isPresent() && temp[6][0].isPresent() && temp[0][5].isPresent() && temp[6][5].isPresent()) {
            if (temp[0][0].get().getType().equals(temp[6][0].get().getType()) &&
                    temp[6][0].get().getType().equals(temp[0][5].get().getType()) &&
                    temp[0][5].get().getType().equals(temp[6][5].get().getType())) return true;
        }
        return false;
    };

    CommonObj twoSquares = (shelf) -> {
        Optional<Tile>[][] temp = shelf.getShelf();
        boolean[][] check = new boolean[6][5];
        int countSquares = 0;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                check[i][j] = false;
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (temp[i][j].isPresent() && temp[i + 1][j].isPresent() && temp[i][j + 1].isPresent() && temp[i + 1][j + 1].isPresent()) {
                    if (temp[i][j].get().getType().equals(temp[i + 1][j].get().getType()) &&
                            temp[i + 1][j].get().getType().equals(temp[i + 1][j + 1].get().getType()) &&
                            temp[i + 1][j + 1].get().getType().equals(temp[i][j + 1].get().getType()) &&
                            !check[i][j] && !check[i + 1][j] && !check[i][j + 1] && !check[i + 1][j + 1]) {
                        countSquares++;
                        check[i][j] = check[i + 1][j] = check[i][j + 1] = check[i + 1][j + 1] = true;
                    }
                }
            }
        }

        if (countSquares >= 2) return true;
        return false;
    };

    CommonObj stairs = (shelf) -> {
        Optional<Tile>[][] temp = shelf.getShelf();
        int heights[] = new int[5];
        int sorted = 0;
        Arrays.fill(heights, 6);

        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 6; i++) {
                if (temp[i][j].isEmpty()) heights[j] -= 1;
            }
        }

        //check if the heights array is sorted
        if (heights[0] < heights[1]) sorted = 1;
        else if (heights[0] > heights[1]) sorted = -1;
        else sorted = 0;

        if (sorted == 1) {
            for (int i = 1; i < 4 && sorted == 1; i++) {
                if (heights[i] >= heights[i + 1]) sorted = 0;
            }
        } else if (sorted == -1) {
            for (int i = 1; i < 4 && sorted == -1; i++) {
                if (heights[i] <= heights[i + 1]) sorted = 0;
            }
        } else if (sorted == 0) return false;

        if (sorted == 1 || sorted == -1) return true;
        return false;
    };

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
