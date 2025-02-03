package com.asyncgate.guild_server.support.utility;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.asyncgate.guild_server.exception.FailType;
import com.asyncgate.guild_server.exception.GuildServerException;
import java.time.LocalDate;
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
    public String uploadFile(final MultipartFile file, final String domain) {
        try {
            String fileName = domain + "/" + LocalDate.now().toString() + UUID.randomUUID().toString();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file.getInputStream(), objectMetadata);

            amazonS3Client.putObject(putObjectRequest);

            return bucketUrl + fileName;
        } catch (SdkClientException | IOException e) {
            throw new GuildServerException(FailType._UPLOAD_FILE_ERROR);
        } catch (Exception e) {
            throw new GuildServerException(FailType._UNKNOWN_ERROR);
        }
    }

    // S3 파일 제거
    public void deleteFile(final String fileUrl) {
        try {
            // fileName 추출
            String fileName = fileUrl.replace(bucketUrl, "");

            // 파일 존재 여부 확인
            boolean fileExists = amazonS3Client.doesObjectExist(bucketName, fileName);

            if (!fileExists) {
                throw new GuildServerException(FailType._FILE_NOT_FOUND);
            }

            amazonS3Client.deleteObject(bucketName, fileName);

        } catch (SdkClientException e) {
            throw new GuildServerException(FailType._DELETE_FILE_ERROR);
        } catch (Exception e) {
            throw new GuildServerException(FailType._UNKNOWN_ERROR);
        }
    }
}
