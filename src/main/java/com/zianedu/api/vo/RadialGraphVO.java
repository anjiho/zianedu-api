package com.zianedu.api.vo;

import lombok.Data;

@Data
public class RadialGraphVO {

    private String[] radialSubjectNames;

    private Integer[] radialSubjectScores;

    public RadialGraphVO(){}

    public RadialGraphVO(String[] radialSubjectNames, Integer[] radialSubjectScores) {
        this.radialSubjectNames = radialSubjectNames;
        this.radialSubjectScores = radialSubjectScores;
    }
}
