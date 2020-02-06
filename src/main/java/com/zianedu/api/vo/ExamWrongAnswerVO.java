package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class ExamWrongAnswerVO {

    private int num;

    private int examQuestionUserKey;

    private int examQuesBankKey;

    private int bankSubjectQuesLinkKey;

    private String questionImage;

    private String commentaryImage;

    private String review;

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

    private int isInterest;

    private int score;

    private String commentaryUrl;

    private String theoryLearningUrl;

}
