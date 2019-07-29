package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TScheduleVO {

    private int scheduleKey;

    private int cKey;

    private int type;

    private String title;

    private String startDate;

    private String endDate;

    private String contents;

    private int color;

    private String link;

    private int dDay;

    public TScheduleVO() {}

    public TScheduleVO(String title, String startDate) {
        this.cKey = 100;
        this.type = 0;
        this.title = title;
        this.startDate = startDate;
        this.endDate = "";
        this.contents = "";
        this.color = 0;
        this.link = "";
    }
}
