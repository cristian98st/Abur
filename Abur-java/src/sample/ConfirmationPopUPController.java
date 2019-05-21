package sample;

import com.jfoenix.controls.JFXButton;
import database.Game;
import database.Item;
import database.marketItem;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static javafx.application.Platform.exit;

public class ConfirmationPopUPController {

    @FXML
    Label lblMessage;
    @FXML
    JFXButton btnConfirm, X;

    private String clasa;

    public void init(String message, String parentClass){
        clasa = parentClass;
        lblMessage.setText(message);
    }

    public void cancel(ActionEvent actionEvent) {
        if(clasa.equals("game"))
            Game.cancelConfirmation();
        else if(clasa.equals("item"))
            Item.cancelConfirmation();
        else if(clasa.equals("marketItem"))
            marketItem.cancelConfirmation();
    }
}
