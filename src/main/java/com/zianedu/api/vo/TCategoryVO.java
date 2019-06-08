package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TCategoryVO {

    private int ctgKey;

    private int cKey;

    private int parentKey;

    private int pos;

    private String name;

    private String caption;

    private int isStatic;

    private String idString;

    private int roleType;

    private String value;

    private int valueNumber;

    private int resKey;
}
