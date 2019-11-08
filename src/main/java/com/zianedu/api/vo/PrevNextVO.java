package com.zianedu.api.vo;

import lombok.Data;

@Data
public class PrevNextVO {

    private int prevBbsKey;

    private String prevTitle;

    private int prevWriteUserKey;

    private Integer prevPwd;

    private String prevCreateDate;

    private int nextBbsKey;

    private String nextTitle;

    private int nextWriteUserKey;

    private Integer nextPwd;

    private String nextCreateDate;
}
