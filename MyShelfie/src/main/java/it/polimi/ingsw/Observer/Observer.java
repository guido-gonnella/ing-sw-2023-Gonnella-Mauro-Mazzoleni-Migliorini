package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Network.Message.Message;

public interface Observer {
    public void update(Message msg);
}
