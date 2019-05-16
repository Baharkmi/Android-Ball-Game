import java.util.Date;


public class Comment {

	private User commenter;
	private Date date = new Date();
	
	protected User getCommenter() {
		return commenter;
	}

	protected void setCommenter(User commenter) {
		this.commenter = commenter;
	}

	protected Date getDate() {
		return date;
	}

	protected void setDate(Date date) {
		this.date = date;
	}

	public void comments(){
	}
	
	public Comment(User commenter){
		this.commenter = commenter;
	}
}
