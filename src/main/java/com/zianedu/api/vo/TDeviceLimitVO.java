package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TDeviceLimitVO {

    private int deviceLimitKey;

    private int cKey;

    private int userKey;

    private String indate;

    private int type;

    private int dataKey;

    private String deviceId;

    private String deviceModel;

    private String osVersion;

    private String appVersion;

    public TDeviceLimitVO(){}

    public TDeviceLimitVO(int userKey, int type, int jGKey, String deviceId) {
        this.cKey = 100;
        this.userKey = userKey;
        this.type = type;
        this.dataKey = jGKey;
        this.deviceId = deviceId;
        this.deviceModel = "";
        this.osVersion = "";
        this.appVersion = "";
    }

    public TDeviceLimitVO(int userKey, int type, int jGKey, String deviceId, String osVersion, String appVersion) {
        this.cKey = 100;
        this.userKey = userKey;
        this.type = type;
        this.dataKey = jGKey;
        this.deviceId = deviceId;
        this.deviceModel = "";
        this.osVersion = osVersion;
        this.appVersion = appVersion;
    }

}
