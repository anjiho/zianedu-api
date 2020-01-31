package com.zianedu.api.vo;

import lombok.Data;

@Data
public class LineGraphDataVO {

    private String name;

    private double[] data;

    public LineGraphDataVO(){}

    public LineGraphDataVO(String name, double[] data) {
        this.name = name;
        this.data = data;
    }
}
