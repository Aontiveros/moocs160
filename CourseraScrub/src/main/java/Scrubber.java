import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Scrubber {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 static String DB_URL = "jdbc:mysql://localhost:3306/moocs160";
	//static final java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/scrapedcourse","root","");
	// Database credentials
	static final String USER = "root";
	static final String PASS = "";

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	      Connection conn = DriverManager.getConnection(DB_URL, "root", "");
	      Statement stmt = conn.createStatement(); 
		String s ="https://api.coursera.org/api/catalog.v1/courses?fields=shortDescription,photo,video,aboutTheCourse,language,instructor&includes=sessions,instructors,universities,categories";
		    URL url = new URL(s);
		    Scanner scan = new Scanner(url.openStream());
		    String str = new String();
		    while (scan.hasNext()){
		        str += scan.nextLine();
		        }
		    scan.close();
		int count = 0;
		JSONObject obj = new JSONObject(str);
	
		JSONArray arrayOfCourses = (JSONArray) obj.get("elements");
		JSONObject link = (JSONObject) arrayOfCourses.get(25);	
		System.out.println(link);
		JSONObject jk = link.getJSONObject("links");
		System.out.println(jk);
		JSONArray sj = jk.getJSONArray("sessions");
		HashMap<String, HashMap<String, String>> sessionsMap = getSessions();
		HashMap<String, HashMap<String, String>> instructorMap = getInstructors();
		HashMap<String, String> categoriesMap = getCategories();
		HashMap<String, String> uniMap = getUniversities();
		
		
		ArrayList<Courses> listOfCourses = new ArrayList<>();
		for(int i = 0; i < arrayOfCourses.length(); i++){
			Courses c = new Courses();
			JSONObject currentObject = arrayOfCourses.getJSONObject(i);
			String name = currentObject.getString("name").replaceAll("[()']", "");
			c.setName(name);
			String id = Integer.toString(currentObject.getInt("id"));
			c.setId(id);
			JSONObject links = currentObject.getJSONObject("links");
			String photoLink = currentObject.getString("photo");
			c.setImage(photoLink);
			String shortDescription = currentObject.getString("shortDescription");
			shortDescription = shortDescription.replaceAll("[()']", "");
			c.setShortDescription(shortDescription);
			String video = "https://www.youtube.com/watch?v=" + currentObject.getString("video");
			c.setVideo(video);
			String longDescription = currentObject.getString("aboutTheCourse");
			longDescription = longDescription.replaceAll("[()’']", "");
			c.setLongDescription(longDescription);
			String language = currentObject.getString("language");
			c.setLanguage(language);
			String startDate = "";
			String duration = "";
			String siteLink = "";
            String universities = "";
            String categories = "";
            String cert = "no";
            HashMap<String, String> instructors = new HashMap<>();
            try{
			JSONArray sessionsArray = links.getJSONArray("sessions");
			for(int j = 0; j < sessionsArray.length(); j++){
				int element = (int) sessionsArray.get(j);
				String sessions = "";
				cert = sessionsMap.get(Integer.toString(element)).get("cert");
				siteLink = sessionsMap.get(Integer.toString(element)).get("link");
				startDate = sessionsMap.get(Integer.toString(element)).get("start");
				duration = sessionsMap.get(Integer.toString(element)).get("duration");
				if(duration.equals("")){ duration = "5";}
				 
			}}
            catch(Exception e){
            	System.out.println(e.getMessage());
            }
            c.setCertificate(cert);
            c.setSite("Coursera");
            c.setCourseLength(duration);
            c.setLink(siteLink);
            c.setStartDate(startDate);
            try{
    			JSONArray universitiesArray = links.getJSONArray("universities");
    			for(int j = 0; j < universitiesArray.length(); j++){
    				int element = (int) universitiesArray.get(j);
    				universities += uniMap.get(Integer.toString(element)) + ",";  
    		}}
            catch(Exception e){
            	System.out.println(e.getMessage());
            }
            c.setUniversities(universities);
            try{
    			JSONArray categoriesArray = links.getJSONArray("categories");
    			for(int j = 0; j < categoriesArray.length(); j++){
    				int element = (int) categoriesArray.get(j);
    				categories += categoriesMap.get(Integer.toString(element)) + ",";
    		}}
            catch(Exception e){
            	System.out.println(e.getMessage());
            }
            c.setCategories(categories);
            try{
    			JSONArray instructorsArray = links.getJSONArray("instructors");
    			for(int j = 0; j < instructorsArray.length(); j++){
    				int element = (int) instructorsArray.get(j);
    				instructors.put(Integer.toString(element), "");
    				String sqler = "INSERT INTO `coursedetails` (`id`,`profname`, `profimage`, `course_id`) VALUES ('" + count + "','" + instructorMap.get(Integer.toString(element)).get("name")+"', '" + instructorMap.get(Integer.toString(element)).get("photo")+"', '" + id + "')";
    				stmt.execute(sqler);
    				count++;
    			}}
            catch(Exception e){
            	System.err.println(e.getMessage());
            }
            listOfCourses.add(c);
		}

	      for(int i = 0; i < listOfCourses.size(); i++){
	    	//  String query = listOfCourses.get(i).toDatabase();
	    	 // Statement stmt = conn.createStatement(); 
	    	 try{
	    		// boolean re = stmt.execute(query);
	    	 }
	    	 catch(Exception e){
	    		 String cate = listOfCourses.get(i).toDatabase();
	    		 //System.out.println(cate);
	    		 throw e;
	    	 }
	      }

	}

	private static HashMap<String, String> getUniversities() throws IOException {
		String s ="https://api.coursera.org/api/catalog.v1/universities?fields=name";
	    URL url = new URL(s);
	    Scanner scan = new Scanner(url.openStream());
	    String str = new String();
	    while (scan.hasNext()){
	        str += scan.nextLine();
	        }
	    scan.close();
	  
	    JSONObject obj = new JSONObject(str);
	    JSONArray arrayOfSessions = (JSONArray) obj.get("elements");
	    HashMap<String, String> courseSessions = new HashMap<>();
	    for(int i = 0; i < arrayOfSessions.length(); i++){
	    	JSONObject currentObject = arrayOfSessions.getJSONObject(i);
	    	String uniName = "";
	    	try{
	    		uniName = currentObject.getString("name");
	    	}
	    	catch(Exception e){
	    		System.out.println("No name found");
	    	}
	    	String id = "";
	    	try{
	    		id = Integer.toString(currentObject.getInt("id"));
	    	}
	    	catch(Exception e){
	    		System.out.println("No id found");
	    	}
	    	courseSessions.put(id, uniName);
	    	
	    }
		return courseSessions;
	}

	private static HashMap<String, String> getCategories() throws IOException {
		String s ="https://api.coursera.org/api/catalog.v1/categories";
	    URL url = new URL(s);
	    Scanner scan = new Scanner(url.openStream());
	    String str = new String();
	    while (scan.hasNext()){
	        str += scan.nextLine();
	        }
	    scan.close();
	  
	    JSONObject obj = new JSONObject(str);
	    JSONArray arrayOfSessions = (JSONArray) obj.get("elements");
	    HashMap<String, String> courseSessions = new HashMap<>();
	    for(int i = 0; i < arrayOfSessions.length(); i++){
	    	JSONObject currentObject = arrayOfSessions.getJSONObject(i);
	    	String firstName = "";
	    	try{
	    		firstName = currentObject.getString("name");
	    	}
	    	catch(Exception e){
	    		System.out.println(e.getMessage());
	    	}
	    	String id = "";
	    	try{
	    		id = Integer.toString(currentObject.getInt("id"));
	    	}
	    	catch(Exception e){
	    		System.out.println(e.getMessage());
	    	}
	    	courseSessions.put(id, firstName);
	    	
	    }
		return courseSessions;
	}

	private static HashMap<String, HashMap<String, String>> getInstructors() throws IOException {
		String s ="https://api.coursera.org/api/catalog.v1/instructors?fields=photo&includes=courses";
	    URL url = new URL(s);
	    Scanner scan = new Scanner(url.openStream());
	    String str = new String();
	    while (scan.hasNext()){
	        str += scan.nextLine();
	        }
	    scan.close();
	  
	    JSONObject obj = new JSONObject(str);
	    JSONArray arrayOfSessions = (JSONArray) obj.get("elements");
	    HashMap<String, HashMap<String,String>> courseSessions = new HashMap<>();
	    HashMap<String, String> instruc = new HashMap<>();
	    for(int i = 0; i < arrayOfSessions.length(); i++){
	    	instruc = new HashMap<>();
	    	JSONObject currentObject = arrayOfSessions.getJSONObject(i);
	    	String firstName = "";
	    	try{
	    		firstName = currentObject.getString("firstName");
	    	}
	    	catch(Exception e){
	    		System.out.println(e.getMessage());
	    	}
	    	String lastName = "";
	    	try{
	    		lastName = currentObject.getString("lastName");
	    	}
	    	catch(Exception e){
	    		System.out.println(e.getMessage());
	    	}
	    	String photo = "";
	    	try{
	    		photo = currentObject.getString("photo");
	    	}
	    	catch(Exception e){
	    		System.out.println(e.getMessage());
	    	}
	    	String id = "";
	    	try{
	    		id = Integer.toString(currentObject.getInt("id"));
	    	}
	    	catch(Exception e){
	    		System.out.println(e.getMessage());
	    	}
	    	instruc.put("name", firstName + " " + lastName);
	    	instruc.put("photo", photo);
	    	courseSessions.put(id, instruc);
	    }
		return courseSessions;
	}

	private static HashMap<String, HashMap<String, String>> getSessions() throws IOException {
		String s ="https://api.coursera.org/api/catalog.v1/sessions?includes=courses&fields=startDay,startMonth,startYear,durationString";
	    URL url = new URL(s);
	    Scanner scan = new Scanner(url.openStream());
	    String str = new String();
	    while (scan.hasNext()){
	        str += scan.nextLine();
	        }
	    scan.close();
	    JSONObject obj = new JSONObject(str);
	    JSONArray arrayOfSessions = (JSONArray) obj.get("elements");
	    HashMap<String, HashMap<String, String>> courses = new HashMap<>();
	    HashMap<String, String> courseSessions = new HashMap<>();
	    for(int i = 0; i < arrayOfSessions.length(); i++){
	    	courseSessions = new HashMap<>();
	    	JSONObject currentObject = arrayOfSessions.getJSONObject(i);
	    	String cert = "no";
	    	try{
	    		cert = currentObject.getBoolean("eligibleForCertificates") ? "yes" : "no";
	    	}
	    	catch(Exception e){
	    		System.out.println(e.getMessage());
	    	}
	    	String link = "";
	    	try{
	    		link = currentObject.getString("homeLink");
	    	}
	    	catch(Exception e){
	    		System.out.println(e.getMessage());
	    	}
	    	String startDay = "";
	    	try{
	    		startDay = currentObject.getInt("startDay") + "";
	    	}
	    	catch(Exception e){
	    		System.out.println(currentObject);
	    		System.out.println(e.getMessage());
	    	}
	    	String startMonth = "";
	    	try{
	    		startMonth = currentObject.getInt("startMonth") + "";
	    	}
	    	catch(Exception e){
	    		System.out.println(e.getMessage());
	    	}
	    	String startYear = "";
	    	try{
	    		startYear = currentObject.getInt("startYear") + "";
	    	}
	    	catch(Exception e){
	    		System.out.println(e.getMessage());
	    	}
	    	String durationString = "";
	    	try{
	    		durationString = currentObject.getString("durationString");
	    	}
	    	catch(Exception e){
	    			System.out.println(e.getMessage());
	    	}
	    	String id = "";
	    	try{
	    		id = currentObject.getInt("id") + "";
	    	}
	    	catch(Exception e){
	    		System.out.println(e.getMessage());
	    	}
	    	courseSessions.put("cert", cert);
	    	courseSessions.put("start", startMonth + "/" + startDay + "/" + startYear);
	    	courseSessions.put("duration", durationString);
	    	courseSessions.put("link", link);
	    	courses.put(id, courseSessions);
	    }
		return courses;
	}

}
