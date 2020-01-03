package com.zianedu.api.vo;

import lombok.Data;

@Data
public class ZianPassEndListVO {

    private String goodsName;

    private int jGKey;

    private int jKey;

    private int gKey;

    private int priceKey;

    private String startDt;

    private String endDt;

    private String retakeYn;
}
