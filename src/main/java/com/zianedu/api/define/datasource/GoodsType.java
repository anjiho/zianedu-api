package com.zianedu.api.define.datasource;

public enum GoodsType {
    //동영상(온라인강좌)
    VIDEO(1, "온라인강좌"),
    //학원
    ACADEMY(2, "학원강의"),
    //책
    BOOK(3, "도서"),
    //모의고사
    EXAM(4, "모의고사"),
    //패키지
    PACKAGE(5, "패키지"),
    //지안페이지
    ZIAN_PASS_PAGE(5, "지안페이지"),
    //지안패스
    ZIAN_PASS(5, "지안패스"),
    //연간회원제 페이지
    YEAR_MEMBER_PAGE(5, "연간회원제 페이지"),
    //연간회원제
    YEAR_MEMBER(5, "연간회원제")
    ;

    int goodsTypeKey;

    String goodsTypeStr;

    GoodsType(int goodsTypeKey, String goodsTypeStr) {
        this.goodsTypeKey = goodsTypeKey;
        this.goodsTypeStr = goodsTypeStr;
    }

    public static Integer getGoodsTypeKey(String goodsTypeStr) {
        for (GoodsType goodsType : GoodsType.values()) {
            if (goodsTypeStr.equals(goodsType.toString())) {
                return goodsType.goodsTypeKey;
            }
        }
        return 0;
    }

    public static String getGoodsTypeStr(int goodsTypeKey) {
        for (GoodsType goodsType : GoodsType.values()) {
            if (goodsTypeKey == goodsType.goodsTypeKey) {
                return goodsType.goodsTypeStr;
            }
        }
        return null;
    }

    public int getGoodsTypeKey() {
        return this.goodsTypeKey;
    }


}
