package com.asyncgate.chat_server.utility

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.asyncgate.chat_server.exception.FailType;
import com.asyncgate.chat_server.exception.ChatServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Configuration
class S3Util(
    private val amazonS3Client: AmazonS3Client,
    @Value("\${cloud.aws.s3.bucket}") private val bucketName: String,
    @Value("\${cloud.aws.s3.url}") private val bucketUrl: String
) {

    // S3 이미지 업로드
    fun uploadFile(file: MultipartFile, id: String): String {
        return try {
            val fileName = "$id/${UUID.randomUUID()}"
            val objectMetadata = ObjectMetadata().apply {
                contentLength = file.size
                contentType = file.contentType
            }

            val putObjectRequest = PutObjectRequest(bucketName, fileName, file.inputStream, objectMetadata)

            amazonS3Client.putObject(putObjectRequest)

            "$bucketUrl$fileName"
        } catch (e: SdkClientException) {
            throw ChatServerException(FailType._UPLOAD_FILE_ERROR)
        } catch (e: IOException) {
            throw ChatServerException(FailType._UPLOAD_FILE_ERROR)
        } catch (e: Exception) {
            throw ChatServerException(FailType._UNKNOWN_ERROR)
        }
    }

    // S3 파일 제거
    fun deleteFile(fileUrl: String) {
        try {
            // fileName 추출
            val fileName = fileUrl.replace(bucketUrl, "")

            // 파일 존재 여부 확인
            val fileExists = amazonS3Client.doesObjectExist(bucketName, fileName)

            if (!fileExists) {
                throw ChatServerException(FailType._FILE_NOT_FOUND)
            }

            amazonS3Client.deleteObject(bucketName, fileName)
        } catch (e: SdkClientException) {
            throw ChatServerException(FailType._DELETE_FILE_ERROR)
        } catch (e: Exception) {
            throw ChatServerException(FailType._UNKNOWN_ERROR)
        }
    }
}
