package com.example.captchas;

import java.io.File;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaptChasController {
	
	@GetMapping("/findText")
	public String check(@RequestParam(name="name", required=true) String name) {
		String text = "ERROR";
		try {
			File resource = new ClassPathResource("static/images/tmp").getFile();
			text = DetectText.detectText(resource + "/" + name);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return text;
	}
	
}
