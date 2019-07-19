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
    //유형별 정답률
    private List<ScoreRateGraphVO> scoreRateByTypeInfo;
    //패턴별 정답률
    private List<ScoreRateGraphVO> scoreRateByPatternInfo;
    //대단원별 정답률
    private List<ScoreRateGraphVO> scoreRateByUnitInfo;

    private List<ScoreRateDTO> scoreRateInfo;

    public AchievementManagementDTO() {}

    public AchievementManagementDTO(AchievementTopInfoVO achievementTopInfo, List<ExamSubjectStaticsVO> examSubjectStaticsInfo,
                                    ExamSubjectTotalVO examSubjectTotalInfo, List<ExamCompareTotalStaticsVO> examCompareTotalStaticsInfo,
                                    List<SubjectStaticsVO> subjectStaticsInfo, StaticsGraphVO subjectStaticsGraphInfo,
                                    StaticsGraphVO compareScoreGraphInfo, List<ScoreRateGraphVO> scoreRateByTypeInfo,
                                    List<ScoreRateGraphVO> scoreRateByPatternInfo, List<ScoreRateGraphVO> scoreRateByUnitInfo,
                                    List<ScoreRateDTO> scoreRateInfo) {
        this.achievementTopInfo = achievementTopInfo;
        this.examSubjectStaticsInfo = examSubjectStaticsInfo;
        this.examSubjectTotalInfo = examSubjectTotalInfo;
        this.examCompareTotalStaticsInfo = examCompareTotalStaticsInfo;
        this.subjectStaticsInfo = subjectStaticsInfo;
        this.subjectStaticsGraphInfo = subjectStaticsGraphInfo;
        this.compareScoreGraphInfo = compareScoreGraphInfo;
        this.scoreRateByTypeInfo = scoreRateByTypeInfo;
        this.scoreRateByPatternInfo = scoreRateByPatternInfo;
        this.scoreRateByUnitInfo = scoreRateByUnitInfo;
        this.scoreRateInfo = scoreRateInfo;
    }

}
