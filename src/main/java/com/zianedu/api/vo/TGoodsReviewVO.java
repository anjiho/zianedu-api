package com.zianedu.api.vo;

import com.zianedu.api.utils.Util;
import lombok.Data;

@Data
public class TGoodsReviewVO {

    private int gReviewKey;

    private int gKey;

    private String indate;

    private int userKey;

    private String title;

    private String contents;

    private int point;

    public TGoodsReviewVO(){}

    public TGoodsReviewVO(int gKey, int userKey, String title, String contents, int point) {
        this.gKey = gKey;
        this.userKey = userKey;
        this.title = Util.isNullValue(title, "");
        this.contents = Util.isNullValue(contents, "");;
        this.point = point;
    }
}
