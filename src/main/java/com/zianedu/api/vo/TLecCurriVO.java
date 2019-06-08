package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TLecCurriVO {

    private int num;

    private int curriKey;//강의 KEY

    private int lecKey;//강좌 Key

    private String name;//강의명

    private String vodFileLow;//동영상 저화질 파일

    private String vodFileHigh;//동영상 과화질 파일

    private String vodFileMobileLow;//모바일 동영상 저화질 파일

    private String vodFileMobileHigh;//모바일 동영상 과화질 파일

    private int vodTime;//강의 시간

    private String dataFile;//자료 파일

    private int dataPage;//데이터 자료 페이지 수

    private int isShow;//노출여부

    private int isSample;//샘플 여부

    private int unitCtgKey = 0;

    private int pos;//정렬

}
