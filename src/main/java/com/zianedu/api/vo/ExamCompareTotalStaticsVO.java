package com.zianedu.api.vo;

import lombok.Data;

@Data
public class ExamCompareTotalStaticsVO {

    private String subjectName;

    private int mySubjectScore;

    private double totalSubjectScore;

    private String totalSubjectScoreName;

    public ExamCompareTotalStaticsVO(){}

    public ExamCompareTotalStaticsVO(String subjectName, int mySubjectScore, int subjectStaticsSum, int totalAnswerCnt) {
        this.subjectName = subjectName;
        this.mySubjectScore = mySubjectScore;
        this.totalSubjectScore =  (subjectStaticsSum / totalAnswerCnt);
    }
}
