package it.polimi.ingsw.View.Gui.SceneControllers;

import it.polimi.ingsw.Enumeration.PubObjType;
import it.polimi.ingsw.Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static it.polimi.ingsw.Observer.ViewObservable.notifyObservers;

public class MainSceneController implements GenericSceneController {
    public Button TileButton_3_0;
    public Button TileButton_4_0;
    public Button TileButton_2_1;
    public Button TileButton_3_1;
    public Button TileButton_4_1;
    public Button TileButton_5_1;
    public Button TileButton_2_2;
    public Button TileButton_3_2;
    public Button TileButton_4_2;
    public Button TileButton_5_2;
    public Button TileButton_6_2;
    public Button TileButton_1_3;
    public Button TileButton_2_3;
    public Button TileButton_3_3;
    public Button TileButton_4_3;
    public Button TileButton_5_3;
    public Button TileButton_6_3;
    public Button TileButton_7_3;
    public Button TileButton_8_3;
    public Button TileButton_0_4;
    public Button TileButton_1_4;
    public Button TileButton_2_4;
    public Button TileButton_3_4;
    public Button TileButton_4_4;
    public Button TileButton_5_4;
    public Button TileButton_7_4;
    public Button TileButton_8_4;
    public Button TileButton_0_5;
    public Button TileButton_1_5;
    public Button TileButton_2_5;
    public Button TileButton_3_5;
    public Button TileButton_4_5;
    public Button TileButton_5_5;
    public Button TileButton_6_5;
    public Button TileButton_7_5;
    public Button TileButton_2_6;
    public Button TileButton_3_6;
    public Button TileButton_4_6;
    public Button TileButton_5_6;
    public Button TileButton_6_6;
    public Button TileButton_3_7;
    public Button TileButton_4_7;
    public Button TileButton_4_8;
    public Button TileButton_5_8;
    public Button TileButton_5_7;
    public Button TileButton_6_4;
    public GridPane Board;
    public Button EndSelectionButton;
    public TextFlow TextBox;
    public GridPane Shelf;
    public ImageView ImageViewShelf_0_0;
    public ImageView ImageViewShelf_1_0;
    public ImageView ImageViewShelf_2_0;
    public ImageView ImageViewShelf_3_0;
    public ImageView ImageViewShelf_4_0;
    public ImageView ImageViewShelf_0_1;
    public ImageView ImageViewShelf_1_1;
    public ImageView ImageViewShelf_2_1;
    public ImageView ImageViewShelf_3_1;
    public ImageView ImageViewShelf_4_1;
    public ImageView ImageViewShelf_0_2;
    public ImageView ImageViewShelf_1_2;
    public ImageView ImageViewShelf_2_2;
    public ImageView ImageViewShelf_3_2;
    public ImageView ImageViewShelf_4_2;
    public ImageView ImageViewShelf_0_3;
    public ImageView ImageViewShelf_1_3;
    public ImageView ImageViewShelf_2_3;
    public ImageView ImageViewShelf_3_3;
    public ImageView ImageViewShelf_4_3;
    public ImageView ImageViewShelf_0_4;
    public ImageView ImageViewShelf_1_4;
    public ImageView ImageViewShelf_2_4;
    public ImageView ImageViewShelf_3_4;
    public ImageView ImageViewShelf_4_4;
    public ImageView ImageViewShelf_0_5;
    public ImageView ImageViewShelf_1_5;
    public ImageView ImageViewShelf_2_5;
    public ImageView ImageViewShelf_3_5;
    public ImageView ImageViewShelf_4_5;
    public Button Col_0;
    public Button Col_1;
    public Button Col_2;
    public Button Col_3;
    public Button Col_4;
    public HBox ColButtons;
    public ImageView PrivateObjective;
    public ImageView PublicObjective_1;
    public ImageView PublicObjective_0;

    @FXML
    public void initialize() {
        Board.setDisable(true);
        EndSelectionButton.setVisible(false);
        ColButtons.setVisible(false);
    }

    public void enableButtons() {
        Board.setDisable(false);
    }

    public void disableButtons() {
        Board.setDisable(true);
        EndSelectionButton.setVisible(false);
    }

