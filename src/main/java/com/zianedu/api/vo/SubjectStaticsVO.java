package com.zianedu.api.vo;

import lombok.Data;

@Data
public class SubjectStaticsVO {

    private String subjectName;

    private double tenPercentScore;

    private double thirtyPercentScore;

    private int myScore;

    private double totalScore;

    public SubjectStaticsVO(){}

    public SubjectStaticsVO(String subjectName, double tenPercentScore, double thirtyPercentScore, int myScore, double totalScore) {
        this.subjectName = subjectName;
        this.tenPercentScore = Double.parseDouble(String.format("%.1f", tenPercentScore));
        this.thirtyPercentScore = Double.parseDouble(String.format("%.1f", thirtyPercentScore));
        this.myScore = myScore;
        this.totalScore = Double.parseDouble(String.format("%.2f", totalScore));
    }
}
