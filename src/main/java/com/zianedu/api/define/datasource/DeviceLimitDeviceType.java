package com.zianedu.api.define.datasource;

public enum DeviceLimitDeviceType {

    PC(0),
    MOBILE(1);

    int deviceTypeKey;

    DeviceLimitDeviceType(int deviceTypeKey) {
        this.deviceTypeKey = deviceTypeKey;
    }

    public static Integer getDeviceTypeKey(String deviceType) {
        for (DeviceLimitDeviceType deviceLimitDeviceType : DeviceLimitDeviceType.values()) {
            if (deviceType == deviceLimitDeviceType.toString()) {
                return deviceLimitDeviceType.deviceTypeKey;
            }
        }
        return null;
    }

}
