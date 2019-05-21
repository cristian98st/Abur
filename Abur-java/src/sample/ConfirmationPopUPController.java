package sample;

import com.jfoenix.controls.JFXButton;
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

    public void init(String message){
        lblMessage.setText(message);
    }

    public void cancel(ActionEvent actionEvent) {
        exit();
    }
}
