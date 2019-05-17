package com.zianedu.api.define.datasource;

public enum GoodsKindType {

    PC(100, "PC 전용"),
    MOBILE(101, "스마트기기 전용");

    int goodsKindKey;

    String goodsKindName;

    GoodsKindType(int goodsKindKey, String goodsKindName) {
        this.goodsKindKey = goodsKindKey;
        this.goodsKindName = goodsKindName;
    }

    public static String getGoodsKindName(int goodsKindKey) {
        for (GoodsKindType goodsKindType : GoodsKindType.values()) {
            if (goodsKindKey == goodsKindType.goodsKindKey) {
                return goodsKindType.goodsKindName;
            }
        }
        return null;
    }

}
