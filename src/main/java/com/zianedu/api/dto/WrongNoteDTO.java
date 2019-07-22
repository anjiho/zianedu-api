package com.zianedu.api.dto;

import com.zianedu.api.vo.ExamWrongAnswerVO;
import com.zianedu.api.vo.TExamUserVO;
import lombok.Data;

import java.util.List;

@Data
public class WrongNoteDTO {

    private TExamUserVO examHeaderInfo;

    private String subjectNameList;

    private String subjectName;

    private List<ExamWrongAnswerVO> resultList;
}
