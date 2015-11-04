import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	public static volatile boolean threadError = false;
	
	public static final String WEBSITE = "https://www.open2study.com/courses";
	public static void Parser(String[] args) {
		try{
			System.out.println("Loading page...");
			
			Document doc = Jsoup.connect(WEBSITE).get();
			Elements courses = doc.select(".field-content");
			
			System.out.println("Parsing courses...");
			ArrayList<Course> parsedCourseInfo = new ArrayList<Course>();
			ArrayList<Thread> threads = new ArrayList<>();
			
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
					String startDate = doc.getElementsByClass("offering_dates_date").first().text();
					String endDate = doc.getElementsByClass("offering_dates_date").get(1).text();
					String whatIsItAbout = doc.getElementsByClass("full-body").get(0).text();
					String whatWillILearn = doc.getElementsByClass("whatyouwilllearncontainer").get(0).text();
					String instructorBio = doc.getElementById("full-body429").text();
					
					c.setInstructor(instructorName);
					c.setStartDate(startDate);
					c.setEndDate(endDate);
					c.setWhatIsItAbout(whatIsItAbout);
					c.setWhatIsItAbout(whatWillILearn);
					c.setInstructorBio(instructorBio);
					
					
				} catch (Exception ex) {
					System.out.println("Error" + c.getDetailPageURL());
					System.out.println(ex.toString());
					ex.printStackTrace();
				}
				
			}
			
			for (Course c : parsedCourseInfo){
				System.out.println(c);
			}
		}
		catch(IOException e){
			System.err.println("Problem connecting to " + WEBSITE);
			e.printStackTrace();
		}
	}
	

}