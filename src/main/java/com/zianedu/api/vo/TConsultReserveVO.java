package com.zianedu.api.vo;

import com.zianedu.api.utils.Util;
import lombok.Data;

@Data
public class TConsultReserveVO {

    private int userKey;

    private String reserveDate;

    private int reserveTimeKey;

    private int reserveType;

    private int reserveLocation;

    private String createDate;

    public TConsultReserveVO(){}

    public TConsultReserveVO(int userKey, String reserveDate, int reserveTimeKey, int reserveType, int reserveLocation) {
        this.userKey = userKey;
        this.reserveDate = reserveDate;
        this.reserveTimeKey = reserveTimeKey;
        this.reserveType = reserveType;
        this.reserveLocation = reserveLocation;
    }
}
