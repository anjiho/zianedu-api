package com.zianedu.api.dto;

import com.zianedu.api.vo.ExamListVO;
import com.zianedu.api.vo.TExamUserVO;
import lombok.Data;

import java.util.List;

@Data
public class ExamListDTO {

    private TExamUserVO examHeaderInfo;

    private String subjectName;

    private List<ExamListVO> examInfo;

    public ExamListDTO(){}

    public ExamListDTO(String subjectName, List<ExamListVO> examInfo) {
        this.subjectName = subjectName;
        this.examInfo = examInfo;
    }
}
