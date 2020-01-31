package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class CompareScoreSubjectGraphVO {
    //과목별 평균 옆 라인 그래프
    private String[] subjectNames;

    private List<LineGraphDataVO> series;

    public CompareScoreSubjectGraphVO(){}

    public CompareScoreSubjectGraphVO(String[] subjectNames, List<LineGraphDataVO> series) {
        this.subjectNames = subjectNames;
        this.series = series;
    }
}
