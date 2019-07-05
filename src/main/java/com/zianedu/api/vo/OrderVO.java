package com.zianedu.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderVO {

    @ApiModelProperty(hidden = true, readOnly = true)
    private int userKey;

    @ApiModelProperty(hidden = true, readOnly = true)
    private int price;

    @ApiModelProperty(hidden = true, readOnly = true)
    private int pricePay;

    @ApiModelProperty(hidden = true, readOnly = true)
    private int point = 0;

    @ApiModelProperty(hidden = true, readOnly = true)
    private int discountPoint = 0;

    @ApiModelProperty(hidden = true, readOnly = true)
    private int deliveryPrice = 0;

    @ApiModelProperty(hidden = true, readOnly = true)
    private int payStatus;

    @ApiModelProperty(hidden = true, readOnly = true)
    private int payType;

    @ApiModelProperty(hidden = true, readOnly = true)
    private String bank;

    @ApiModelProperty(hidden = true, readOnly = true)
    private String bankAccount;

    @ApiModelProperty(hidden = true, readOnly = true)
    private String cardCode = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String depositUser = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String deliveryName = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String deliveryTelephone = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String deliveryTelephoneMobile = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String deliveryEmail = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String deliveryZipcode = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String deliveryAddress = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String deliveryAddressRoad = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String deliveryAddressAdd = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String uniqueExtendDayList;

    @ApiModelProperty(hidden = true, readOnly = true)
    private int payKey;

    @ApiModelProperty(hidden = true, readOnly = true)
    private int isMobile = 0;

    @ApiModelProperty(hidden = true, readOnly = true)
    private String orderGoodsList;

}
