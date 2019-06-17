package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class AcademyLectureListDetailVO {

    private int gKey;

    private String goodsName;

    private String lectureDate;

    private int priceKey;

    private String sellPrice;

    private String month;

    private int kind;

    private List<LectureBookVO> lectureBook;

}
