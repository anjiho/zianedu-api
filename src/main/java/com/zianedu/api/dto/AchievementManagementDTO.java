package com.zianedu.api.dto;

import com.zianedu.api.vo.*;
import lombok.Data;

import java.util.List;

@Data
public class AchievementManagementDTO {
    //상단 시험 정보
    private AchievementTopInfoVO achievementTopInfo;
    //개인성적 종합분석 리스트
    private List<ExamSubjectStaticsVO> examSubjectStaticsInfo;

    private ExamSubjectTotalVO examSubjectTotalInfo;

    private List<ExamCompareTotalStaticsVO> examCompareTotalStaticsInfo;

    private int userStaticsScore;

    private int totalStaticsScore;
    //과목별 평균
    private List<SubjectStaticsVO> subjectStaticsInfo;

    private StaticsGraphVO subjectStaticsGraphInfo;

    private StaticsGraphVO compareScoreGraphInfo;

    public AchievementManagementDTO() {}

    public AchievementManagementDTO(AchievementTopInfoVO achievementTopInfo, List<ExamSubjectStaticsVO> examSubjectStaticsInfo,
                                    ExamSubjectTotalVO examSubjectTotalInfo, List<ExamCompareTotalStaticsVO> examCompareTotalStaticsInfo,
                                    List<SubjectStaticsVO> subjectStaticsInfo, StaticsGraphVO subjectStaticsGraphInfo,
                                    StaticsGraphVO compareScoreGraphInfo) {
        this.achievementTopInfo = achievementTopInfo;
        this.examSubjectStaticsInfo = examSubjectStaticsInfo;
        this.examSubjectTotalInfo = examSubjectTotalInfo;
        this.examCompareTotalStaticsInfo = examCompareTotalStaticsInfo;
        this.subjectStaticsInfo = subjectStaticsInfo;
        this.subjectStaticsGraphInfo = subjectStaticsGraphInfo;
        this.compareScoreGraphInfo = compareScoreGraphInfo;
    }

}
