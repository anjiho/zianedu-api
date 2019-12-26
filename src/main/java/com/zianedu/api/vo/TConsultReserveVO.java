package com.zianedu.api.vo;

import com.zianedu.api.utils.Util;
import lombok.Data;

@Data
public class TConsultReserveVO {

    private String reserveDate;

    private int reserveTimeKey;

    private int reserveType;

    private int reserveLocation;

    private String userName;

    private String mobileNumber;

    private String emailAddress;

    private int ctgKey1;

    private int ctgKey2;

    private int ctgKey3;

    private String reserveContents;

    private String createDate;

    public TConsultReserveVO(){}

    public TConsultReserveVO(String reserveDate, int reserveTimeKey, int reserveType, int reserveLocation, String userName,
                             String mobileNumber, String emailAddress, int ctgKey1, int ctgKey2, int ctgKey3, String reserveContents) {
        this.reserveDate = reserveDate;
        this.reserveTimeKey = reserveTimeKey;
        this.reserveType = reserveType;
        this.reserveLocation = reserveLocation;
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
        this.ctgKey1 = ctgKey1;
        this.ctgKey2 = ctgKey2;
        this.ctgKey3 = ctgKey3;
        this.reserveContents = reserveContents;
    }
}
