import java.util.ArrayList;

import javafx.scene.image.Image;


public class Direct {
		
	ArrayList<String> messages = new ArrayList<>();
	ArrayList<Image> images = new ArrayList<>();
	ArrayList<User> recievers = new ArrayList<User>();
	User sender = new User();	
	
	public void sendImage(Image image){
		images.add(image);
	}
	
	public void sendMessage(String msg){
		messages.add(msg);
	}
	
}
