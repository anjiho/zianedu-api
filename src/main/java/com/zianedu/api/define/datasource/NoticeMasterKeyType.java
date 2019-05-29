package com.zianedu.api.define.datasource;

public enum NoticeMasterKeyType {

    PUBLIC_SUBJECT_OPEN(10001, "행정직_개강안내"),
    PUBLIC_ACADEMY_INFO(10007, "행정직_학원소식"),
    PUBLIC_VIDEO_INFO(10009, "행정직_동영상소식"),

    TECH_SUBJECT_OPEN(10026, "기술직_개강안내"),
    TECH_ACADEMY_INFO(10027, "기술직_학원소식"),
    TECH_VIDEO_INFO(10029, "기술직_동영상소식"),

    POST_SUBJECT_OPEN(10041, "계리직_개강안내"),
    POST_ACADEMY_INFO(10042, "계리직_학원소식"),
    POST_VIDEO_INFO(10044, "계리직_동영상소식")
    ;

    int noticeMasterKey;

    String noticeMasterKeyStr;

    NoticeMasterKeyType(int noticeMasterKey, String noticeMasterKeyStr) {
        this.noticeMasterKey = noticeMasterKey;
        this.noticeMasterKeyStr = noticeMasterKeyStr;
    }

    public int getNoticeMasterKey() {
        return this.noticeMasterKey;
    }
}
