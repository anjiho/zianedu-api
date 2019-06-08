package com.zianedu.api.vo;

import lombok.Data;

@Data
public class ReferenceRoomVO {

    private int num;

    private int bbsKey;

    private int bbsParentKey;

    private String createDate;

    private String userName;

    private String title;

    private int readCount;

    private String pwd;

    private int writeUserKey;

    private int commentCount;

    private int level;

    private String indate;

    private boolean isNew;

}
