package com.zianedu.api.vo;

import lombok.Data;

@Data
public class BoardDetailVO {

    private int bbsKey;

    private String title;

    private String indate;

    private int readCount;

    private String fileName;

    private String contents;

    private String userId;

    private String userName;

    private String fileUrl;

    private String fileDetailName;

    private int ctgKey;
}
