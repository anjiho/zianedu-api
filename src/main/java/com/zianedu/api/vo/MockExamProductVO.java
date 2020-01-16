package com.zianedu.api.vo;

import lombok.Data;

@Data
public class MockExamProductVO {

    private int gKey;

    private String goodsName;

    private int priceKey;

    private String price;

    private String sellPrice;

    private String isOnOff;

    private int isSell;

    private int onOffKey;

    private int examKey;
    //시험신청 기간
    private String acceptDate;
    //시험응시 기간
    private String stareDate;
    //응시직렬
    private String className;
    //응시과목
    private String subjectName;

}
