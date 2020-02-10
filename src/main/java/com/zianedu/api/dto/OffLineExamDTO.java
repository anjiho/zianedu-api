package com.zianedu.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class OffLineExamDTO {

    private String serial;

    private int subjectCount;

    private List<Integer> examNumberList;
}
