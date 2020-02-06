package com.zianedu.api.vo;

import lombok.Data;

@Data
public class ExamLogVO {

    private int examKey;

    private String goodsName;

    private int examUserKey;

    private String examDate;

    private int isGichul;

    private int isRealFree;

    private int isOnOff;

    private String examType;
}
