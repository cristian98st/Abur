package sample;

import static javafx.application.Platform.exit;

import java.io.IOException;
import java.sql.SQLException;

import database.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignupController {
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtPassword;
	@FXML
	private TextField ConfirmPassword;
	@FXML
	private TextField txtEmail;
	
    private static Stage stage = new Stage();
    
    public void Signup(ActionEvent actionEvent) throws IOException {
    	if(this.txtPassword.getText().compareTo(this.ConfirmPassword.getText())==0) {
    		User newAcc = new User(this.txtUsername.getText(),this.txtPassword.getText(),this.txtEmail.getText(),200);
    		try {
				newAcc.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
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
