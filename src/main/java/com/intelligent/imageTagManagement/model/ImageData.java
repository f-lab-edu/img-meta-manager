package com.intelligent.imageTagManagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ImageData {

    @Id
    @UuidGenerator
    private String uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = true)
    private String fileLocation;

    @ElementCollection
    @CollectionTable(name = "metadatas", joinColumns = @JoinColumn(name = "image_id"))
    @MapKeyColumn(name = "metadata_key")
    @Column(name = "metadata_value")
    private Map<String, String> metadata;


}
