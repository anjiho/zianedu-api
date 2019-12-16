package com.zianedu.api.vo;

import lombok.Data;

@Data
public class FreeLectureVO {

    private int gKey;

    private int lecKey;

    private String ctgName;

    private String subjectName;

    private String goodsName;

    private int lecCount;

    private String freeThumbnailImg;

    private String teacherName;
}
