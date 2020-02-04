package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class ExamInfoDTO {

    private TExamUserVO examHeaderInfo;

    private List<String> subjectNameInfo;
}
