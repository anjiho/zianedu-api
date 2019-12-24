package com.zianedu.api.vo;

import lombok.Data;

@Data
public class ReferenceRoomVO implements Comparable<ReferenceRoomVO>{

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

    private String indate2;

    private boolean isNew;

    private int isNotice;

    private String writeUserName;

    @Override
    public int compareTo(ReferenceRoomVO o) {
        if (this.level < o.getLevel()) {
            return -1;
        } else if (this.level > o.getLevel()) {
            return 1;
        }
        return 0;
    }
}
