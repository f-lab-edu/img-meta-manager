package com.intelligent.imagetagmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(name = "metadata_string_value")
    private String stringValue;

    @Column(name = "metadata_number_value")
    private long numberValue;

    @Column(name = "metadata_date_value")
    private LocalDateTime dateValue;

    @Column(name = "data_type", nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageData imageData;

    // getters and setters
}