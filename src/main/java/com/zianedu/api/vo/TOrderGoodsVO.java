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

    public TOrderGoodsVO(int jKey, int userKey, int gKey, int priceKey, int price, int kind, int examYear,
                         int classGroupCtgKey, int subjectCtgKey, String teacherName, String goodsName) throws Exception {
        this.jKey = jKey;
        this.cKey = 100;
        this.userKey = userKey;
        this.gKey = gKey;
        this.cartKey = 0;
        this.priceKey = priceKey;
        this.price = price;
        this.sellPrice = price;
        this.point = 0;
        this.cnt = 1;
        this.type = 2;
        this.pmType = 0;
        this.kind = kind;
        this.extendDay = -1;
        this.couponIssueKey = 0;
        this.couponDcPrice = 0;
        this.jPmKey = 0;
        this.freebieParentCartKey = 0;
        this.tmp = 0;
        this.ctgKey = 0;
        this.siteName = "";
        this.startDate = Util.plusDate(Util.returnNowDateByYYMMDD(), 7);
        this.examYear = examYear;
        this.classGroupCtgKey = classGroupCtgKey;
        this.subjectCtgKey = subjectCtgKey;
        this.stepCtgKey = 0;
        this.teacherNameList = teacherName;
        this.stsCalcPrice = 0;
        this.stsTeacherList = "";
        this.stsSubjectCtgKey = 0;
        this.stsStepCtgKey = 0;
        this.stsSiteName = "";
        this.stsAffiliationCtgKey = 0;
        this.gName = goodsName;
    }

}
