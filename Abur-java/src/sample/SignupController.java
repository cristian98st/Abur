package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static javafx.application.Platform.exit;

public class SignupController {

    private static Stage stage = new Stage();

    public void Signup(ActionEvent actionEvent) throws IOException {
        openLogin();
    }

    public void Back(ActionEvent actionEvent) throws IOException {
        openLogin();
    }

    public void Cancel(ActionEvent actionEvent) {
        exit();
    }

    public void closeStage(){
        stage.close();
    }

    public void openLogin() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Abur");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/resources/dark-theme.css");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        stage = primaryStage;
        LoginController.closeStage();
    }

}
