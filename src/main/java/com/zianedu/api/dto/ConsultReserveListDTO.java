package com.zianedu.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ConsultReserveListDTO {

    private int userKey;

    private String reserveDate;

    private int reserveTimeKey;

    private int reserveType;

    private int reserveLocation;

    private String userName;

    private String mobileNumber;

    private String emailAddress;

    private String reserveContents;

    private String createDate;

    private int consultStatus;

    private String reserveTimeName;

    private String reserveTypeName;

    private String reserveLocationName;

    private String consultStatusName;
}
