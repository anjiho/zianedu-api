package com.zianedu.api.vo;

import lombok.Data;

@Data
public class ZianPassProductListVO {

    private int gKey;

    private int priceKey;

    private String name;

    private String sellPrice;

    private int type;

    private int pmType;

    private String targetUrl;

    private String term;
}
