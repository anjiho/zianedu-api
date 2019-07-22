package com.zianedu.api.dto;

import com.zianedu.api.vo.ExamStaticsDetailSubjectVO;
import lombok.Data;

import java.util.List;

@Data
public class AchievementSubjectDTO {

    private String subjectName;

    private List<ExamStaticsDetailSubjectVO> resultList;
}
