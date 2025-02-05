package org.example.eduapp.aws;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLConnection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    public String bucketName = "bucketName";
    public String endpoint = "endpoint";
    public String accessKey = "accessKey";
    public String secretKey = "secretKey";

    @Override
    public String uploadFile(byte[] bytes) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();
            String fileName = UUID.randomUUID() + "_";

            String mimeType = detectMimeType(bytes);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            File file = new File(fileName);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(bytes);
                try (InputStream imageStream = new FileInputStream(file)) {
                    PutObjectArgs data = PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(imageStream, file.length(), -1)
                            .contentType(mimeType)
                            .build();
                    minioClient.putObject(data);
                    System.out.println("Image uploaded successfully to Minio!");

                    String imageUrl = minioClient.getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder()
                                    .bucket(bucketName)
                                    .object(fileName)
                                    .method(io.minio.http.Method.GET)
                                    .build());
                    System.out.println("Image URL: " + imageUrl);
                    return imageUrl;
                }
            } catch (IOException e) {
                return e.getMessage();
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String detectMimeType(byte[] bytes) {
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            return URLConnection.guessContentTypeFromStream(is);
        } catch (Exception e) {
            System.err.println("MIME turini aniqlab bo'lmadi: " + e.getMessage());
            return null;
        }
    }
}
