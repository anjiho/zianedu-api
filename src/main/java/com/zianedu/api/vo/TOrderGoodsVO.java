package com.zianedu.api.vo;

import com.zianedu.api.utils.Util;
import lombok.Data;

@Data
public class TOrderGoodsVO {

    private int jGKey;

    private int jKey;

    private int cKey;

    private int userKey;

    private int gKey;

    private int cartKey;

    private int priceKey;

    private int price;

    private int sellPrice;

    private int point;

    private int cnt;

    private int type;

    private int pmType;

    private int kind;

    private int extendDay;

    private int couponIssueKey;

    private int couponDcPrice;

    private int jPmKey;

    private int freebieParentCartKey;

    private int tmp;

    private int ctgKey;

    private String siteName;

    private String startDate;

    private int examYear;

    private int classGroupCtgKey;

    private int subjectCtgKey;

    private int stepCtgKey;

    private String teacherNameList;

    private float stsCalcPrice;

    private String stsTeacherList;

    private int stsSubjectCtgKey;

    private int stsStepCtgKey;

    private String stsSiteName;

    private int stsAffiliationCtgKey;

    private String gName;

    public TOrderGoodsVO(){}

    public TOrderGoodsVO(int jKey, int userKey, int gKey, int cartKey, int priceKey, int price, int sellPrice, int point,
                         int type, int pmType, int kind, int extendDay, int ctgKey, int examYear, int classGroupCtgKey,
                         int subjectCtgKey, String teacherName, String goodsName) throws Exception {
        this.jKey = jKey;
        this.cKey = 100;
        this.userKey = userKey;
        this.gKey = gKey;
        this.cartKey = cartKey;
        this.priceKey = priceKey;
        this.price = price;
        this.sellPrice = sellPrice;
        this.point = point;
        this.cnt = 1;
        this.type = type;
        this.pmType = pmType;
        this.kind = kind;
        this.extendDay = extendDay;
        this.couponIssueKey = 0;
        this.couponDcPrice = 0;
        this.jPmKey = 0;
        this.freebieParentCartKey = 0;
        this.tmp = 0;
        this.ctgKey = ctgKey;
        this.siteName = "";
        this.startDate = type == 2 ? Util.returnNow() : "";
        this.examYear = examYear;
        this.classGroupCtgKey = classGroupCtgKey;
        this.subjectCtgKey = subjectCtgKey;
        this.stepCtgKey = 0;
        this.teacherNameList = teacherName;
        this.gName = goodsName;
    }

    public TOrderGoodsVO(int jKey, int userKey, int gKey, int cartKey, int priceKey, int price, int sellPrice, int point,
                         int type, int pmType, int kind, int extendDay, int ctgKey, int examYear, int classGroupCtgKey,
                         int subjectCtgKey, String teacherName, String goodsName, int jPmKey) throws Exception {
        this.jKey = jKey;
        this.cKey = 100;
        this.userKey = userKey;
        this.gKey = gKey;
        this.cartKey = cartKey;
        this.priceKey = priceKey;
        this.price = price;
        this.sellPrice = sellPrice;
        this.point = point;
        this.cnt = 1;
        this.type = type;
        this.pmType = pmType;
        this.kind = kind;
        this.extendDay = extendDay;
        this.couponIssueKey = 0;
        this.couponDcPrice = 0;
        this.jPmKey = jPmKey;
        this.freebieParentCartKey = 0;
        this.tmp = 0;
        this.ctgKey = ctgKey;
        this.siteName = "";
        this.startDate = type == 2 ? Util.returnNow() : "";
        this.examYear = examYear;
        this.classGroupCtgKey = classGroupCtgKey;
        this.subjectCtgKey = subjectCtgKey;
        this.stepCtgKey = 0;
        this.teacherNameList = teacherName;
        this.gName = goodsName;
    }

}
