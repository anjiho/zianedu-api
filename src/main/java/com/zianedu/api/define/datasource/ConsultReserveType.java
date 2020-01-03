package com.zianedu.api.define.datasource;

public enum ConsultReserveType {

    VISIT(1, "방문상담예약"),
    TELEPHONE(2, "전화상담예약");

    int consultReserveTypeKey;

    String consultReserveTypeName;

    ConsultReserveType(int consultReserveTypeKey, String consultReserveTypeName) {
        this.consultReserveTypeKey = consultReserveTypeKey;
        this.consultReserveTypeName = consultReserveTypeName;
    }

    public static String getConsultReserveTypeName(int consultReserveTypeKey) {
        for (ConsultReserveType reserveType : ConsultReserveType.values()) {
            if (consultReserveTypeKey == reserveType.consultReserveTypeKey) {
                return reserveType.consultReserveTypeName;
            }
        }
        return null;
    }
}
