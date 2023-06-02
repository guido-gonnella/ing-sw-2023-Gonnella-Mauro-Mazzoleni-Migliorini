package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.PrivateObjective;
import it.polimi.ingsw.Model.SerializableOptional;
import it.polimi.ingsw.Model.Space;
import it.polimi.ingsw.Model.Tile;
import java.util.ArrayList;
import java.util.Map;

public interface View {
    void init();
    void askNickname();
    void askSelectTile();
    void askInsertCol();
    void boardShow(Space[][] board);
    void shelfShow(SerializableOptional<Tile>[][] shelf);
    void showTilesInHand(ArrayList<Tile> hand);
    void askplayernumber();
    void showpoints(Map<String, Integer> mappoints, Map<String, boolean[]> mapobjective);
    void showpublicobjective(String code);
    void showprivateobjective(PrivateObjective objective);

    void invalidTile(int x, int y);
    void invalidcombo();

    void invalidColumn(int column);
}
