package com.zianedu.api.vo;

import lombok.Data;

@Data
public class CommunityListVO {

    private int bbsKey;

    private String indate;

    private String createDate;

    private String title;

    private int writeUserKey;

    private int readCount;

    private int level1;

    private int commentCount;

    private String userName;

    private String pwd;

    private boolean isNew;

    private String fileUrl;

    private int isNotice;
}
