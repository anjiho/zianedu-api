package com.zianedu.api.vo;

import com.zianedu.api.utils.Util;
import com.zianedu.api.utils.ZianUtils;
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

    public TOrderVO(OrderVO vo, String uniqueTypeList, String uniqueExtendDayList) {
        this.cKey = 100;
        this.userKey = vo.getUserKey();
        this.jId = ZianUtils.getJId();
        this.price = vo.getPrice();
        this.pricePay = vo.getPricePay();
        this.point = vo.getPoint();
        this.dcWelfare = 0;
        this.dcPoint = 0;
        this.dcCoupon = 0;
        this.dcFree = 0;
        this.deliveryPrice = vo.getDeliveryPrice();
        this.payStatus = vo.getPayStatus();
        this.payDate = Util.returnNow();
        this.payType = vo.getPayType();
        this.cancelDate = "";
        this.bank = "";
        this.bankAccount = "";
        this.cardCode = vo.getCardCode();
        this.depositUser = vo.getDepositUser();
        this.depositDate = "";
        this.deliveryName = vo.getDeliveryName();
        this.deliveryTelephone = vo.getDeliveryTelephone();
        this.deliveryTelephoneMobile = vo.getDeliveryTelephoneMobile();
        this.deliveryEmail = vo.getDeliveryEmail();
        this.deliveryZipcode = vo.getDeliveryZipcode();
        this.deliveryAddress = vo.getDeliveryAddress();
        this.deliveryAddressRoad = vo.getDeliveryAddressRoad();
        this.deliveryAddressAdd = vo.getDeliveryAddressAdd();
        this.uniqueTypeList = uniqueTypeList;
        this.uniqueExtendDayList = uniqueExtendDayList;
        this.payKey = vo.getPayKey();
        this.offlineSerial = 0;
        this.isMobile = vo.getIsMobile();
        this.isOffline = 0;
        this.tmp = 0;
        this.gNameList = "";
        this.isCancelRequest = 0;
        this.cashReceiptType = 0;
        this.cashReceiptNumber = "";
        this.cancelRequestDate = "";
    }

}
