package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class ScoreRateGraphVO {

    private String ctgName;

    private int problemCnt;

    private int scoreCnt;

    private String subjectName;

    private String[] ctgNames;

    private Integer[] problemCnts;

    private Integer[] scoreCnts;

    public ScoreRateGraphVO(){}

    public ScoreRateGraphVO(String subjectName, String[] ctgNames, Integer[] problemCnts, Integer[] scoreCnts) {
        this.subjectName = subjectName;
        this.ctgNames = ctgNames;
        this.problemCnts = problemCnts;
        this.scoreCnts = scoreCnts;
    }
}
