package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TOrderLecStartStopLogVO {

    private int idx;

    private int jLecKey;

    private String logType;

    private String createDate;

    public TOrderLecStartStopLogVO() {}

    public TOrderLecStartStopLogVO(int jLecKey, String logType) {
        this.jLecKey = jLecKey;
        this.logType = logType;
    }
}
