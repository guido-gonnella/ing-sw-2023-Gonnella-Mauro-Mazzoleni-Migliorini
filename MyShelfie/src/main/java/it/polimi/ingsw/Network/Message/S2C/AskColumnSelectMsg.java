package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

public class AskColumnSelectMsg extends Message {

    /*TODO rimuovere qui e in tutti i messaggi lo username, per adesso l'ho lasciato per
    *  non far dare errore e non l'ho rimosso perché ho paura di rompere qualcosa, se mi date
    *  l'ok posso farlo io (pier)*/
    public AskColumnSelectMsg(String u){super(MsgType.SELECT_COL_REQUEST);}
}
