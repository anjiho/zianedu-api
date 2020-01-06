package com.zianedu.api.define.datasource;

public enum ConsultStatusType {
    PREPARE(1, "상담대기"),
    SUCCESS(2, "상담완료"),
    CANCEL(3, "예약취소");

    int consultStatusKey;

    String consultStatusName;

    ConsultStatusType(int consultStatusKey, String consultStatusName) {
        this.consultStatusKey = consultStatusKey;
        this.consultStatusName = consultStatusName;
    }

    public static String getConsultStatusTypeName(int consultStatusKey) {
        for (ConsultStatusType statusType : ConsultStatusType.values()) {
            if (consultStatusKey == statusType.consultStatusKey) {
                return statusType.consultStatusName;
            }
        }
        return null;
    }
}
