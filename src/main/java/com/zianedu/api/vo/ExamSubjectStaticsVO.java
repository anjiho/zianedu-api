package com.zianedu.api.vo;

import lombok.Data;

@Data
public class ExamSubjectStaticsVO {

    private String subjectName;

    private int questionCnt;

    private int answerCnt;

    private int answerScore;

    private int totalAnswerCnt;

    private int totalAnswerScore = 100;

    private int examSbjUserKey;

    private int examUserKey;

    private int examSbjKey;

    private int examKey;

    private int bankSubjectExamLinkKey;

    private int examQuesBankSubjectKey;

    private int userKey;

    private int requiredType;

    private int isSelected;

    private int examQuestionBankSubjectKey;

    private int subjectKey;

    private String name;

    private int userGrade;
}
