package FileUpload.Springboot.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AppConfig {

    @Value("${ACCESS_KEY}")
    private String accessKey;

    @Value("fIO2AcYW+RYKqh+J2CV5j+gCEXpbHYedN5OTY0tFzEo")
    private String secretKey;

    @Bean
    public AmazonS3 getS3Client() {
        BasicAWSCredentials cred = new BasicAWSCredentials(
        accessKey, secretKey);
        EndpointConfiguration epConfig = new EndpointConfiguration(
            "sgp1.digitaloceanspaces.com", "sgp1");

            return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(epConfig)
            .withCredentials(new AWSStaticCredentialsProvider(cred))
            .build();

    }
}

// Secret	
// fIO2AcYW+RYKqh+J2CV5j+gCEXpbHYedN5OTY0tFzEo 