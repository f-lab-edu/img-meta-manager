package com.intelligent.imagetagmanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class WorkQueueData {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "work_status")
    private String work_status;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime created_date;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime last_modified_date;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageData imageData;
}
