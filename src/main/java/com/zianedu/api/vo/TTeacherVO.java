package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TTeacherVO {
    //강사코드
    private int teacherKey;
    //사용자코드
    private int userKey;
    //리스트이미지
    private String imageList;

    private String imageView;
    //리스트이미지
    private String imageTeacherList;
    //뷰 이미지
    private String imageTeacherView;

    private String imageMainList;

    private String imageContents;
    //인사말
    private String greeting;
    //약력
    private String history;
    //저서
    private String bookWriting;
    //온라인 강좌 정산률
    private int onlinelecCalculateRate;
    //오프라인 강좌 정산률
    private int offlinelecCalculateRate;
    //샘플강의
    private String sampleVodFile;

    private String imageListUrl;

    private String imageTeacherListUrl;

    private String imageTeacherViewUrl;

    private String introduce;
}