    public void setBoard(Space[][] board) {
        ObservableList<Node> childrens = Board.getChildren();

        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[0].length; j++) {
                if(board[i][j].getTile().isPresent()) {
                    for (Node node : childrens) {
                        if(Objects.equals(node.getId(), "TileButton_" + j + "_" + i)) {
                            String tileName = "";
                            switch (board[i][j].getTile().get().getType()) {
                                case BOOK -> tileName = "Libri1." + board[i][j].getTile().get().getId() + ".png";
                                case CAT -> tileName = "Gatti1." + board[i][j].getTile().get().getId() + ".png";
                                case GAME -> tileName = "Giochi1." + board[i][j].getTile().get().getId() + ".png";
                                case FRAME -> tileName = "Cornici1." + board[i][j].getTile().get().getId() + ".png";
                                case PLANT -> tileName = "Piante1." + board[i][j].getTile().get().getId() + ".png";
                                case TROPHY -> tileName = "Trofei1." + board[i][j].getTile().get().getId() + ".png";
                            }
                            InputStream input = Objects.requireNonNull(getClass().getResourceAsStream("/item tiles/" + tileName));
                            Image image = new Image(input);
                            ImageView imageView = new ImageView(image);
                            imageView.setPreserveRatio(true);
                            imageView.setFitHeight(105);
                            ((Button) node).setPadding(new Insets(-1, -1, -1, -1));
                            ((Button) node).setGraphic(imageView);
                            node.setVisible(true);
                            break;
                        }
                    }
                }
                else {
                    for (Node node : childrens) {
                        if(Objects.equals(node.getId(), "TileButton_" + j + "_" + i)) {
                            node.setVisible(false);
                            break;
                        }
                    }
                }
            }
        }
    }

    @FXML
    private void tileSelect(ActionEvent event) {
        EndSelectionButton.setVisible(true);
        String[] tileCoords = (((Button) event.getSource()).getId()).split("_");
        new Thread(() -> notifyObservers(obs -> obs.onSelectTile(Integer.parseInt(tileCoords[2].trim()), Integer.parseInt(tileCoords[1].trim())))).start();
    }

    @FXML
    private void endSelection() {
        new Thread(() -> notifyObservers(obs -> obs.onSelectTile(-1,-1))).start();
    }

    public void setText(String text) {
        TextBox.getChildren().add(new Text(text));
    }

    public void setShelf(SerializableOptional<Tile>[][] shelf) {
        ObservableList<Node> childrens = Shelf.getChildren();

        for(int i=0; i<shelf.length; i++) {
            for(int j=0; j<shelf[0].length; j++) {
                if(shelf[i][j].isPresent()) {
                    for (Node node : childrens) {
                        if(Objects.equals(node.getId(), "ImageViewShelf_" + j + "_" + i)) {
                            String tileName = "";
                            switch (shelf[i][j].get().getType()) {
                                case BOOK -> tileName = "Libri1." + shelf[i][j].get().getId() + ".png";
                                case CAT -> tileName = "Gatti1." + shelf[i][j].get().getId() + ".png";
                                case GAME -> tileName = "Giochi1." + shelf[i][j].get().getId() + ".png";
                                case FRAME -> tileName = "Cornici1." + shelf[i][j].get().getId() + ".png";
                                case PLANT -> tileName = "Piante1." + shelf[i][j].get().getId() + ".png";
                                case TROPHY -> tileName = "Trofei1." + shelf[i][j].get().getId() + ".png";
                            }
                            InputStream input = Objects.requireNonNull(getClass().getResourceAsStream("/item tiles/" + tileName));
                            Image image = new Image(input);
                            ((ImageView) node).setImage(image);
                            node.setVisible(true);
                            break;
                        }
                    }
                }
                else {
                    for (Node node : childrens) {
                        if(Objects.equals(node.getId(), "ImageViewShelf_" + j + "_" + i)) {
                            node.setVisible(false);
                            break;
                        }
                    }
                }
            }
        }
    }

    @FXML
    private void colSelection(ActionEvent event) {
        String[] col = (((Button) event.getSource()).getId()).split("_");
        new Thread(() -> notifyObservers(obs -> obs.onSelectCol(Integer.parseInt(col[1].trim())))).start();
    }

    public void enableColSelection() {
        ColButtons.setVisible(true);
    }

    public void disableColSelection() {
        ColButtons.setVisible(false);
        TextBox.getChildren().clear();
    }

    public void setPublicObjectives(PubObjType code) {
        String objNum = "";
        switch (code) {
            case DIAG -> objNum = "11.jpg";
            case CROSS -> objNum = "10.jpg";
            case EIGHT -> objNum = "9.jpg";
            case STAIR -> objNum = "12.jpg";
            case ANGLES -> objNum = "8.jpg";
            case DIFF_COL -> objNum = "2.jpg";
            case DIFF_ROW -> objNum = "6.jpg";
            case SIX_COUPLES -> objNum = "4.jpg";
            case TWO_SQUARES -> objNum = "1.jpg";
            case COL_THREE_TYPES -> objNum = "5.jpg";
            case FOUR_QUADRUPLES -> objNum = "3.jpg";
            case ROW_THREE_TYPES -> objNum = "7.jpg";
        }
        InputStream input = Objects.requireNonNull(getClass().getResourceAsStream("/common goal cards/" + objNum));
        Image image = new Image(input);
        if(PublicObjective_0.getImage()==null) PublicObjective_0.setImage(image);
        else PublicObjective_1.setImage(image);
    }

    public void setPrivateObjective(PrivateObjective objective) {
        InputStream input;
        if(objective.getId()==1) input = Objects.requireNonNull(getClass().getResourceAsStream("/personal goal cards/Personal_Goals.png"));
        else input = Objects.requireNonNull(getClass().getResourceAsStream("/personal goal cards/Personal_Goals" + objective.getId() + ".png"));
        Image image = new Image(input);
        PrivateObjective.setImage(image);
    }

    public void setFinalPoints(Map<String, Integer> mapPoints, Map<String, boolean[]> mapObjective) {
        boolean flag;
        for (String player: mapPoints.keySet())
        {
            flag= false;
            TextBox.getChildren().add(new Text("\n" + player + " scored: "));
            TextBox.getChildren().add(new Text(mapPoints.get(player) + " points!\n"));
            TextBox.getChildren().add(new Text("and completed: "));
            if (mapObjective.get(player)[0]){
                flag=true;
                TextBox.getChildren().add(new Text("the first objective ✔"));
            }
            if (flag) {
                if (mapObjective.get(player)[1]) {
                    TextBox.getChildren().add(new Text(" and the second objective ✔"));
                }
            } else if (mapObjective.get(player)[1]) {
                TextBox.getChildren().add(new Text("the second objective ✔"));
            } else {
                TextBox.getChildren().add(new Text("no objectives (╥﹏╥)"));
            }
            TextBox.getChildren().add(new Text("\n"));
        }
    }
}