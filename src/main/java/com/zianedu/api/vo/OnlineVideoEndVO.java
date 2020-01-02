package com.zianedu.api.vo;

import lombok.Data;

@Data
public class OnlineVideoEndVO {

    private int jKey;

    private int jGKey;

    private int GKey;

    private int limitDay;

    private int pauseTotalDay;

    private String startDate;

    private String endDate;

    private String ctgName;

    private String name;

    private int kind;

    private String kindName;

    private int jLecKey;

    private int pauseCnt;

    private int pauseDay;

    private int extendDay;

    private String extendDayName;

    private int jPmKey;

    private int priceKey;

    private String limitDate;

    private String retakeYn;

}
