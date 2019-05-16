package com.zianedu.api.define.datasource;

/**
 * T_LEC.STATUS 값 정의
 */
public enum LectureStatusType {

    READY(0, "준비중"),
    PROCEED(1, "진행중"),
    COMPLETE(2, "완강"),
    CLOSE(3, "폐강")
    ;

    int lectureStatus;

    String lectureStatusStr;

    LectureStatusType(int lectureStatus, String lectureStatusStr) {
        this.lectureStatus = lectureStatus;
        this.lectureStatusStr = lectureStatusStr;
    }

    public static String getLectureStatusName(int lectureStatus) {
        for (LectureStatusType lectureStatusType : LectureStatusType.values()) {
            if (lectureStatusType.lectureStatus == lectureStatus) {
                return lectureStatusType.lectureStatusStr;
            }
        }
        return null;
    }

}
