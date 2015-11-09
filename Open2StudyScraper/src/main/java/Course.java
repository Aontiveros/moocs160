
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
	private String teacherImage;
	private String videoLink;
	private String id;
	
	
	public Course(String title, String description, String detailPage){
		this.setTitle(title);
		this.setDescription(description);
		this.detailPage = detailPage;
		String[] arr =this.detailPage.split("/");
		id = "1"+arr[arr.length - 1];
	}
	

	
	public void setInstructor(String instructor){
		this.setInstructorName(instructor);
	}
	
	public String getDetailPageURL(){
		return detailPage;
	}
	
	public String toString(){
		return "Title: " + title ;
				
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



	public String getTeacherImage() {
		return teacherImage;
	}



	public void setTeacherImage(String teacherImage) {
		this.teacherImage = teacherImage;
	}



	public String getVideoLink() {
		return videoLink;
	}



	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}



	public String getInstructorName() {
		return instructorName;
	}



	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}
}
	