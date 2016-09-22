import java.util.List;

public class Video {
	
	private List<String> response;
	private int vid;
	private int owner_id;
	private String title;
	private int date;
	private int views;
	private String image;
	private String imageMedium;
	private int comments;
	String player;

	public int getVid() {
		return vid;
	}
	public void setVid(int vid) {
		this.vid = vid;
	}
	public int getOwner_id() {
		return owner_id;
	}
	public void setOwner_Id(int ownerId) {
		this.owner_id = ownerId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getImageMedium() {
		return imageMedium;
	}
	public void setImageMedium(String imageMedium) {
		this.imageMedium = imageMedium;
	}
	public int getComments() {
		return comments;
	}
	public void setComments(int comments) {
		this.comments = comments;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	@Override
	public String toString() {
		return "Video [response=" + response + ", vid=" + vid + ", ownerId=" + owner_id + ", title=" + title + ", date="
				+ date + ", views=" + views + ", image=" + image + ", imageMedium=" + imageMedium + ", comments="
				+ comments + ", player=" + player + "]";
	}
	
	
}
