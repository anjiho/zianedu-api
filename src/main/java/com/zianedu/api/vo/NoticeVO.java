package com.zianedu.api.vo;

import lombok.Data;

@Data
public class NoticeVO {

    private int idx;

    private int bbsMasterKey;

    private String createDate;

    private String title;

    private int writeUserKey;

    private int isHead;

    private String contents;

    private String filename;


}
