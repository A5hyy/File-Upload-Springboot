package FileUpload.Springboot.services;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AmazonS3Service {

    @Autowired
    private AmazonS3 amazonS3Client;

    public String upload(MultipartFile multipartFile) throws IOException {

        //create and put your own User Data
        Map<String, String> userData = new HashMap<>();
        userData.put("name", "ash");
        userData.put("uploadTime", (new Date()).toString());
        userData.put("originalFileName", multipartFile.getOriginalFilename());

        //set the Metadata for the file upload
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setUserMetadata(userData);

        //to generate a new filename using uuid, so that the filename will not clash if use same name
        String key = UUID.randomUUID().toString().substring(0, 8);

        // Create a put request. 1st, bucketname in spaces
        PutObjectRequest putObjectRequest = new PutObjectRequest(
            "datauploads", //Bucket name in spaces
            "myUploads/%s".formatted(key), //key
            multipartFile.getInputStream(), //inputStream
            objectMetadata); //metadata

        // Allow public access
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3Client.putObject(putObjectRequest);

        return key;
    }
}
