package com.zianedu.api.define.datasource;

public enum FaqCtgType {
    MEMBER(317, "회원관련"),
    LECTURE(318, "수강신청관련"),
    VIDEO(319, "동영상관련"),
    MOBILE(320, "모바일관련"),
    PAY(321, "결제·환불관련"),
    BOOK(322, "교재·배송교환"),
    ETC(323, "기타문의");

    int faqCtgTypeKey;

    String faqCtgTypeName;

    FaqCtgType(int faqCtgTypeKey, String faqCtgTypeName) {
        this.faqCtgTypeKey = faqCtgTypeKey;
        this.faqCtgTypeName = faqCtgTypeName;
    }

    public static String getFaqCtgName(int faqCtgTypeKey) {
        for (FaqCtgType faqCtgType : FaqCtgType.values()) {
            if (faqCtgType.faqCtgTypeKey == faqCtgTypeKey) {
                return faqCtgType.faqCtgTypeName;
            }
        }
        return null;
    }
}
