package com.intelligent.imagetagmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ImageData {

    @PrePersist
    public void prePersist() {
        this.uploadDate = LocalDateTime.now();
    }

    @Id
    @UuidGenerator
    private String uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = true)
    private String fileLocation;

    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadDate;

    @OneToMany(mappedBy = "imageData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageMetaData> metadata;


}
