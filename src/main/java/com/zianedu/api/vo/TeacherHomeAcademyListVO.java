package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class TeacherHomeAcademyListVO {

    private int gKey;

    private String ctgName;

    private int emphasis;

    private String emphasisName;

    private int price;

    private String discountPercent;

    private String goodsName;

    private int lecCount;

    private int limitDay;

    private int kind;

    private int priceKey;

    private String sellPriceName;

    private String lectureDate;

    private String imageTeacherList;

    private String imageTeacherUrl;

    private int sellPrice;

    private List<LectureBookVO> teacherLectureBook;
}
