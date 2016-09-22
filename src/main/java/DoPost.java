import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DoPost {

	String USER_AGENT= "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:45.0) Gecko/20100101 Firefox/45.0";
	MysqlDAO sql= new MysqlDAO();
	
	String token;
	
	DoPost(String token){
		this.token=token;
	}
	 

	void sendGet(String url) throws Exception {
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
		//Parser p = new Parser();
		//p.todayTranslationsWriter(response.toString());
		parseJson(response.toString());
		
		
	}
	 
	void getVideoResponse(List<String> videosIds,String ownerId){
		for(int i=videosIds.size()-1;i>=0;i--){
			
			String url="https://api.vkontakte.ru/method/video.get?ownerId="+ownerId+"&videos="+videosIds.get(i)+"&access_token="+this.token;
			
			
			try {
				sendGet(url);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	void parseJson(String json){
		
		ObjectMapper mapper = new ObjectMapper(); 
		
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    //ResponseWrapper wrapper = null;
	    JsonNode nodee=null;
	    try {
	        //wrapper = mapper.readValue(json , ResponseWrapper.class);
	        JsonNode node= mapper.readTree(json);
	         nodee=node.withArray("response");
	         mapper.readValue(nodee.get(1).toString(),Video.class);
	         System.out.println("wrapper = " +  mapper.readValue(nodee.get(1).toString(),Video.class));
	         Video answer=mapper.readValue(nodee.get(1).toString(),Video.class);
	         System.out.println(answer.getPlayer());
	        
	         System.out.println(answer.getImage());
	         Parser p = new Parser();
	         String title=answer.getTitle();
	         String href=p.latinHref(answer.getTitle());
	         
	         boolean isInDatabse=sql.selectTitle(title);
	        
	         if(isInDatabse){	
	        	 String iframe=p.iFrameGenerator(answer.getPlayer());
	        	 String mainText ="<!-- MarketGidComposite Start --><div id=\"MarketGidScriptRootC601360\"> <div id=\"MarketGidPreloadC601360\">"
	        		+ "<a id=\"mg_add601360\" href=\"http://usr.marketgid.com/demo/celevie-posetiteli/\" target=\"_blank\"><img src=\"//cdn.marketgid.com/images/marketgid_add_link.png\" style=\"border:0px\"></a><br> "
	        		+ "<a href=\"http://marketgid.com/\" target=\"_blank\">Загрузка...</a> </div> <script> "
	        		+ "(function(){"
	        		+ "var D=new Date(),d=document,b='body',ce='createElement',ac='appendChild',st='style',ds='display',n='none',gi='getElementById';"
	        		+ "var i=d[ce]('iframe');i[st][ds]=n;d[gi](\"MarketGidScriptRootC601360\")[ac](i);try{var iw=i.contentWindow.document;iw.open();iw.writeln(\"<ht\"+\"ml><bo\"+\"dy></bo\"+\"dy></ht\"+\"ml>\");iw.close();var c=iw[b];}"
	        		+ "catch(e){var iw=d;var c=d[gi](\"MarketGidScriptRootC601360\");}var dv=iw[ce]('div');dv.id=\"MG_ID\";dv[st][ds]=n;dv.innerHTML=601360;c[ac](dv);"
	        		+ "var s=iw[ce]('script');s.async='async';s.defer='defer';s.charset='utf-8';s.src=\"//jsc.marketgid.com/f/o/footlivehd.com.601360.js?t=\"+D.getYear()+D.getMonth()+D.getDate()+D.getHours();c[ac](s);})();"
	        		+ "</script></div><!-- MarketGidComposite End -->"
	        		+ "\n"+iframe;
	        	 sql.insertTranslationQuery(mainText,title, href);
	   	         int id=sql.postedId(title);
	   	         System.out.println(id);
	   	        
	         
	   	         sql.insertIntoPostRelationships(id,23);
	         
	   	         sql.insertIntoWpPostMeta(id, "_thumbnail_id", "1573");
	   	         //sql.insertIntoWpPostMeta(id, "tie_post_head", "video");
	   	         //sql.insertIntoWpPostMeta(id, "tie_embed_code", iframe);
	   	         sql.insertIntoWpPostMeta(id, "_yoast_wpseo_content_score", "30");
	   	         sql.insertIntoWpPostMeta(id, "_yoast_wpseo_primary_category", "23");
	         }
	         
	    } catch (java.lang.NullPointerException ex){
	    	System.out.println("Video was banned!");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
