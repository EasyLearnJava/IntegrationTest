package hello;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@RestController
public class UploadFileToS3BucketController {

    
    private final AtomicLong counter = new AtomicLong();
    private static final String SUFFIX = "/";

    @PostMapping("/uploadToS3")
    public void uploadFile(@RequestParam("file") MultipartFile uploadfile) {
       System.out.println("Uploading File...");
       
       AWSCredentials credentials = new BasicAWSCredentials(
				"AKIAJHVXSKOT43E7QLTA", 
				"7/4V1sSu2ASMS6eOTB2mMmuFXk1Bo7OTueMrO4dK");
       
       // create a client connection based on credentials
    	AmazonS3 s3client = new AmazonS3Client(credentials);
    
    	System.out.println("S3Client : " + s3client);
    	
    	// create bucket - name must be unique for all S3 users
		String bucketName = "testsalesforcestorage";
	
		// create folder into bucket
		String folderName = "testfolder";
		//createFolder(bucketName, folderName, s3client);
		
		// upload file to folder and set it to public
		String fileName = folderName + SUFFIX + "Arkham_Batman.png";
		/*s3client.putObject(new PutObjectRequest(bucketName, fileName, 
				new File("C:\\Users\\OIS - Raghu\\Pictures\\batman\\Arkham_Batman.png"))
				.withCannedAcl(CannedAccessControlList.PublicRead));*/
    }
    
    public static void createFolder(String bucketName, String folderName, AmazonS3 client) {
		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);

		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
				folderName + SUFFIX, emptyContent, metadata);

		// send request to S3 to create folder
		client.putObject(putObjectRequest);
	}
}
