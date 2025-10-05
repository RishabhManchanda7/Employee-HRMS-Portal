package com.example.hrms.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
//.
@Configuration
@EnableDynamoDBRepositories(basePackages = "com.example.hrms.repository")
public class DynamoDBConfig {

    @Value("${aws.dynamodb.endpoint:http://localhost:8000}")
    private String dynamoDbEndpoint;

    @Value("${aws.dynamodb.region:us-east-1}")
    private String region;

    @Value("${aws.access.key:fake}")
    private String accessKey;

    @Value("${aws.secret.key:fake}")
    private String secretKey;

    @Bean
    @Primary
    public DynamoDBMapperConfig dynamoDBMapperConfig() {
        return DynamoDBMapperConfig.DEFAULT;
    }

    @Bean
    @Primary
    public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig config) {
        return new DynamoDBMapper(amazonDynamoDB, config);
    }

    @Bean
    @Primary
    public AmazonDynamoDB amazonDynamoDB() {
        // For local development (when endpoint is specified)
        if (dynamoDbEndpoint != null && !dynamoDbEndpoint.isEmpty()) {
            return AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoDbEndpoint, region))
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                    .build();
        } else {
            // For AWS production (uses IAM role)
            return AmazonDynamoDBClientBuilder.standard()
                    .withRegion(region)
                    .build();
        }
    }
}
