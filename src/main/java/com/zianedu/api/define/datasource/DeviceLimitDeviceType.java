package com.zianedu.api.define.datasource;

public enum DeviceLimitDeviceType {

    PC("0"),
    MOBILE("1");

    String deviceTypeKey;

    DeviceLimitDeviceType(String deviceTypeKey) {
        this.deviceTypeKey = deviceTypeKey;
    }

    public static String getDeviceTypeKey(String deviceType) {
        for (DeviceLimitDeviceType deviceLimitDeviceType : DeviceLimitDeviceType.values()) {
            if (deviceType.equals(deviceLimitDeviceType.name())) {
                return deviceLimitDeviceType.deviceTypeKey;
            }
        }
        return null;
    }

}
