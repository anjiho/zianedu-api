package com.zianedu.api.vo;

import lombok.Data;

@Data
public class OrderGoodsDetailVO {

    private int sellPrice;

    private int cnt;

    private int type;

    private int kind;

    private int pmType;

    private int extendDay;

    private String goodsName;

    private String productType;

    private String priceName;
}
