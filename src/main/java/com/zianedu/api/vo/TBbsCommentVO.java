package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TBbsCommentVO {

    private int bbsCommentKey;

    private int bbsKey;

    private int userKey;

    private String indate;

    private String comment_;

    public TBbsCommentVO(){}

    public TBbsCommentVO(int bbsKey, int userKey, String comment) {
        this.bbsKey = bbsKey;
        this.userKey = userKey;
        this.comment_ = comment;
    }
}
