package com.intelligent.imagetagmanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ImageMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "metadata_key")
    private String key;

    @Column(name = "metadata_value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageData imageData;

    // getters and setters
}