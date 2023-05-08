package it.polimi.ingsw.FA;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Game;

import java.io.*;

/**
 * Implementation of persistence of the game.<br>
 * Save on a file the state of the game and turn controller.
 *
 * @author Guido Gonnella
 */
public class Persistence {

    public void save(GameController gameController){
        try(FileOutputStream file = new FileOutputStream(new File("save"))){
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(gameController);
        }catch (IOException e){
            //error
        }
    }

    /**
     * Return the game controller saved on the file
     * @return the game controller, null if there's no file saved.
     */
    public GameController load(){
        GameController gameController = null;
        try (FileInputStream file = new FileInputStream(new File("save"))){
            ObjectInputStream in = new ObjectInputStream(file);

            gameController = (GameController) in.readObject();

            return gameController;
        } catch (IOException e) {
            //error
        } catch (ClassNotFoundException e){
            //error class not found
        }

        return null;
    }

}
