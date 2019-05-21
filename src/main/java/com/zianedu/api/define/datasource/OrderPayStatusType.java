package com.zianedu.api.define.datasource;

public enum OrderPayStatusType {

    EXPECTED(0, "입금예정"),
    WAIT(1, "결제대기"),
    SUCCESS(2, "결제완료"),
    PAY_CANCEL(8, "결제취소"),
    ORDER_CANCEL(9, "주문취소"),
    PAY_FAIL(10, "결제실패")
    ;


    int orderPayStatusTypeKey;

    String orderPayStatusTypeStr;

    OrderPayStatusType(int orderPayStatusTypeKey, String orderPayStatusTypeStr) {
        this.orderPayStatusTypeKey = orderPayStatusTypeKey;
        this.orderPayStatusTypeStr = orderPayStatusTypeStr;
    }

    public static String getOrderPayStatusStr(int payStatusKey) {
        for (OrderPayStatusType payStatusType : OrderPayStatusType.values()) {
            if (payStatusKey == payStatusType.orderPayStatusTypeKey) {
                return payStatusType.orderPayStatusTypeStr;
            }
        }
        return null;
    }

}
