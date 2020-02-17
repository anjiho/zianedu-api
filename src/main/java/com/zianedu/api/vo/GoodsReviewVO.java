package com.zianedu.api.vo;

import lombok.Data;

@Data
public class GoodsReviewVO {

    private int gReviewKey;

    private String title;

    private int userKey;

    private int point;

    private String name;

    private String indate;

    private String contents;

    private String goodsName;

    private String userName;

    private int teacherKey;

    private int gKey;
}
