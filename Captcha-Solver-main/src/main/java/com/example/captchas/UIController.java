package com.example.captchas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UIController {

	@GetMapping("/showCaptchas")
	//public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
	public String greeting(Model model) {
		//model.addAttribute("name", name);
		String fileName = Downloadfile.downloadFile();
		model.addAttribute("name", fileName);
		return "captchas";
	}

}