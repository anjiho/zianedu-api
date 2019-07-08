package com.zianedu.api.vo;

import lombok.Data;

@Data
public class OrderGoodsListVO {

    private int priceKey;

    private Long cartKey;

    private int kind;

    private int gKey;

    private int extendDay;

    private int pmType;
}
