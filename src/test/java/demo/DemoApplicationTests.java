package demo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import demo.domain.MyResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration

public class DemoApplicationTests {

	final String BASE_URL = "http://localhost:8080";
	//private final static String user = "user";
	//private final static String password = "password";

	
	@Test
	@Ignore
	public void testRestService() {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		ResponseEntity<String> response = restTemplate.exchange(BASE_URL,  HttpMethod.GET, 
			      new HttpEntity<String>(headers),
			      String.class);
		
		System.out.println("response: " + response.getBody());
	}
	
	
	
	@Test
	public void testRestServicegetName() {
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		Map<String, String> vars = new HashMap<String, String>();
        vars.put("name", "JS23");
		
		ResponseEntity<MyResponse> response = restTemplate.exchange(BASE_URL+"/user/{name}", HttpMethod.GET, entity, MyResponse.class, vars);
		System.out.println("Received response: "+ response);
		MyResponse myResponse = response.getBody();
		System.out.println("Received response: "+ myResponse.getName());
	}


}

