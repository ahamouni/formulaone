package demo.rs;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.domain.MyResponse;

@RestController
public class DemoController {
	
	@RequestMapping("/")
	public String hello() {
		return "Hi Man!!";
	}
	
	/**
	 * Create user
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/user/{name}", method=RequestMethod.GET)
	public MyResponse retrieve(@PathVariable("name")  String name) {
		return new MyResponse("hama", 123);
	}
	 
}

