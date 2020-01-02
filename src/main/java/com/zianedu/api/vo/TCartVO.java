package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TCartVO {

    private int cartKey;

    private int cKey;

    private int indate;

    private int userKey;

    private int gKey;

    private int priceKey;

    private String groupId;

    private int isDelete;

    private int isDirect;

    private int isMember;

    private String jId;

    private int cnt;

    private int freebieParentKey;

    private int linkPrice;

    private int linkSellPrice;

    private int linkPoint;

    private int extendDay;

    private int ctgKey;

    private String siteName;

    public TCartVO(){}

    public TCartVO(int userKey, int gKey, int priceKey, int gCount) {
        this.cKey = 100;
        this.userKey = userKey;
        this.gKey = gKey;
        this.priceKey = priceKey;
        this.groupId = "";
        this.isDelete = 0;
        this.isDirect = 0;
        this.isMember = 1;
        this.jId = "";
        this.cnt = gCount;
        this.freebieParentKey = 0;
        this.linkPrice = 0;
        this.linkSellPrice = 0;
        this.linkPoint = 0;
        this.extendDay = -1;
        this.ctgKey = 0;
        this.siteName = "";
    }
    //자유패키지일때 모델링
    public TCartVO(int userKey, int linkPrice, int linkSellPrice) {
        this.cKey = 100;
        this.userKey = userKey;
        this.gKey = 101400;
        this.priceKey = 100924;
        this.groupId = "";
        this.isDelete = 0;
        this.isDirect = 0;
        this.isMember = 1;
        this.jId = "";
        this.cnt = 1;
        this.freebieParentKey = 0;
        this.linkPrice = linkPrice;
        this.linkSellPrice = linkSellPrice;
        this.linkPoint = 0;
        this.extendDay = -1;
        this.ctgKey = 834;
        this.siteName = "";
    }

    public TCartVO(int userKey, int gKey, int priceKey, int gCount, int ctgKey) {
        this.cKey = 100;
        this.userKey = userKey;
        this.gKey = gKey;
        this.priceKey = priceKey;
        this.groupId = "";
        this.isDelete = 0;
        this.isDirect = 0;
        this.isMember = 1;
        this.jId = "";
        this.cnt = gCount;
        this.freebieParentKey = 0;
        this.linkPrice = 0;
        this.linkSellPrice = 0;
        this.linkPoint = 0;
        this.extendDay = -1;
        this.ctgKey = 833;
        this.siteName = "";
    }

    public TCartVO(int userKey, int gKey, int priceKey, int gCount, String extendDay, int price, int sellPrice) {
        this.cKey = 100;
        this.userKey = userKey;
        this.gKey = gKey;
        this.priceKey = priceKey;
        this.groupId = "";
        this.isDelete = 0;
        this.isDirect = 0;
        this.isMember = 1;
        this.jId = "";
        this.cnt = gCount;
        this.freebieParentKey = 0;
        this.linkPrice = price;
        this.linkSellPrice = sellPrice;
        this.linkPoint = 0;
        this.extendDay = Integer.parseInt(extendDay);
        this.ctgKey = 0;
        this.siteName = "";
    }
}
