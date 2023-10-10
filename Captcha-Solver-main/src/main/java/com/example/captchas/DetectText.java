package com.example.captchas;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetectText {

	public static void main(String... args) throws Exception {
		detectText();
	}

	public static void detectText() throws IOException {
		// TODO(developer): Replace these variables before running the sample.
		//String filePath = "/Temp/image3.png";
		String filePath = "/Temp/download.png";
		String t = detectText(filePath);
		System.out.println("Result=" + t);
	}

	// Detects text in the specified image.
	public static String detectText(String filePath) throws IOException {
		List<AnnotateImageRequest> requests = new ArrayList<>();

		ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

		Image img = Image.newBuilder().setContent(imgBytes).build();
		Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
		AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		requests.add(request);

		// Initialize client that will be used to send requests. This client only needs
		// to be created
		// once, and can be reused for multiple requests. After completing all of your
		// requests, call
		// the "close" method on the client to safely clean up any remaining background
		// resources.
		try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
			BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();

			for (AnnotateImageResponse res : responses) {
				if (res.hasError()) {
					System.out.format("Error: %s%n", res.getError().getMessage());
					return null;
				}

				// For full list of available annotations, see http://g.co/cloud/vision/docs
//				for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
//					System.out.format("Text: %s%n", annotation.getDescription());
//					System.out.format("Position : %s%n", annotation.getBoundingPoly());
//				}
				
				// In our case, the text we are looking for are at the second one
				EntityAnnotation annotation = res.getTextAnnotationsList().get(1);
				if (annotation != null) {
					System.out.format("Finding Text: %s%n", annotation.getDescription());
					return annotation.getDescription();
				}
			}
		}
		return null;
	}
}