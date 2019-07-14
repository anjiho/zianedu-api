package com.zianedu.api.vo;

import com.zianedu.api.utils.Aes256;
import com.zianedu.api.utils.SecurityUtil;
import com.zianedu.api.utils.Util;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TUserVO implements Serializable {

    @ApiModelProperty(hidden = true,readOnly = true)
    private int userKey;

    @ApiModelProperty(hidden = true,readOnly = true)
    private Long cKey;
    //아이디
    @ApiModelProperty(hidden = true,readOnly = true)
    private String userId;
    //등록일
    @ApiModelProperty(hidden = true,readOnly = true)
    private String indate;
    //이름
    @ApiModelProperty(hidden = true,readOnly = true)
    private String name;
    //권한
    @ApiModelProperty(hidden = true,readOnly = true)
    private int authority;
    //가입상태 0 - 가입대기, 1 - 오프라인에서 가입후 대기중, 10 - 회원가입된 상태, 20 - 탈퇴대기, 21 - 탈퇴완료
    @ApiModelProperty(hidden = true,readOnly = true)
    private int status;
    //비밀번호
    @ApiModelProperty(hidden = true,readOnly = true)
    private String pwd;
    //생년월일
    @ApiModelProperty(hidden = true,readOnly = true)
    private String birth;
    //음력
    @ApiModelProperty(hidden = true,readOnly = true)
    private String lunar;
    //성별
    @ApiModelProperty(hidden = true,readOnly = true)
    private int gender;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String telephone;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String telephoneMobile;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String zipcode;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String addressRoad;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String addressNumber;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String address;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String email;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String snsFacebook;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String snsTwiter;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String snsGoogleplus;

    @ApiModelProperty(hidden = true,readOnly = true)
    private int recvSms;

    @ApiModelProperty(hidden = true,readOnly = true)
    private int recvEmail;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String recvSnsFacebook;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String recvSnsTwiter;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String recvSnsGoogleplus;
    //복지 할인률
    @ApiModelProperty(hidden = true,readOnly = true)
    private int welfareDcPercent;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String certCode;

    @ApiModelProperty(hidden = true,readOnly = true)
    private int grade;
    //직렬 카테고리 키
    @ApiModelProperty(hidden = true,readOnly = true)
    private int interestCtgKey0;

    @ApiModelProperty(hidden = true,readOnly = true)
    private int interestCtgKey1;

    @ApiModelProperty(hidden = true,readOnly = true)
    private int isMobileReg;

    @ApiModelProperty(hidden = true,readOnly = true)
    private int adminAuthorityKey;
    //등급변경일
    @ApiModelProperty(hidden = true,readOnly = true)
    private String gradeDate;

    @ApiModelProperty(hidden = true,readOnly = true)
    private int gradeGKey;

    @ApiModelProperty(hidden = true,readOnly = true)
    private int gradePrice;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String note;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String appPushKey;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String appDeviceType;

    @ApiModelProperty(hidden = true,readOnly = true)
    private String userPwd;

    @ApiModelProperty(hidden = true,readOnly = true)
    private int cartCount;

    public TUserVO() {}

    public TUserVO(TUserVO tUserVO) throws Exception {
        this.userKey = tUserVO.getUserKey();
        this.userId = tUserVO.getUserId();
        this.indate = Util.returnNow();
        this.name = tUserVO.getName();
        this.authority = 10;
        this.pwd = tUserVO.getPwd();
        this.birth = Util.isNullValue(tUserVO.getBirth(), "");
        this.lunar = tUserVO.getLunar();
        this.gender = 0;
        this.telephone = Util.isNullValue(tUserVO.getTelephone(), "");
        this.telephoneMobile = Util.isNullValue(tUserVO.getTelephoneMobile(), "");
        this.zipcode = Util.isNullValue(tUserVO.getZipcode(), "");
        this.addressRoad = tUserVO.getAddressRoad();
        this.addressNumber = tUserVO.getAddressNumber();
        this.address = Util.isNullValue(tUserVO.getAddress(), "");
        this.email = Util.isNullValue(tUserVO.getEmail(), "");
        this.recvSms = tUserVO.getRecvSms();
        this.recvEmail = tUserVO.getRecvEmail();
        this.certCode = tUserVO.getCertCode();
        this.grade = 0;
        this.interestCtgKey0 = tUserVO.getInterestCtgKey0();
        this.adminAuthorityKey = 0;
        this.isMobileReg = tUserVO.getIsMobileReg();
        this.gradePrice = 0;
        this.note = Util.isNullValue(tUserVO.getNote(), "");
        this.userPwd = !"".equals(Util.isNullValue(tUserVO.getPwd(), "")) ? SecurityUtil.encryptSHA256(tUserVO.getPwd()) : "";
    }
}
