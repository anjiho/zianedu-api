package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TOrderLecCurriVO {

    private Long jCurriKey;

    private int jLecKey;

    private int curriKey;

    private int time;

    public TOrderLecCurriVO(){}

    public TOrderLecCurriVO(int jLecKey, int curriKey, int time) {
        this.jLecKey = jLecKey;
        this.curriKey = curriKey;
        this.time = time;
    }
}
