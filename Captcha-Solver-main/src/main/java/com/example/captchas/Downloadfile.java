package com.example.captchas;

import java.util.Arrays;
import java.util.Random;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.FileOutputStream;
import java.io.File;

public class Downloadfile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileName = downloadFile();
	}

	public static String downloadFile() { // This method will download file using RestTemplate
		String fileName = "ERROR";
		FileOutputStream fos = null;
		try {
			RestTemplateBuilder restTemplate = new RestTemplateBuilder();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
			HttpEntity<String> entity = new HttpEntity<>(headers);
			String randomNum =  getRandomNumber();
			String urlString = "https://image.captchas.net/?client=demo&random=" + randomNum + "&letters=4";
			ResponseEntity<byte[]> response = restTemplate.build().exchange(urlString,
					HttpMethod.GET, entity, byte[].class);
			File resource = new ClassPathResource("static/images/tmp").getFile();
			String tmpFilename = randomNum + ".png";
			File imgFile = new File(resource, tmpFilename);
			fos = new FileOutputStream(imgFile);
			fos.write(response.getBody());
			fileName = tmpFilename;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return fileName;
	}
	
	public static String getRandomNumber() {
	    Random random = new Random();
	    int randomNum = random.nextInt(9999);
	    return String.format("%04d", randomNum);
	    
	}
	
	
}
