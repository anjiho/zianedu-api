package com.zianedu.api.vo;

import lombok.Data;

@Data
public class NoticeListVO {

    private int bbsKey;

    private String createDate;

    private String title;

    private int commentCnt;

    private String indate;

    private boolean isNew;

    private int isNotice;

    private String fileName;

    private String contents;
}
