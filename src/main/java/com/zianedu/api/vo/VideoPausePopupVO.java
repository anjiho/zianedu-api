package com.zianedu.api.vo;

import lombok.Data;

@Data
public class VideoPausePopupVO {

    private int jLecKey;

    private String name;

    private String startDt;

    private String endDt;

    private int remainStopDay;
}
