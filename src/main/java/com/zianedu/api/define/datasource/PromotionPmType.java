package com.zianedu.api.define.datasource;

public enum PromotionPmType {
    //패키지
    SPECIAL_PACKAGE(1, "특별패키지"),
    //자유패키지
    FREE_PACKAGE(2, "자유패키지"),
    //연간회원제
    YEAR_MEMBER(50, "연간회원제"),
    //연간회원제 페이지
    YEAR_MEMBER_PAGE(51, "연간회원제 페이지"),
    //지안패스
    ZIAN_PASS(100, "지안패스"),
    //지안패스 페이지
    ZIAN_PASS_PAGE(101, "지안패스 페이지")
    ;

    int promotionPmTypekey;

    String promotionPmTypeStr;

    PromotionPmType(int promotionPmTypekey, String promotionPmTypeStr) {
        this.promotionPmTypekey = promotionPmTypekey;
        this.promotionPmTypeStr = promotionPmTypeStr;
    }

    public static int getPromotionPmTypeKey(String promotionPmTypeStr) {
        for (PromotionPmType pmType : PromotionPmType.values()) {
            if (promotionPmTypeStr.equals(pmType.toString())) {
                return pmType.promotionPmTypekey;
            }
        }
        return 0;
    }

    public static String getPromotionPmTypeName(int promotionPmTypekey) {
        for (PromotionPmType pmType : PromotionPmType.values()) {
            if (promotionPmTypekey == pmType.promotionPmTypekey) {
                return pmType.promotionPmTypeStr;
            }
        }
        return null;
    }

    public int getPromotionPmKey() {
        return this.promotionPmTypekey;
    }

    public String getPromotionPmStr() {
        return this.promotionPmTypeStr;
    }
}
