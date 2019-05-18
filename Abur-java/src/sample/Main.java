package sample;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static javafx.application.Platform.exit;

public class Main extends Application {

    @FXML
    private AnchorPane panelMyAccount, panelMyGames, panelMyItems, panelSettings;
    @FXML
    private JFXButton btnMyAccount, btnMyGames, btnMyItems, btnSettings, btnLogout;

    static Stage stage = new Stage();

    public static void closeStage() {
        stage.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/resources/dark-theme.css");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        stage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void Cancel(ActionEvent actionEvent) {
        exit();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        makePanelInvisible(panelMyAccount, panelMyGames, panelMyItems, panelSettings);
        if (event.getSource() == btnMyAccount) {
            panelMyAccount.setVisible(true);
        } else if (event.getSource() == btnMyGames) {
            panelMyGames.setVisible(true);
        } else if (event.getSource() == btnMyItems) {
            panelMyItems.setVisible(true);
        } else if (event.getSource() == btnSettings) {
            panelSettings.setVisible(true);
        } else if (event.getSource() == btnLogout) {
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            primaryStage.setTitle("Login");
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/resources/dark-theme.css");
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();
            MainController.closeStage();
        }
    }


    private void makePanelInvisible(AnchorPane... panels) {
        for (AnchorPane panel : panels) {
            panel.setVisible(false);
        }
    }
}
