package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class TeacherHomeLectureListVO {

    private int gKey;

    private String ctgName;

    private int emphasis;

    private String emphasisName;

    private int price;

    private String sellPrice;

    private String discountPercent;

    private String goodsName;

    private int lecCount;

    private int limitDay;

    private String lowVideo;

    private String highVideo;

    private int curriKey;

    private String pcSellPriceName;

    private String mobileSellPriceName;

    private String pcMobileSellPriceName;

    private String imageTeacherList;

    private String teacherImageUrl;

    private int lecKey;

    private String discountPercentName;

    private List<TGoodsPriceOptionVO> videoLectureKindList;

    private List<LectureBookVO> teacherLectureBook;

    private List<TLecCurriVO> lectureCurriList;
}
