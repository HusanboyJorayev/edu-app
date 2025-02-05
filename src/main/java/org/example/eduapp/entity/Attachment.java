package org.example.eduapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
@Table(name = "attachment")
public class Attachment extends BaseEntity {
    @Column(columnDefinition = "TEXT")
    private String originName;
    private Long size;
    private String type;
    private String contentType;
    @Column(columnDefinition = "TEXT")
    private String cloudPath;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
