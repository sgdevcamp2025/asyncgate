package com.asyncgate.user_server.utility;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class S3Util {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.s3.url}")
    private String bucketUrl;

    // S3 이미지 업로드
    public String uploadFile(final MultipartFile file, final String id) {
        try {
            String fileName = id + "/" + UUID.randomUUID().toString();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file.getInputStream(), objectMetadata);

            amazonS3Client.putObject(putObjectRequest);

            return bucketUrl + fileName;
        } catch (SdkClientException | IOException e) {
            throw new UserServerException(FailType.UPLOAD_FILE_ERROR);
        }
    }

    // S3 파일 제거
    public void deleteFile(final String fileUrl) {
        try {
            // 공백 제거
            String fileName = fileUrl.replace(bucketUrl, "");

            amazonS3Client.deleteObject(bucketName, fileName);
        } catch (SdkClientException e) {
            throw new UserServerException(FailType.DELETE_FILE_ERROR);
        }
    }
}
