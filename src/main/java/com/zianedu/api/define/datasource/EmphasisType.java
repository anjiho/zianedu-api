package com.zianedu.api.define.datasource;

public enum EmphasisType {

    NONE(0, ""),
    BEST(1, "BEST"),
    NEW(2, "NEW");

    int emphasisKey;

    String emphasisStr;

    EmphasisType(int emphasisKey, String emphasisStr) {
        this.emphasisKey = emphasisKey;
        this.emphasisStr = emphasisStr;
    }

    public static String getEmphasisStr(int emphasisKey) {
        for (EmphasisType emphasisType : EmphasisType.values()) {
            if (emphasisKey == emphasisType.emphasisKey) {
                return emphasisType.emphasisStr;
            }
        }
        return null;
    }
}
