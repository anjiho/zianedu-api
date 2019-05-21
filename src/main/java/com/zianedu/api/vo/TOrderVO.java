package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TOrderVO {

    private int jKey;

    private int cKey;

    private int userKey;

    private String jId = "";

    private String indate = "";

    private int price;

    private int pricePay;

    private int point;

    private int dcWelfare;

    private int dcPoint;

    private int dcCoupon;

    private int dcFree;

    private int deliveryPrice;

    private int payStatus;

    private String payDate;

    private int payType;

    private String cancelDate;

    private String bank;

    private String bankAccount;

    private String cardCode;

    private String depositUser;

    private String depositDate;

    private String deliveryName;

    private String deliveryTelephone;

    private String deliveryTelephoneMobile;

    private String deliveryEmail;

    private String deliveryZipcode;

    private String deliveryAddress;

    private String deliveryAddressRoad;

    private String deliveryAddressAdd;

    private String uniqueTypeList;

    private String uniqueExtendDayList;

    private int payKey;

    private int offlineSerial;

    private int isMobile;

    private int isOffline;

    private int tmp;

    private String gNameList;

    private int isCancelRequest;

    private int cashReceiptType;

    private String cashReceiptNumber;

    private String cancelRequestDate;

}
