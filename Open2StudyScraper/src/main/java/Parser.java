import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    public static final String DB_URL = "jdbc:mysql://localhost:3306/moocs160";
	
	public static final String WEBSITE = "https://www.open2study.com/courses";
	public static void main(String[] args) {
		try{
			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, "root", "");
			System.out.println("Loading page...");
			
			Document doc = Jsoup.connect(WEBSITE).get();
			Elements courses = doc.select(".field-content");
			
			System.out.println("Parsing courses...");
			ArrayList<Course> parsedCourseInfo = new ArrayList<Course>();
			
			for (Element e : courses){
				String title = e.getElementsByClass("adblock_course_title").first().ownText();
				String description = e.getElementsByClass("adblock_course_body").first().ownText();
				
				
				String detailPage = "https://www.open2study.com" + e.getElementsByTag("a").attr("href"); 
						//e.baseUri() + "/" + title.replace(' ', '-');
				
				Course c = new Course(title, description, detailPage);
				
				parsedCourseInfo.add(c);
				
				try {
					doc = Jsoup.connect(c.getDetailPageURL()).followRedirects(true).get();
					System.out.println("detail page = " + c.getDetailPageURL());
					
					Element nameElement = doc.getElementById("subject-teacher-tagline").firstElementSibling().getElementsByTag("h5").first();
					
					String instructorName = nameElement.text().substring(3);
					
					Element startDateElement = doc.getElementsByClass("offering_dates_date").first();
					String startDate = "";
					String endDate = "";
					
					if (startDateElement != null){
						startDate = startDateElement.text();
						endDate = doc.getElementsByClass("offering_dates_date").get(1).text();
					}
					
					String whatIsItAbout = doc.getElementsByClass("full-body").get(0).text();
					String whatWillILearn = doc.getElementsByClass("whatyouwilllearncontainer").get(0).text();
					String instructorBio = doc.getElementsByClass("readmore-container-truncate").select("p").text();
					String teacherImage = doc.getElementsByClass("image-style-teacher-small-profile").get(0).attr("src");
					String videoLink = doc.getElementsByClass("media-youtube-player").get(0).attr("src");
					
					
					c.setInstructor(instructorName);
					c.setStartDate(startDate);
					c.setEndDate(endDate);
					c.setWhatIsItAbout(whatIsItAbout);
					c.setWhatIsItAbout(whatWillILearn);
					c.setInstructorBio(instructorBio);
					c.setTeacherImage(teacherImage);
					c.setVideoLink(videoLink);
					
					
				} catch (Exception ex) {
					System.out.println("Error at: " + c.getDetailPageURL());
					System.out.println(ex.toString());
					ex.printStackTrace();
					return;
				}
				
			}
			
			java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
			for (Course c : parsedCourseInfo){
				System.out.println(c);
				
			    Statement stmt = conn.createStatement(); 
			    StringBuilder sql = new StringBuilder("INSERT INTO `course_data` (`id`, `title`, `short_desc`, `long_desc`, `course_link`, `video_link`, "
						+ "`start_date`, `course_length`, `course_image`, `category`, `site`, `course_fee`, `language`, `certificate`, `university`, `time_scraped`) "
						+ "VALUES ('");
				sql.append(c.getId());
				sql.append("', '");
				sql.append(c.getTitle());
				sql.append("', '");
				sql.append(c.getDescription());
				sql.append("', '");
				sql.append("N/A");
				sql.append("', '");
				sql.append(c.getDetailPageURL());
				sql.append("', '");
				sql.append(c.getVideoLink());
				sql.append("', '");
				
				Date startDate = new Date(2015, 1, 1);
				if (!c.getStartDate().isEmpty())
				{
					String nums[] = c.getStartDate().split("/");
					startDate.setDate(Integer.parseInt(nums[0]));
					startDate.setMonth(Integer.parseInt(nums[1]));
					startDate.setYear(Integer.parseInt(nums[2]));
				}
				
				sql.append(startDate);
				sql.append("', '");
				sql.append("0");
				sql.append("', '");
				sql.append(c.getTeacherImage());
				sql.append("', '");
				sql.append("N/A");
				sql.append("', '");
				sql.append("Open2Study");
				sql.append("', '");
				sql.append("0");
				sql.append("', '");
				sql.append("English");
				sql.append("', '");
				sql.append("no");
				sql.append("', '");
				sql.append("N/A");
				sql.append("', '");
				sql.append(sqlDate.toString());
				sql.append("')");
				stmt.execute(sql.toString());
			}
			int i = 741;
			for (Course c : parsedCourseInfo){
				
			    Statement stmt = conn.createStatement(); 
			    String instName = c.getInstructorName();
			    if (instName.length() > 50){
			    	instName = instName.substring(0, 50);
			    }
				String sqler = "INSERT INTO `coursedetails` (`id`,`profname`, `profimage`, `course_id`) VALUES ('" + i + "','" + instName+"', '" + c.getTeacherImage()+"', '" + c.getId() + "')";
				stmt.execute(sqler);
				++i;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	

}