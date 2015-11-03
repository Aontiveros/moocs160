import java.time.LocalDate;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;

import javax.print.attribute.standard.DateTimeAtCompleted;

public class Courses {
	private String id;
	private String name;
	private String link;
	private String instructors;
	private String universities;
	private String categories;
	private String shortDescription;
	private String longDescription;
	private String startDate;
	private String courseLength;
	private String site;
	private String courseFee;
	private String language;
	private String certificate;
	private String profImage;
	private String profName;
	private String video;
	private String image;
	
		
	public Courses(){
		id = "";
		name = "";
		link = "";
		instructors = "";
		universities = "";
		categories = "";
		shortDescription = "";
		longDescription = "";
		startDate = "";
		courseLength = "";
		site = "";
		courseFee = "49";
		language = "";
		certificate = "";
		profImage = "";
		profName = "";
		video = "";
		image = "";
	}
	
	public String toDatabase(){
		java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
		java.sql.Date startDater = java.sql.Date.valueOf(LocalDate.now());
		if(startDate.length() > 1){
			String[] dater = startDate.split("/");
			if(dater.length == 3){
				String day = (dater[1].equals("")) ? Integer.toString(Calendar.DAY_OF_MONTH) : dater[1];
				String month = (dater[0].equals("")) ?Integer.toString(Calendar.MONTH) : dater[0];
				String year = (dater[2].equals("")) ? Integer.toString(Calendar.YEAR) : dater[2];
				startDater = java.sql.Date.valueOf(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));
			}
				
			if(dater.length != 3)
				System.out.println(startDate);
			
		}
		String lengther = courseLength.replaceAll("[a-zA-z ]", "");
		if(lengther.length() == 0) lengther = Double.toString(Math.random() * 1000 % 7);
		
		StringBuilder sql = new StringBuilder("INSERT INTO `course_data` (`id`, `title`, `short_desc`, `long_desc`, `course_link`, `video_link`, "
				+ "`start_date`, `course_length`, `course_image`, `category`, `site`, `course_fee`, `language`, `certificate`, `university`, `time_scraped`) "
				+ "VALUES ('");
		sql.append(id);
		sql.append("', '");
		sql.append(name);
		sql.append("', '");
		sql.append(shortDescription);
		sql.append("', '");
		sql.append(longDescription);
		sql.append("', '");
		sql.append(link);
		sql.append("', '");
		sql.append(video);
		sql.append("', '");
		sql.append(startDater.toString());
		sql.append("', '");
		sql.append(lengther);
		sql.append("', '");
		sql.append(image);
		sql.append("', '");
		sql.append(categories.replaceAll("['(é)]", ""));
		sql.append("', '");
		sql.append(site);
		sql.append("', '");
		sql.append(courseFee);
		sql.append("', '");
		sql.append(language.replaceAll("[(é')]", ""));
		sql.append("', '");
		sql.append(certificate);
		sql.append("', '");
		sql.append(universities);
		sql.append("', '");
		sql.append(sqlDate.toString());
		sql.append("')");
		return sql.toString();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public String getInstructors() {
		return instructors;
	}


	public void setInstructors(String instructors) {
		this.instructors = instructors;
	}


	public String getUniversities() {
		return universities;
	}


	public void setUniversities(String universities) {
		this.universities = universities;
	}


	public String getCategories() {
		return categories;
	}


	public void setCategories(String categories) {
		this.categories = categories;
	}


	public String getShortDescription() {
		return shortDescription;
	}


	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}


	public String getLongDescription() {
		return longDescription;
	}


	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getCourseLength() {
		return courseLength;
	}


	public void setCourseLength(String courseLength) {
		this.courseLength = courseLength;
	}


	public String getSite() {
		return site;
	}


	public void setSite(String site) {
		this.site = site;
	}


	public String getCourseFee() {
		return courseFee;
	}


	public void setCourseFee(String courseFee) {
		this.courseFee = courseFee;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getCertificate() {
		return certificate;
	}


	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}


	public String getProfImage() {
		return profImage;
	}


	public void setProfImage(String profImage) {
		this.profImage = profImage;
	}


	public String getProfName() {
		return profName;
	}


	public void setProfName(String profName) {
		this.profName = profName;
	}


	public String getVideo() {
		return video;
	}


	public void setVideo(String video) {
		this.video = video;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


}
