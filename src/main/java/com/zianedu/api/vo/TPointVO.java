package com.zianedu.api.vo;

import com.zianedu.api.utils.Util;
import lombok.Data;

@Data
public class TPointVO {

    private int pointKey;

    private int cKey;

    private String indate;

    private int userKey;

    private int type;

    private int point;

    private int jKey;

    private String jId;

    private int descType;

    private String description;

    public TPointVO() {}

    public TPointVO(int userKey, int type, int point, int jKey, String jId, int descType) {
        this.cKey = 100;
        this.userKey = userKey;
        this.type = type;
        this.point = point;
        this.jKey = jKey;
        this.jId = jId;
        this.descType = descType;
        this.description = descType == 0 ? "회원가입을 축하합니다" : "";
    }
}
