package com.zianedu.api.vo;

import lombok.Data;

@Data
public class PaymentVO {

    private int payStatus;

    private int payType;

    private int deliveryStatus;

    private String deliveryNo;

    private String deliveryCompanyName;

    private String payStatusName;

    private String payTypeName;

    private String deliveryStatusName;

    private int deliveryMasterKey;

    private int pricePay;

    private String pricePayName;
}
