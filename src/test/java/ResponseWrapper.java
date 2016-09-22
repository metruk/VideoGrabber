import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseWrapper {
	@JsonProperty ("response")
	List<Response> response;
}
