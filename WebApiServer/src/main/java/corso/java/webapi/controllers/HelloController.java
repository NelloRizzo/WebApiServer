package corso.java.webapi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	@GetMapping("/hello")
	public @ResponseBody String hello() {
		return "Hello, welcome!";
	}
	
	@GetMapping("/hello-name")
	public @ResponseBody String hello(@RequestParam String name) {
		return String.format("Hello, %s, welcome!", name);
	}
	@GetMapping("/hello-param")
	public @ResponseBody String hello2(@PathVariable String name) {
		return String.format("Hello, %s, welcome!", name);
	}
}
