import java.awt.Button;

import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;


public class SignInController {

	@FXML
	TextField username;
	
	@FXML
	PasswordField password;
	
	@FXML
	Button SignIn;
	
	@FXML
	Hyperlink SignUp;

	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
		// TODO Auto-generated method stub
        this.mainApp = mainApp;
	}
	
	@FXML
	public void handleSignUp(){
		mainApp.showSignUp();
	}
	
	@FXML
	public void handleSignIn(){
		User user = User.login(username.getText(), password.getText());
		
		if(user != null ){	        
	        mainApp.setUser(user);
	        mainApp.showShirazUGram();
		}
		else{
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("Error");
	        alert.setHeaderText(null);
	        alert.setContentText("Username or password incorrect!");

	        alert.showAndWait();
		}
	}
}
