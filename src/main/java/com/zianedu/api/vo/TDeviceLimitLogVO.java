package com.zianedu.api.vo;

import com.zianedu.api.utils.Util;
import lombok.Data;

@Data
public class TDeviceLimitLogVO {

    private int deviceLogKey;

    private int deviceLimitKey;

    private int cKey;

    private int userKey;

    private String indate;

    private String deleteDate;

    private int type;

    private int dataKey;

    private String deviceId;

    private String deviceModel;

    private String osVersion;

    private String appVersion;

    public TDeviceLimitLogVO(){}

    public TDeviceLimitLogVO(int deviceLimitKey, int cKey, int userKey, String indate, int type, int dataKey,
                             String deviceId, String deviceModel, String osVersion, String appVersion) {
        this.deviceLimitKey = deviceLimitKey;
        this.cKey = cKey;
        this.userKey = userKey;
        this.indate = indate;
        this.deleteDate = Util.returnNow();
        this.type = type;
        this.dataKey = dataKey;
        this.deviceId = deviceId;
        this.deviceModel = Util.isNullValue(deviceModel, "");
        this.osVersion = Util.isNullValue(osVersion, "");
        this.appVersion = Util.isNullValue(appVersion, "");
    }
}
