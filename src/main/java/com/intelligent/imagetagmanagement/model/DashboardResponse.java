package com.intelligent.imagetagmanagement.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {


    private long totalSuccessfulWorkflowCount;
    private long totalFailedWorkflowCount;
    private long todaySuccessfulWorkflowCount;
    private long todayFailedWorkflowCount;

    private long totalImageCount;
    private long todayImageCount;

}
