package com.zianedu.api.vo;

import lombok.Data;

@Data
public class StaticsGraphVO {

    private String[] categoryName;

    private Integer[] categoryTopTenData;

    private Integer[] categoryTopThirtyData;

    private double[] categoryMyData;

    private Integer[] categoryStaticsData;

    public StaticsGraphVO() {}

    public StaticsGraphVO(String[] categoryName, Integer[] categoryTopTenData, Integer[] categoryTopThirtyData, double[] categoryMyData) {
        this.categoryName = categoryName;
        this.categoryTopTenData = categoryTopTenData;
        this.categoryTopThirtyData = categoryTopThirtyData;
        this.categoryMyData = categoryMyData;
    }

    public StaticsGraphVO(Integer[] categoryTopTenData, Integer[] categoryStaticsData, double[] categoryMyData) {
        this.categoryTopTenData = categoryTopTenData;
        this.categoryStaticsData = categoryStaticsData;
        this.categoryMyData = categoryMyData;
    }

}
