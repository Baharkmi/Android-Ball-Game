
import java.awt.Button;
import java.io.File;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;


public class ShirazUGramController {

	@FXML
	private ImageView profilePic;
	@FXML
	private Label name;
	@FXML
	private Label posts;
	@FXML
	private Label followers;
	@FXML
	private Label followings;
	@FXML
	private ImageView newPost;
	@FXML
	private Hyperlink ok;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private GridPane gridPane;
	
	private int gridNum = 0;
	
	private MainApp mainApp;
	private User user;
	private Image image;
	
	protected void setUser(User user){
		this.user = user;
	}
	protected User getUser(User user){
		return user;
	}
	
	public void setMainApp(MainApp mainApp) {
		// TODO Auto-generated method stub
		this.mainApp = mainApp;
	}
	
    @FXML
    private void initialize() {
    }
    
    public void setData(){
    	profilePic.setImage(user.getImage());
    	name.setText(user.getName());
    	followers.setText(user.followers.size() + "");
    	followings.setText(user.followings.size() + "");
    	posts.setText(user.posts.size() + "");
    }
    
    @FXML
    public void handlePostBrowse() {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Open Picture");
//      fileChooser.getExtensionFilters().addAll(new ExtensionFilter("jpg", "*.jpg"),
//          new ExtensionFilter("All Files", "*.*"));
      File selectedFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
      if (selectedFile != null) {
        String imageAddress = "file:///" + selectedFile.getPath();
        image = new Image(imageAddress);
    	newPost.setImage(image);
    	ok.setText("Click to post this photo");
      }
    }
    
    @FXML
    public void handePost(){
    	user.postPic(image);
    	setData();
    	
    	gridPane.setGridLinesVisible(true);
        gridPane.setPadding(new Insets(1));
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        
        final ImageView imv = new ImageView();
        imv.setImage(image);
        imv.setFitHeight(100);
        imv.setFitWidth(100);

        final HBox pictureRegion = new HBox();
        
        pictureRegion.getChildren().add(imv);
        gridPane.add(pictureRegion, gridNum, gridNum);
        gridNum++;
/*    	ImageView imageView = new ImageView();
    	imageView.setImage(image);
    	imageView.setX(0.25);
    	imageView.setY(0.25);
    	gridPane.add(imageView, gridNum, gridNum);
    	gridPane.setPrefSize(0.25, 0.25);
    	gridNum = gridNum + 1;*/
    	
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText(null);
		alert.setContentText("You have successfully posted this photo!");

		alert.showAndWait();
    }

}
