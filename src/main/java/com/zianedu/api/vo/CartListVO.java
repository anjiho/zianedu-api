package com.zianedu.api.vo;

import lombok.Data;

@Data
public class CartListVO {

    private Long cartKey;

    private int gKey;

    private int priceKey;

    private String groupId;

    private int kind;

    private int price;

    private int sellPrice;

    private int point;

    private String goodsName;

    private int type;

    private int cnt;

    private int freebieParentKey;

    private int linkPrice;

    private int linkSellPrice;

    private int linkPoint;

    private int extendDay;

    private int isFreebieDeliveryFree;

    private int ctgKey;

    private int pmType;
}
