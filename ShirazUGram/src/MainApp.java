import java.io.IOException;




import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private User user;
    
	protected User getUser() {
		return user;
	}

	protected void setUser(User user) {
		this.user = user;
	}

	@Override
	public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ShirazUGram");
        
        initRootLayout();
        
        showLoginOverview();
	}

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the person overview inside the root layout.
     */
    public void showLoginOverview() {
        try {
            // Load fileTrasnferOverview
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("LoginOverview.fxml"));
            AnchorPane LoginOverview = (AnchorPane) loader.load();

            // Set fileTransferOverview into the center of root layout.
            rootLayout.setCenter(LoginOverview);
            
            // Give the controller access to the main app.
            SignInController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showShirazUGram(){
        try {
            // Load fileTrasnferOverview
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("ShirazUGramOverview.fxml"));
            AnchorPane LoginOverview = (AnchorPane) loader.load();

            // Set fileTransferOverview into the center of root layout.
            rootLayout.setCenter(LoginOverview);
            
            // Give the controller access to the main app.
            ShirazUGramController controller = loader.getController();
            controller.setMainApp(this);
            controller.setUser(user);
            controller.setData();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showSignUp() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("SignUp.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
/*            dialogStage.setTitle("Sign Up");
*/            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            SignUpController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
	public static void main(String[] args) {
		launch(args);
	}
}