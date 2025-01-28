package com.asyncgate.chat_server.utility

import com.amazonaws.SdkClientException

@Configuration
@RequiredArgsConstructor
class S3Util {
    private val amazonS3Client: AmazonS3Client? = null

    @Value("\${cloud.aws.s3.bucket}")
    private val bucketName: String? = null

    @Value("\${cloud.aws.s3.url}")
    private val bucketUrl: String? = null

    // S3 이미지 업로드
    fun uploadFile(file: MultipartFile, id: String): String {
        try {
            val fileName = id + "/" + UUID.randomUUID().toString()
            val objectMetadata: ObjectMetadata = ObjectMetadata()
            objectMetadata.setContentLength(file.getSize())
            objectMetadata.setContentType(file.getContentType())

            val putObjectRequest: PutObjectRequest =
                PutObjectRequest(bucketName, fileName, file.getInputStream(), objectMetadata)

            amazonS3Client.putObject(putObjectRequest)

            return bucketUrl + fileName
        } catch (e: SdkClientException) {
            throw UserServerException(FailType.UPLOAD_FILE_ERROR)
        } catch (e: IOException) {
            throw UserServerException(FailType.UPLOAD_FILE_ERROR)
        }
    }

    // S3 파일 제거
    fun deleteFile(fileUrl: String) {
        try {
            // 공백 제거
            val fileName: String = fileUrl.replace(bucketUrl, "")

            amazonS3Client.deleteObject(bucketName, fileName)
        } catch (e: SdkClientException) {
            throw UserServerException(FailType.DELETE_FILE_ERROR)
        }
    }
}
