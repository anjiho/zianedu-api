package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class LectureApplyAcademyListVO {

    private int stepCtgKey;

    private int ctgKey;

    private int gKey;

    private int subjectCtgKey;

    private String goodsName;

    private String ctgName;

    private String lectureDate;

    private String sellPrice;

    private int priceKey;

    private String month;

    private int kind;

    private String subjectName;

    private String imageView;

    private int emphasis;

    private String emphasisStr;

    private int sellPrice2;

    private int price;

    private String discountPercent;


    private List<TeacherInfoVO> teacherInfoList;

}
