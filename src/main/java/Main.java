import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		
		//05e339636f8d1c62973c3bf902362e3a9aa6e7458fb372987247591407d5c54e45b8db1979e10a8a12a5a
		System.out.print( "Enter a token:\n");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String token = null;
		try {
			 token= in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Parser p = new Parser();
		String groupId=p.getGroupId("https://vk.com/lifesport");
		System.out.println(groupId);
	
		List<String> videos= new ArrayList<String>();
		String videosAlbum="https://vk.com/videos"+groupId;
		videos=p.getVideoId(videosAlbum);
		videos=videos.subList(0, videos.size()/4);
		System.out.println(videos.size());
		
		DoPost post= new DoPost(token);
		
		post.getVideoResponse(videos, groupId);
		/*
		Date date = new Date();   // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date);   // assigns calendar to given date 
		calendar.get(Calendar.HOUR_OF_DAY);
		*/
		
		//p.groupConnector("https://vk.com/videos-28639294");
	}
	

}
