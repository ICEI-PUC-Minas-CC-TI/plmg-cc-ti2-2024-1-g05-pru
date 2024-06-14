package util;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.CompareFacesMatch;
import software.amazon.awssdk.services.rekognition.model.CompareFacesRequest;
import software.amazon.awssdk.services.rekognition.model.CompareFacesResponse;
import software.amazon.awssdk.services.rekognition.model.Image;

public class CompareFacesUtil {
	private static Float similarityThreshold = 70F;
	private static Region region = Region.US_EAST_1;

	public static boolean compareTwoFaces(String sourceImage, String targetImage) {
		RekognitionClient rekClient = RekognitionClient.builder()
			.region(region)
			.credentialsProvider(ProfileCredentialsProvider.create())
			.build();

		try {
			InputStream sourceStream = new URI(sourceImage).toURL().openStream();
			InputStream targetStream = new URI(targetImage).toURL().openStream();

			SdkBytes sourceBytes = SdkBytes.fromInputStream(sourceStream);
			SdkBytes targetBytes = SdkBytes.fromInputStream(targetStream);

			Image souImage = Image.builder()
				.bytes(sourceBytes)
				.build();

			Image tarImage = Image.builder()
				.bytes(targetBytes)
				.build();

			CompareFacesRequest facesRequest = CompareFacesRequest.builder()
				.sourceImage(souImage)
				.targetImage(tarImage)
				.similarityThreshold(similarityThreshold)
				.build();

			CompareFacesResponse compareFacesResult = rekClient.compareFaces(facesRequest);
			List<CompareFacesMatch> faceDetails = compareFacesResult.faceMatches();

			return !faceDetails.isEmpty();
		} catch (Exception e) {
			System.out.println(e);
			return false;
		} finally {
			rekClient.close();
		}
	}
}
