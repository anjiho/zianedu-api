package com.zianedu.api.vo;

import lombok.Data;

@Data
public class GoodsListVO {

    private int gKey;

    private int lecKey;

    private String ctgName;

    private String goodsName;

    private int emphasis;

    private int limitCount;

    private int limitDay;

    private String lowVideo;

    private String highVideo;

    private int curriKey;

    private String discountPercent;
}
