package com.smartinvigilate.smartinvigilate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProctoringLogRequest {
    private String logType;
    private String details;
    private LocalDateTime timestamp;
}
