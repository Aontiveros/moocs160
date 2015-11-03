
public class Course {

	private String title;
	private String description;
	private String detailPage;
	private String instructor;
	
	public Course(String title, String description, String detailPage){
		this.title = title;
		this.description = description;
		this.detailPage = detailPage;
	}
	
	public void setInstructor(String instructor){
		this.instructor = instructor;
	}
	
	public String getDetailPageURL(){
		return detailPage;
	}
	
	public String toString(){
		return "Title: " + title 
				+ "\nDescription: " + description
				+ "\nInstructor: " + instructor;
	}
	
}
