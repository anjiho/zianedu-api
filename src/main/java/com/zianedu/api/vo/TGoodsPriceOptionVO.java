package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TGoodsPriceOptionVO {

    private int priceKey;

    private int gKey;

    private int kind;

    private int price;

    private int sellPrice;

    private int point;

    private int extendPercent;

    private String discountPercent;
}
