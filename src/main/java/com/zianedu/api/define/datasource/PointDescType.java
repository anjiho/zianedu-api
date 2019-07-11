package com.zianedu.api.define.datasource;

public enum PointDescType {

    MEMBER_REG(0, "회원가입", "의한 마일리지 획득"),
    PRODUCT_BUY_GAIN(1, "상품구매", "의한 마일리지 흭득"),
    PRODUCT_BUY_USE(2, "상품구매", "마일리지 사용"),
    PRODUCT_BUY_ORDER_CANCEL(3, "상품구매 주문취소", "인해 사용한 마일리지 반환"),
    PAYMENT_CANCEL_USE(4, "결제취소", "인해 사용한 마일리지 반환"),
    PAYMENT_CANCEL_GAIN(5, "결제취소", "인해 획득한 마일리지 반환");

    int pointDescTypeKey;

    String pointDescTypeTitle;

    String pointDescTypeContent;

    PointDescType(int pointDescTypeKey, String pointDescTypeTitle, String pointDescTypeContent) {
        this.pointDescTypeKey = pointDescTypeKey;
        this.pointDescTypeTitle = pointDescTypeTitle;
        this.pointDescTypeContent = pointDescTypeContent;
    }

    public int getPointDescTypeKey() {
        return this.pointDescTypeKey;
    }
}
