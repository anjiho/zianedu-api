package com.zianedu.api.vo;

import lombok.Data;

@Data
public class OnlineLectureDetailVO {

    private int jKey;

    private int jGKey;

    private int gKey;

    private String lectureDate;

    private int lectureCnt;

    private int status;

    private String teacherName;

    private String lectureName;

    private String imageTeacherList;

    private String description;

    private int jLecKey;

    private String progressRate;

    private String teacherImgUrl;

    private String lectureStatusName;

    private String lectureCntName;
}
