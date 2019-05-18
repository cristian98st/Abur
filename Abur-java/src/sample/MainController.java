package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;

import static javafx.application.Platform.exit;


public class MainController {
    @FXML
    private Label lblStatus;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    private static Stage stage = new Stage();

    private HashMap<String, String> users = new HashMap<>();

    public void Login(javafx.event.ActionEvent actionEvent) throws IOException {
        users.put("admin", "admin");
        if (users.containsValue(txtUsername.getText()) && users.get(txtUsername.getText()).equals(txtPassword.getText())) {
            lblStatus.setText("Login Success");

            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            primaryStage.setTitle("Abur");
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/resources/dark-theme.css");
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();
            stage = primaryStage;
            Main.closeStage();
        } else {
            lblStatus.setText("Login Failed");
        }
    }


    public void Cancel() {
        exit();
    }

    public static void closeStage(){
        stage.close();
    }
}
