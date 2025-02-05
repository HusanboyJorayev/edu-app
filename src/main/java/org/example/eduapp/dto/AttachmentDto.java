package org.example.eduapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentDto {
    private Long id;
    private String originName;
    private Long size;
    private String type;
    private String contentType;
    private String cloudPath;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
