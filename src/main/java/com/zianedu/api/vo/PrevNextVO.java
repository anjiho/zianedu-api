package com.zianedu.api.vo;

import lombok.Data;

@Data
public class PrevNextVO {

    private int prevBbsKey;

    private String prevTitle;

    private int prevWriteUserKey;

    private int prevPwd;

    private String prevCreateDate;

    private int nextBbsKey;

    private String nextTitle;

    private int nextWriteUserKey;

    private int nextPwd;

    private String nextCreateDate;
}
