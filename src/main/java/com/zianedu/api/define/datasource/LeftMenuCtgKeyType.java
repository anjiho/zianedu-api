package com.zianedu.api.define.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum LeftMenuCtgKeyType {
    //행정직학원 > 학원수강신청 > 좌측 메뉴
    PUBLIC_ACADEMY_LEFT_MENU(335, "행정직학원 > 학원수강신청 > 좌측 메뉴"),
    PUBLIC_ACADEMY_EXAM_LEFT_MENU(336, "행정직학원 > 모의고사 > 좌측 메뉴"),
    PUBLIC_ACADEMY_TEACHER_LEFT_MENU(337, "행정직학원 > 교수소개 > 좌측 과목 메뉴"),
    PUBLIC_ACADEMY_NOTICE_LEFT_MENU(338, "행정직학원 > 공지사항 > 좌측 과목 메뉴"),

    PUBLIC_VOD_LECTURE_LEFT_MENU(234, "행정직동영상 > 동영상강좌 > 죄측 과목 메뉴"),
    PUBLIC_VOD_ZIANPASS_LEFT_MENU(239, "행정직동영상 > 365지안패스 > 죄측 메뉴"),
    PUBLIC_VOD_FREE_LECTURE_LEFT_MENU(4446, "행정직동영상 > 무료강좌 > 죄측 과목 메뉴"),
    PUBLIC_VOD_PACKAGE_LEFT_MENU(240, "행정직동영상 > 패키지 > 죄측 과목 메뉴"),
    PUBLIC_VOD_EXAM_LEFT_MENU(241, "행정직동영상 > 모의고사 > 죄측 과목 메뉴"),
    PUBLIC_VOD_TEACHER_LEFT_MENU(242, "행정직동영상 > 교수소개 > 죄측 과목 메뉴"),

    TECH_ACADEMY_LEFT_MENU(394, "기술직학원 > 학원수강신청 > 좌측 메뉴"),
    TECH_ACADEMY_YEAR_LEFT_MENU(762, "기술직학원 > 연간관리반 > 좌측 메뉴"),
    TECH_ACADEMY_EXAM_LEFT_MENU(395, "기술직학원 > 모의고사 > 좌측 메뉴"),
    TECH_ACADEMY_TEACHER_LEFT_MENU(396, "기술직학원 > 교수소개 > 좌측 메뉴"),

    TECH_VOD_LECTURE_LEFT_MENU(453, "기술직동영상 > 동영상강좌 > 죄측 과목 메뉴"),
    TECH_VOD_ZIANPASS_LEFT_MENU(454, "기술직동영상 > 지안패스 > 죄측 메뉴"),
    TECH_VOD_FREE_LECTURE_LEFT_MENU(4447, "기술직동영상 > 무료강좌 > 죄측 과목 메뉴"),
    TECH_VOD_PACKAGE_LEFT_MENU(455, "기술직동영상 > 패키지 > 죄측 과목 메뉴"),
    TECH_VOD_EXAM_LEFT_MENU(456, "기술직동영상 > 모의고사 > 죄측 과목 메뉴"),
    TECH_VOD_TEACHER_LEFT_MENU(457, "기술직동영상 > 교수소개 > 죄측 과목 메뉴"),

    POST_ACADEMY_LEFT_MENU(518, "계리직학원 > 학원수강신청 > 좌측 메뉴"),
    POST_ACADEMY_YEAR_LEFT_MENU(763, "계리직학원 > 연간관리반 > 좌측 메뉴"),
    POST_ACADEMY_EXAM_LEFT_MENU(519, "계리직학원 > 모의고사 > 좌측 메뉴"),
    POST_ACADEMY_TEACHER_LEFT_MENU(523, "계리직학원 > 교수소개 > 좌측 메뉴"),

    POST_VOD_LECTURE_LEFT_MENU(585, "계리직동영상 > 동영상강좌 > 죄측 과목 메뉴"),
    POST_VOD_ZIANPASS_LEFT_MENU(587, "계리직동영상 > 지안패스 > 죄측 메뉴"),
    POST_VOD_FREE_LECTURE_LEFT_MENU(4448, "계리직동영상 > 무료강좌 > 죄측 과목 메뉴"),
    POST_VOD_PACKAGE_LEFT_MENU(455, "계리직동영상 > 패키지 > 죄측 과목 메뉴"),
    POST_VOD_EXAM_LEFT_MENU(586, "계리직동영상 > 모의고사 > 죄측 과목 메뉴"),
    POST_VOD_TEACHER_LEFT_MENU(591, "계리직동영상 > 교수소개 > 죄측 과목 메뉴"),

    BOOK_COMMON_SUBJECT_LEFT_MENU(689, "온라인서점 > 공통과목 > 좌측메뉴"),
    BOOK_PUBLIC_SUBJECT_LEFT_MENU(690, "온라인서점 > 행정직군 > 좌측메뉴"),
    BOOK_TECH_SUBJECT_LEFT_MENU(691, "온라인서점 > 기술직군 > 좌측메뉴"),
    BOOK_POST_SUBJECT_LEFT_MENU(692, "온라인서점 > 계리직 > 좌측메뉴"),
    BOOK_CERTIFICATE_SUBJECT_LEFT_MENU(693, "온라인서점 > 자격증 > 좌측메뉴")
    ;

    int ctgKey;

    String menuDefine;

    LeftMenuCtgKeyType(int ctgKey, String menuDefine) {
        this.ctgKey = ctgKey;
        this.menuDefine = menuDefine;
    }

    public static List<HashMap<String, String>> getLeftMenuCtgKeyList() {
        List<HashMap<String, String>>list = new ArrayList<>();
        for (LeftMenuCtgKeyType leftMenuCtgKeyType : LeftMenuCtgKeyType.values()) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("ctgKey", String.valueOf(leftMenuCtgKeyType.ctgKey));
            hashMap.put("menuDefine", String.valueOf(leftMenuCtgKeyType.menuDefine));

            list.add(hashMap);
        }
        return list;
    }
}
