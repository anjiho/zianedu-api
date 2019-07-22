package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class ExamStaticsDetailSubjectVO {

    private int num;

    private int examQuesBankKey;

    private int bankSubjectQuesLinkKey;

    private int answer;

    private int userAnswer;

    private String examLevelName;

    private String answerComment;

    private String stepName;

    private int unitCtgKey;

    private int totalCnt;

    private int totalScoreCnt;

    private int totalWrongCnt;

    private String unitName;

    private String scorePercent;

    private List<ProblemNumberScoreVO> problemScoreList;
}
