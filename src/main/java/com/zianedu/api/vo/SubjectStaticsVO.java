package com.zianedu.api.vo;

import lombok.Data;

@Data
public class SubjectStaticsVO {

    private String subjectName;

    private int tenPercentScore;

    private int thirtyPercentScore;

    private int myScore;

    private double totalScore;

    public SubjectStaticsVO(){}

    public SubjectStaticsVO(String subjectName, int tenPercentScore, int thirtyPercentScore, int myScore, double totalScore) {
        this.subjectName = subjectName;
        this.tenPercentScore = tenPercentScore;
        this.thirtyPercentScore = thirtyPercentScore;
        this.myScore = myScore;
        this.totalScore = totalScore;
    }
}
