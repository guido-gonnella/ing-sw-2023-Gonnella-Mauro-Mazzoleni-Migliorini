package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.PrivateObjective;
import it.polimi.ingsw.Model.SerializableOptional;
import it.polimi.ingsw.Model.Space;
import it.polimi.ingsw.Model.Tile;
import java.util.ArrayList;
import java.util.Map;

public interface View {
    void init();
    void asknickname();
    void askselecttile();
    void askinsertcol();
    void boardshow(Space[][] board);
    void shelfshow(SerializableOptional<Tile>[][] shelf);
    void showtilesinhand(ArrayList<Tile> hand);
    void askplayernumber();
    void showpoints(Map<String, Integer> mappoints, Map<String, boolean[]> mapobjective);
    void showpublicobjective(String code);
    void showprivateobjective(PrivateObjective objective);

    void invalidTile(int x, int y);
    void invalidcombo();

    void invalidColumn(int column);
}
