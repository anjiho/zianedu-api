package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TLecVO {

    private int lecKey;

    private int gKey;

    private int teacherKey;

    private int classGroupCtgKey;

    private int subjectCtgKey;

    private int stepCtgKey;

    private int status;

    private String regdate;

    private String startdate;
    //수강일수 (학원등록에서는 무조건 0값)
    private int limitDay;
    //학원강의(선택과목수), 동영상(강좌수)
    private int limitCount;

    private int lecTime;

    private float multiple;

    private int lecDateYear;

    private int lecDateMonth;

    private int examYear;
    //종합반 여부
    private int isPack;

    private int goodsId;

}
