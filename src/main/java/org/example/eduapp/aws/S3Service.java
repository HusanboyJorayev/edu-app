package org.example.eduapp.aws;

import org.springframework.stereotype.Component;

@Component
public interface S3Service {
    String uploadFile(byte[] bytes);
}
