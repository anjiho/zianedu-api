package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class TLinkKeyVO {

    private int linkKey; //0
    // G_KEY, 쿠폰키, 팝업 일때는 카테코리 키값 연동
    private int reqKey; //과목마지막키값
    // T_GOODS.g_key, T_EXAM_MASTER.exam_key, 쿠폰키, 팝업 키
    private int resKey; //teacherkey
    // LinkKeyReqType 정의
    private int reqType; //100
    //모의고사 정보 -> 온/오프라인여부 (2:온라인, 3:오프라인), 300 : 팝업, 400 : 쿠폰, 200 : 강사
    private int resType; //200

    private int pos; //0
    //주교재 여부
    private int valueBit; //0

    private String goodsName;

    private String name;

    private int ctgKey;

    private List<TCategoryVO> categoryList;

    public TLinkKeyVO(){}

    public TLinkKeyVO(int linkKey, int valueBit) {
        this.linkKey = linkKey;
        this.valueBit = valueBit;
    }
}
