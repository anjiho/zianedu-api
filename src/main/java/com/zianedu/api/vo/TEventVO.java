package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TEventVO {

    private int idx;

    private String eventTitle;

    private String eventDesc;

    private String eventStartDate;

    private String eventEndDate;

    private String thumbnailPath;

    private String targetUrl;

    private String targetName;

    private String createDate;

    private String eventTerm;

    public TEventVO(){}

    public TEventVO(String eventTitle, String eventDesc, String eventStartDate, String eventEndDate, String thumbnailFileName,
                    String targetUrl, String targetName) {
        this.eventTitle = eventTitle;
        this.eventDesc = eventDesc;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.thumbnailPath = "100/bbs/" + thumbnailFileName;
        this.targetUrl = targetUrl;
        this.targetName = targetName;
    }
}
