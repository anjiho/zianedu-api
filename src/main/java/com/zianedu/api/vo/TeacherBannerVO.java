package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TeacherBannerVO {

    private String teacherTitle;

    private String targetUrl;

    private String teacherName;

    private int subjectCode;

    private int teacherKey;

    private int isNewPageOpen;

    private String teacherImage;

    private String subjectName;

    private String teacherImageUrl;

    private int parentMnk;

    private int reqKey;

    private TTeacherVO teacherInfo;

}
