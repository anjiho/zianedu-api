package com.zianedu.api.vo;

import lombok.Data;

@Data
public class CouponOfflineInfoVO {

    private int couponOfflineKey;

    private int couponMasterKey;

    private String indate;

    private String code;

    private String isIssue;
}
