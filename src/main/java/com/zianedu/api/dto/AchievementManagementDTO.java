package com.zianedu.api.dto;

import com.zianedu.api.vo.AchievementTopInfoVO;
import com.zianedu.api.vo.ExamSubjectStaticsVO;
import lombok.Data;

import java.util.List;

@Data
public class AchievementManagementDTO {
    //상단 시험 정보
    private AchievementTopInfoVO achievementTopInfo;
    //개인성적 종합분석
    private List<ExamSubjectStaticsVO> examSubjectStaticsInfo;

    public AchievementManagementDTO() {}

    public AchievementManagementDTO(AchievementTopInfoVO achievementTopInfo, List<ExamSubjectStaticsVO> examSubjectStaticsInfo) {
        this.achievementTopInfo = achievementTopInfo;
        this.examSubjectStaticsInfo = examSubjectStaticsInfo;
    }

}
