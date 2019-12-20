package com.zianedu.api.vo;

import lombok.Data;

@Data
public class OrderDetailInfoVO {

    private int userKey;

    private String goodsName;

    private int jKey;

    private int type;

    private int jgKey;

    private String typeName;

    private int payType;

    private int jLecKey;

    private boolean isReviewYn = false;
}
