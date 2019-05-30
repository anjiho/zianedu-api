package com.zianedu.api.vo;

import lombok.Data;

@Data
public class GoodsReviewVO {

    private int gReviewKey;

    private String title;

    private int point;

    private String name;

    private String indate;

    private String contents;
}
