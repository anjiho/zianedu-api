package com.zianedu.api.vo;

import lombok.Data;

@Data
public class CouponListVO {

    private int couponMasterKey;

    private int couponIssueKey;

    private String indate;

    private String description;

    private int dcType;

    private int dcValue;

    private int periodType;

    private int limitDay;

    private String startDate;

    private String endDate;

    private String name;

    private int device;

    private String useDate;
}
