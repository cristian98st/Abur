package sample;

import com.jfoenix.controls.JFXButton;
import database.DBException;
import database.Database;
import database.User;
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
import java.sql.*;
import java.util.HashMap;

import static javafx.application.Platform.exit;


public class LoginController {
    @FXML
    private Label lblStatus;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

    private String username;
    private int id;
    private String pass;
    private String mail;
    private int coins;

    private static Stage stage = new Stage();


    private HashMap<String, String> users = new HashMap<>();

    public void Login(javafx.event.ActionEvent actionEvent) throws Exception {
        users.put("admin", "admin");
        if ((users.containsValue(txtUsername.getText()) && users.get(txtUsername.getText()).equals(txtPassword.getText())) || (checkUserAndPassword(txtUsername.getText(), txtPassword.getText()))) {
            lblStatus.setText("Login Success");

            username = txtUsername.getText();
            this.retrieveByName(username);

            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Main.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Abur");
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/resources/dark-theme.css");

            Main controller = loader.getController();
            controller.init(id, username, pass, mail, coins);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();
            stage = primaryStage;
            Main.closeStage();
        } else {
            lblStatus.setText("Login Failed");
        }
    }


    public boolean checkUserAndPassword(String username, String password) throws SQLException {
        Connection con = Database.getConnection();
        Statement stmt = con.createStatement();
        ResultSet result = stmt.executeQuery("SELECT ID FROM accounts WHERE username ='" + username + "' and pass = '" + password + "'");

        if(result.next())
            return true;
        return false;
    }

    public void Cancel() {
        exit();
    }

    public static void closeStage(){
        stage.close();
    }

    public void openSignUp(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
        primaryStage.setTitle("Abur");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/resources/dark-theme.css");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        stage = primaryStage;
        Main.closeStage();
    }


    private void retrieveByName(String name) throws SQLException, DBException {
        Connection con = Database.getConnection();
        Statement pstmt = con.createStatement();
        ResultSet rez = pstmt.executeQuery("select * from accounts where username = '" + name + "'");
        rez.next();
        id = (rez.getInt(1));
        System.out.println(rez.getInt(1));

        username = (rez.getString(2));
        System.out.println(rez.getString(2));

        pass = (rez.getString(3));
        System.out.println(rez.getString(3));

        mail = (rez.getString(4));
        coins = (rez.getInt(5));
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getPass() {
        return pass;
    }

    public String getMail() {
        return mail;
    }

    public int getCoins() {
        return coins;
    }
}