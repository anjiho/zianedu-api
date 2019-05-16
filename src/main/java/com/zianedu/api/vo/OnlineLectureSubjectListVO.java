package com.zianedu.api.vo;

import lombok.Data;

@Data
public class OnlineLectureSubjectListVO {

    private int curriKey;

    private String name;

    private String vodTime;

    private String dataFile;

    private String vodFileLow;//동영상 저화질 파일

    private String vodFileHigh;//동영상 과화질 파일

    private String vodFileMobileLow;//모바일 동영상 저화질 파일

    private String vodFileMobileHigh;//모바일 동영상 과화질 파일

    private int remainTime;

    private int progressRate;

    private String time;

    private String progressRateName;
}
