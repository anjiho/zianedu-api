package com.zianedu.api.dto;

import com.zianedu.api.vo.ExamStaticsDetailSubjectVO;
import com.zianedu.api.vo.TExamUserVO;
import lombok.Data;

import java.util.List;

@Data
public class AchievementSubjectDTO {

    private TExamUserVO examHeaderInfo;

    private String subjectNameList;

    private String subjectName;

    private List<ExamStaticsDetailSubjectVO> resultList;
}
