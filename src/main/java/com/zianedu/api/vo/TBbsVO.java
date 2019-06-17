package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TBbsVO {

    private int bbsKey;

    private int bbsMasterKey;

    private String indate;

    private String title;

    private int writeUserKey;

    private int isNotice;

    private String contents;

    private String filename;

}
