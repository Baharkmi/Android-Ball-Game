import java.util.ArrayList;
import java.util.Date;

import javafx.scene.image.Image;


public class Post {

	private Image image;
	private Date date = new Date();
	private int likes = 0;
	
	ArrayList<Post> allPosts = new ArrayList<>();
	ArrayList<Comment> comments = new ArrayList<>();
	ArrayList<User> likers = new ArrayList<>();
	ArrayList<User> commenters = new ArrayList<>();
	
	protected Image getImage() {
		return image;
	}

	protected void setImage(Image image) {
		this.image = image;
	}

	protected Date getDate() {
		return date;
	}

	protected void setDate(Date date) {
		this.date = date;
	}

	public Post(Image image){
		this.image = image;
	}
	
	public Post(){
	}
	
	public void like(User liker){
		likes = likes + 1;
		likers.add(liker);
	}
	
	public void comment(User commenter){
		Comment c = new Comment(commenter);
		commenters.add(commenter);
		comments.add(c);
	}
/*	int date;
	DateType type;	*/
		
	/* this method changes the date when time passes,
	 * after 24 hours changes the dateType to hour
	 * and after 7 days changes the dateType to week
	 */
/*	public void changeDate(){
	}*/
}
