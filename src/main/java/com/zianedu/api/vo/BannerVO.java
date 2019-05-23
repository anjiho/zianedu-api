package com.zianedu.api.vo;

import lombok.Data;

@Data
public class BannerVO {

    private String fileUrl;

    private String colorCode;

    private String targetUrl;

    private int type;

    private String fullFileUrl;
}
