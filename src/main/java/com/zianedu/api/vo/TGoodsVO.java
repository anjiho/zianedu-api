package com.zianedu.api.vo;

import com.zianedu.api.utils.Util;
import lombok.Data;

@Data
public class TGoodsVO {

    private int gKey;

    private int cKey = 100;

    private int cpKey;

    private String name;

    private String indate;

    private String sellstartdate;
    //1:동영상(온라인강좌), 3:책
    private int type;

    private int isShow;

    private int isSell;

    private int isFree;

    private int isNodc;

    private String imageList;

    private String imageView;

    private int emphasis;

    private String tags;

    private String summary;

    private String description;

    private int calculateRate;

    private int isFreebieDeliveryFree;

    private int isQuickDelivery;

    private int goodsId;

    private String goodsTypeName;

    public TGoodsVO() {}

    public TGoodsVO(int cpKey, String name, String inDate, String sellStartDate, int type, int isShow, int isSell, int isFree,
                    int isNodc, String imageList, String imageView, int emphasis, String tags, String summary, String description,
                    int calculateRate, int isFreebieDeliveryFree, int isQuickDelivery, int goodsId) {
        this.cpKey = cpKey;
        this.name = name;
        this.indate = inDate;
        this.sellstartdate = sellStartDate;
        this.type = type;
        this.isShow = isShow;
        this.isSell = isSell;
        this.isFree = isFree;
        this.isNodc = isNodc;
        this.imageList = imageList;
        this.imageView = imageView;
        this.emphasis = emphasis;
        this.tags = Util.isNullValue(tags, "");
        this.summary = Util.isNullValue(summary, "");
        this.description = description;
        this.calculateRate = calculateRate;
        this.isFreebieDeliveryFree = isFreebieDeliveryFree;
        this.isQuickDelivery = isQuickDelivery;
        this.goodsId = goodsId;

    }
}
