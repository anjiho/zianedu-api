package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TOrderLecCurriVO {

    private Long jCurriKey;

    private Long jLecKey;

    private Long curriKey;

    private Integer time;

    public TOrderLecCurriVO(){}

    public TOrderLecCurriVO(Long jCurriKey, Long jLecKey, Long curriKey, Integer time) {
        this.jCurriKey = jCurriKey;
        this.jLecKey = jLecKey;
        this.curriKey = curriKey;
        this.time = time;
    }
}
