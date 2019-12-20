package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderDeliveryListVO {

    private int jKey;

    private String jId;

    private int price;

    private String sellPriceName;

    private String indate;

    private int payStatus;

    private int isCancelRequest;

    private List<String> goodsNameList;

    private String payStatusName;

    private String priceName;

    private String orderGoodsName;

    private int deliveryStatus;

    private String deliveryStatusName;

    private int orderGoodsCount;
}
