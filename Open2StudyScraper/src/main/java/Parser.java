import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {

	public static volatile boolean threadError = false;
	
	public static final String WEBSITE = "https://www.open2study.com/courses";
	public static void main(String[] args) {
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
				
				
				String detailPage = e.baseUri() + "/" + title.replace(' ', '-');
				
				Course c = new Course(title, description, detailPage);
				
				Thread t = new Thread(new CourseDetailThread(c));
				t.start();
				threads.add(t);
				
				parsedCourseInfo.add(c);
			}
			
			for (Thread t : threads){
				t.join();
				if (threadError)
					throw new Exception("Error loading one of the course detail pages!");
			}
			
			for (Course c : parsedCourseInfo){
				System.out.println(c);
			}
		}
		catch(IOException e){
			System.err.println("Problem connecting to " + WEBSITE);
			e.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public static class CourseDetailThread implements Runnable{

		private Course c;
		
		public CourseDetailThread(Course c){
			this.c = c;
		}
		@Override
		public void run() {
			try {
				Document doc = Jsoup.connect(c.getDetailPageURL()).followRedirects(true).get();
				
				
				String name = doc.select("h3").first().text();
				
				c.setInstructor(name);
				
			} catch (Exception e) {
				System.out.println(c.getDetailPageURL());
				e.printStackTrace();
			}
			
		}
		
	}

}
