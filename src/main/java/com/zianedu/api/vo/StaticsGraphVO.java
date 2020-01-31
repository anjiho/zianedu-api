package com.zianedu.api.vo;

import lombok.Data;

@Data
public class StaticsGraphVO {

    private String[] categoryName;

    private double[] categoryTopTenData;

    private double[] categoryTopThirtyData;

    private double[] categoryMyData;

    private double[] categoryStaticsData;

    public StaticsGraphVO() {}

    public StaticsGraphVO(String[] categoryName, double[] categoryTopTenData, double[] categoryTopThirtyData, double[] categoryMyData) {
        this.categoryName = categoryName;
        this.categoryTopTenData = categoryTopTenData;
        this.categoryTopThirtyData = categoryTopThirtyData;
        this.categoryMyData = categoryMyData;
    }

    public StaticsGraphVO(double[] categoryTopTenData, double[] categoryStaticsData, double[] categoryMyData) {
        this.categoryTopTenData = categoryTopTenData;
        this.categoryStaticsData = categoryStaticsData;
        this.categoryMyData = categoryMyData;
    }

}
