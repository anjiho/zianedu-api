package com.zianedu.api.define.err;

public enum ZianErrCode {

    BAD_REQUEST(400, "Bad request, parameter not accepted"),
    NOT_AUTHENTICATE(401, "Not authenticated"),

    CUSTOM_NOT_FOUNT_USER(900, "CUSTOM :: 사용자를 찾을수 없음"),
    CUSTOM_FAIL_REG_USER(901, "CUSTOM :: 회원가입 실패"),
    CUSTOM_DUPLICATED_USER_ID(902, "CUSTOM :: 아이디 중복"),
    CUSTOM_SHORT_USER_PASSWORD(903, "CUSTOM :: 비밀번호 길이가 4개이하"),
    CUSTOM_DIFFERENT_CURRENT_USER_PASSWORD(904, "CUSTOM :: 현재 비밀번호가 다름"),
    CUSTOM_PAUSE_LIMIT_OVER_VIDEO(905, "CUSTOM :: 강좌 일시정지 횟수 3회 초과"),
    CUSTOM_DATA_SIZE_ZERO(906, "CUSTOM :: 데이터의 길이가 0"),
    CUSTOM_DEVICE_LIMIT_PC(907, "CUSTOM :: PC 기기제한 초기화 초과"),
    CUSTOM_DEVICE_LIMIT_MOBILE(908, "CUSTOM :: MOBILE 기기제한 초기화 초과"),
    CUSTOM_DEVICE_CHANGE_CODE_CHECK_FAIL(909, "CUSTOM :: 기기변경 코드 일치 실패"),
    CUSTOM_DEVICE_CHANGE_CODE_OVER_TIME(910, "CUSTOM :: 기기변경 제한시간 초과")
    ;

    int code;
    String msg;

    ZianErrCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

    public static String getZianErrorMessage(int code) {
        for (ZianErrCode errCode : ZianErrCode.values()) {
            if (code == errCode.code) {
                return errCode.msg();
            }
        }
        return null;
    }


}
