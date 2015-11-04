
public class Course {

	private String title;
	private String description;
	private String detailPage;
	private String instructorName;
	private String startDate;
	private String endDate;
	private String whatIsItAbout;
	private String whatWillILearn;
	private String instructorBio;
	
	
	public Course(String title, String description, String detailPage){
		this.title = title;
		this.description = description;
		this.detailPage = detailPage;
	}
	

	
	public void setInstructor(String instructor){
		this.instructorName = instructor;
	}
	
	public String getDetailPageURL(){
		return detailPage;
	}
	
	public String toString(){
		return "Title: " + title 
				+ "\nDescription: " + description
				+ "\nInstructor: " + instructorName;
	}



	public String getStartDate() {
		return startDate;
	}



	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}



	public String getEndDate() {
		return endDate;
	}



	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	public String getWhatIsItAbout() {
		return whatIsItAbout;
	}



	public void setWhatIsItAbout(String whatIsItAbout) {
		this.whatIsItAbout = whatIsItAbout;
	}



	public String getWhatWillILearn() {
		return whatWillILearn;
	}



	public void setWhatWillILearn(String whatWillILearn) {
		this.whatWillILearn = whatWillILearn;
	}



	public String getInstructorBio() {
		return instructorBio;
	}



	public void setInstructorBio(String instructorBio) {
		this.instructorBio = instructorBio;
	}
}
	