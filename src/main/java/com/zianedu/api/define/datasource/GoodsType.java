package com.zianedu.api.define.datasource;

public enum GoodsType {
    //동영상(온라인강좌)
    VIDEO(1, "동영상"),
    //학원
    ACADEMY(2, "학원실강"),
    //책
    BOOK(3, "도서"),
    //모의고사
    EXAM(4, "모의고사"),
    //패키지
    PACKAGE(5, "지안패스/패키지"),
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

    public static String getGoodsTypeStr(int goodsTypeKey, int extendDay) {
        for (GoodsType goodsType : GoodsType.values()) {
            if (goodsTypeKey == goodsType.goodsTypeKey) {
                if (extendDay > -1) {
                    return "재수강";
                } else {
                    return goodsType.goodsTypeStr;
                }
            }
        }
        return null;
    }

    public int getGoodsTypeKey() {
        return this.goodsTypeKey;
    }


}
