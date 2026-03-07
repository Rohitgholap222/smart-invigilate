package com.smartinvigilate.smartinvigilate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmitExamRequest {
    private Map<Integer, String> answers; // questionId -> selectedOption
}
