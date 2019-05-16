
import java.util.ArrayList;

import javafx.scene.image.Image;

public class User{
	
	private String username;
	private String password;
	private String name;
	private Image image;

	protected String getUsername() {
		return username;
	}

	protected void setUsername(String username) {
		this.username = username;
	}

	protected String getPassword() {
		return password;
	}

	protected void setPassword(String password) {
		this.password = password;
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}
	
	protected Image getImage() {
		return image;
	}

	protected void setImage(Image image) {
		this.image = image;
	}

	public User(String username, String name, String password, Image image){
		this.username = username;
		this.name = name;
		this.password = password;
		this.image = image;
	}
	
	public User(String username, String name, String password){
		this.username = username;
		this.name = name;
		this.password = password;
	}
	
	public User(){
	}

	static ArrayList<User> allUsers = new ArrayList<User>();
	ArrayList<User> followers = new ArrayList<User>();
	ArrayList<User> followings = new ArrayList<User>();
	ArrayList<User> blockList = new ArrayList<User>();
	
	ArrayList<Post> posts = new ArrayList<Post>();
	ArrayList<Direct> directs = new ArrayList<Direct>();
	
	public static boolean register(String name, String username, String password, Image image) {
		
		if(checkUser(username) != null){
			return false;
		}
		
		User u = null;
		if(image != null){
			u = new User(name, username, password, image);
		}
		else{
			u = new User(name, username, password);
		}
		allUsers.add(u);
		return true;
	}
	
	
	
	public static User checkUser(String username){
		
		if(!allUsers.isEmpty()){
			for (User user : allUsers) {
				if(user.getUsername().equals(username)){
					return user;
				}
			}
		}

		return null;
	}
	
	public boolean checkPass(String password){
		
		if(this.password.equals(password)){
			return true;
		}
		return false;
	}
	
	public static User login(String username, String password){
		
		User u = checkUser(username);
		if(u!=null){
			if(u.checkPass(password)){
				return u;
			}
		}
		return null;
	}
	
	public void follow(User u){
		followers.add(u);
		u.followings.add(this);
	}
	
	public void unfollow(User u){
		followers.remove(u);
	}
	
	public void block(User u){
		blockList.add(u);
	}
	
	public User searchUser(String username){
		User user = checkUser(username);
		if(user != null){
			for (User blockedUser : blockList) {
				if(!user.equals(blockedUser)){
					return user;
				}
			}
		}
		return null;
	}
	
	public void postPic(Image image){
		Post post = new Post(image);
		posts.add(post);
	}
	
	public void deletePost(Post post){
		posts.remove(post);
	}
	
	public ArrayList<Post> newsFeed(){
		
		ArrayList<Post> posts = new ArrayList<Post>();
		//Date current = new Date();
		for (User flw : followings) {
			for (Post p : flw.posts){
				posts.add(p);
			}
		}
		return posts;
	}
	
/*	public String notification(){
		return null;
	}
	
	public String activityLog(){
		return null;
	}*/
	
}
