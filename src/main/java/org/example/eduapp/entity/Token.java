package org.example.eduapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "token")
public class Token extends BaseEntity {
    @Column(columnDefinition = "TEXT")
    private String token;
    private String type;

    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
}
