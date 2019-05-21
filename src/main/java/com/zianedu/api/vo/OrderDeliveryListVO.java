package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderDeliveryListVO {

    private int jKey;

    private int price;

    private int pricePay;

    private String indate;

    private int payStatus;

    private String jId;

    private int isCancelRequest;

    private List<String> goodsNameList;

    private String payStatusName;

    private String priceName;
}
