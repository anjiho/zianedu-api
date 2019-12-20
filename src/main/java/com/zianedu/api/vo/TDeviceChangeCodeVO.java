package com.zianedu.api.vo;

import com.zianedu.api.utils.RandomUtil;
import lombok.Data;

@Data
public class TDeviceChangeCodeVO {

    private int idx;

    private int userKey;

    private String requestCode;

    private String requestDate;

    private int applyYn;

    private String deviceType;

    private String sendEmailAddress;

    public TDeviceChangeCodeVO(){}

    public TDeviceChangeCodeVO(int userKey, String requestCode, String deviceType, String sendEmailAddress) {
        this.userKey = userKey;
        this.requestCode = requestCode;
        this.deviceType = deviceType;
        this.sendEmailAddress = sendEmailAddress;
    }
}
