package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Space;
import it.polimi.ingsw.Model.Tile;

import java.util.ArrayList;
import java.util.Optional;

public interface View {
    void init();
    void asknickname();
    void askselecttile();
    void askswap();
    void askinsert();
    void boardshow(Space[][] board);
    void shelfshow(Optional<Tile>[][] shelf);
    void showtilesinhand(ArrayList<Tile> hand);
    void askplayernumber();
}
