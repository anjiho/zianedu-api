package com.zianedu.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TPayInipayVO {
    @ApiModelProperty(hidden = true, readOnly = true)
    private int payKey;

    @ApiModelProperty(hidden = true, readOnly = true)
    private int cKey = 100;

    @ApiModelProperty(hidden = true, readOnly = true)
    private String indate;

    @ApiModelProperty(hidden = true, readOnly = true)
    private String tid = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String resultcode = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String resultmsg = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String paymethod = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String moid = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private int totprice = 0;

    @ApiModelProperty(hidden = true, readOnly = true)
    private String appldate = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String appltime = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String applnum = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String cardQuota = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String cardInterest = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String cardNum = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String cardCode = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String cardBankcode = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String eventcode = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String cardApplprice = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String ocbPayprice = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String acctBankcode = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private String resulterrorcode = "";

    @ApiModelProperty(hidden = true, readOnly = true)
    private int isMobile = 0;
}
