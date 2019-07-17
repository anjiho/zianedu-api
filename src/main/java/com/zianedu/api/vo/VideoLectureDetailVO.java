package com.zianedu.api.vo;

import lombok.Data;

@Data
public class VideoLectureDetailVO {

    private int gKey;

    private String goodsName;

    private int status;

    private String statusName;

    private int limitDay;

    private int lectureCount;

    private String point;

    private int evaluationPoint;

    private String lowVideo;

    private String highVideo;

    private int curriKey;

    private String imageTeacherList;

    private String teacherName;

    private String description;

    private String teacherImageUrl;
}