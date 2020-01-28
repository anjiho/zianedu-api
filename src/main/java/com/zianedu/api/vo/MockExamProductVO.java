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
    private String acceptStartDate;

    private String acceptEndDate;
    //시험응시 기간
    private String stareDate;

    private String endDate;
    //응시직렬
    private String className;
    //응시과목
    private String subjectName;

    private int isComplete;

    private String classCtgName;
    // 응시하기 버튼 조건(0: 응시마감, 1:응시가능, 2:응시완료)
    private int acceptType;

    // 시험지 파일명
    private String printQuestionFileUrl;
    // 해설지 파일명
    private String printCommentaryFileUrl;

}
