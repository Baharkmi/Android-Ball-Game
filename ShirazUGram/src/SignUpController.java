import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class SignUpController {

	@FXML
	private TextField name;
	
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private ImageView imageView;
	
    private Stage dialogStage;
    private String imageAddress = "";
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    @FXML
    public void handlePostBrowse() {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Open Picture");
//      fileChooser.getExtensionFilters().addAll(new ExtensionFilter("jpg", "*.jpg"),
//          new ExtensionFilter("All Files", "*.*"));
      File selectedFile = fileChooser.showOpenDialog(dialogStage);
      if (selectedFile != null) {
        imageAddress = "file:///" + selectedFile.getPath();
    	imageView.setImage(new Image(imageAddress));
      }
    }
    
    @FXML
    public void handleSignUp(){
    	
    	Image image = null;
    	if(!imageAddress.equals("")){
        	image = new Image(imageAddress);
    	}

    	
    	if(name.getText().equals("") || username.getText().equals("") || password.getText().equals("")){
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText(null);
    		alert.setContentText("Enter all the information needed!");

    		alert.showAndWait();
    	}
    	
    	else{
    		boolean registered = User.register(name.getText(), username.getText(), password.getText(), image);

        	if(registered){
/*        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Success");
        		alert.setHeaderText(null);
        		alert.setContentText("You have successfully signed up!");

        		alert.showAndWait()*/;
        		
                dialogStage.close();
        	}
        	
        	else{
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error");
        		alert.setHeaderText(null);
        		alert.setContentText("This username already exists!");

        		alert.showAndWait();
        	}
    	}

    }
}
