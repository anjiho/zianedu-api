package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class SpecialPackageLectureVO {

    private int gKey;

    private String stepName;

    private String imageList;

    private int emphasis;

    private String emphasisName;

    private String name;

    private int limitDay;

    private int lectureCount;

    private String discountPercent;

    private List<TLecCurriVO> lectureList;
}
