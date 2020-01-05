package com.zianedu.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TConsultReserveVO implements Serializable {
    @ApiModelProperty(hidden = true,readOnly = true)
    private int idx;
    @ApiModelProperty(hidden = true,readOnly = true)
    private int userKey;
    @ApiModelProperty(hidden = true,readOnly = true)
    private String reserveDate;
    @ApiModelProperty(hidden = true,readOnly = true)
    private int reserveTimeKey;
    @ApiModelProperty(hidden = true,readOnly = true)
    private int reserveType;
    @ApiModelProperty(hidden = true,readOnly = true)
    private int reserveLocation;
    @ApiModelProperty(hidden = true,readOnly = true)
    private String userName;
    @ApiModelProperty(hidden = true,readOnly = true)
    private String mobileNumber;
    @ApiModelProperty(hidden = true,readOnly = true)
    private String emailAddress;
    @ApiModelProperty(hidden = true,readOnly = true)
    private int ctgKey1;
    @ApiModelProperty(hidden = true,readOnly = true)
    private int ctgKey2;
    @ApiModelProperty(hidden = true,readOnly = true)
    private int ctgKey3;
    @ApiModelProperty(hidden = true,readOnly = true)
    private String reserveContents;
    @ApiModelProperty(hidden = true,readOnly = true)
    private String createDate;

    public TConsultReserveVO(){}

    public TConsultReserveVO(TConsultReserveVO reserveVO) {
        this.userKey = reserveVO.getUserKey();
        this.reserveDate = reserveVO.getReserveDate();
        this.reserveTimeKey = reserveVO.getReserveTimeKey();
        this.reserveType = reserveVO.getReserveType();
        this.reserveLocation = reserveVO.getReserveLocation();
        this.userName = reserveVO.getUserName();
        this.mobileNumber = reserveVO.getMobileNumber();
        this.emailAddress = reserveVO.getEmailAddress();
        this.ctgKey1 = reserveVO.getCtgKey1();
        this.ctgKey2 = reserveVO.getCtgKey2();
        this.ctgKey3 = reserveVO.getCtgKey3();
        this.reserveContents = reserveVO.getReserveContents();
    }
}
