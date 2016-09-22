import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
	
	Document groupConnector(String href){
		Document doc = null;
		while(doc==null){
			try {
				doc = Jsoup.connect(href).get();
			}catch(java.net.UnknownHostException ex){
				ex.printStackTrace();
				System.out.println("UnknownHostException");
			}catch(java.lang.IllegalArgumentException ex){
				ex.printStackTrace();
				System.out.println("Argument");
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return doc;
	}
	
	
	
	String getGroupId(String vkHref) {

	Document doc = groupConnector(vkHref);
	Element id = doc.body();
		//class="profile_menu
	Elements profileMenu =id.select("ul[class=profile_menu]");
	Elements links = profileMenu.select("a[href]");
	
	String albumPattern="(a href=\"\\/videos)(-[0-9]+)";
	
	String ownerId=regexAlbum(albumPattern, links.toString(),2);	
	
	return ownerId;
	}
	
	List <String> getVideoId(String vkVideo){
		List<String> videos = new ArrayList<String>();
		Document doc=groupConnector(vkVideo);
		Elements elements=doc.select("a[class=video_item_title]");
		
		for(Element element:elements){
			if(!element.select("a[href]").toString().contains("section")){
			String parsedString=element.select("a[href]").toString();
			String pattern="(href=\"\\/video)(-[0-9]+_[0-9]+)";
			String video=regexAlbum(pattern, parsedString,2);
			System.out.println(video);
			videos.add(video);
			
			}
		}
	return videos;	
	}
	
	
	
	String regexAlbum(String pattern,String matcher,int group){
		Pattern patt = Pattern.compile(pattern);
		Matcher match = patt.matcher(matcher);
		String albumHref="";
		if (match.find()) {
			albumHref += match.group(group);
		}	
	return albumHref;
	}
	
	String latinHref(String header) {
		header = header.toLowerCase();
		header = header.replace(" ", "-");
		header = header.replace(".", "-");
		header = header.replace("–", "");
		header = header.replace(",", "-");
		header = header.replace("|", "-");
		header = header.replace("---", "-");
		header = header.replace(":", "-");
		header = header.replace("(", "-");
		header = header.replace(")", "-");
		header = header.replace("--", "-");
		header = header.replace("/", "-");
		header = header.substring(0,header.length()-1);
		
		

		char[] english = { 'a', 'b', 'v', 'g', 'd', 'e', 'e', 'j', 'z', 'i',
				'k', 'l', 'm', 'n', 'o', 'p', 'r', 's', 't', 'h', 'f', 'u',
				's', 's', 'q', 'u', 'a', 'i', 'c', 'j', 'c' };

		char[] russian = { 'а', 'б', 'в', 'г', 'д', 'е', 'э', 'ж', 'з', 'и',
				'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'х', 'ф', 'у',
				'ш', 'щ', 'ь', 'ю', 'я', 'ы', 'ч', 'й', 'ц' };
		for (int i = 0; i < header.length(); i++) {
			for (int j = 0; j < russian.length; j++) {
				if (header.charAt(i) == russian[j]) {
					header = header.replace(russian[j], english[j]);

				}
			}
		}
		return header;
	}
	
	String iFrameGenerator(String player){
	
		return "<iframe src=\""+player+"&hd=2\" width=\"853\" height=\"480\" frameborder=\"0\" allowfullscreen></iframe>";
		
	}
}
