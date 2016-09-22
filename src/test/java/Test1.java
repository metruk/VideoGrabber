import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test1 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ObjectMapper mapper = new ObjectMapper(); 
		
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    ResponseWrapper wrapper = null;
	    String json="{\"response\":[1,{\"vid\":456242980,\"owner_id\":-28639294,\"title\":\"Its going viral - Barcelona vs Omiya Ardija u12\",\"description\":\"Подписывайтесь на нас: <br>ФУТБОЛЬНЫЕ ОБЗОРЫ - vk.com\\/lifesport <br>ФУТБОЛЬНЫЕ КАРИКАТУРЫ - vk.com\\/cartfoot <br>ФУТБОЛЬНЫЙ GOOGLE - vk.com\\/gfoot\",\"duration\":73,\"link\":\"video-28639294_456242980\",\"date\":1472512866,\"views\":12612,\"image\":\"http:\\/\\/cs631525.vk.me\\/v631525294\\/4cc94\\/OjNi3FMHKBE.jpg\",\"image_medium\":\"http:\\/\\/cs631525.vk.me\\/v631525294\\/4cc93\\/txf8WaUn4RY.jpg\",\"comments\":9,\"player\":\"https:\\/\\/vk.com\\/video_ext.php?oid=-28639294&id=456242980&hash=b3dc5382a017311a\"}]}";
	    JsonNode nodee=null;
	    try {
	        //wrapper = mapper.readValue(json , ResponseWrapper.class);
	        JsonNode node= mapper.readTree(json);
	         nodee=node.withArray("response");
	         mapper.readValue(nodee.get(1).toString(),Video.class);
	         System.out.println("wrapper = " +  mapper.readValue(nodee.get(1).toString(),Video.class));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	   
	   
		
	}
}
