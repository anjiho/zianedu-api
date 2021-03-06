package com.zianedu.api.service;

import com.zianedu.api.define.datasource.ApiResultKeyCode;
import com.zianedu.api.define.datasource.DeviceLimitDeviceType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.mapper.UserMapper;
import com.zianedu.api.utils.*;
import com.zianedu.api.vo.*;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import static org.springframework.http.HttpStatus.OK;

@Service
public class UserService extends ApiResultKeyCode {

    protected int resultCode = OK.value();

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private EmailSendService emailSendService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 사용자 로그인
     * @param userId
     * @param userPwd
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultObjectDTO loginUser(String userId, String userPwd) {
        int resultCode = OK.value();

        if ("".equals(userId) || "".equals(userPwd)) resultCode = ZianErrCode.BAD_REQUEST.code();
        //사용자 존재 여부 확인
        Integer userCount = userMapper.selectUserCount(userId, SecurityUtil.encryptSHA256(userPwd));
        //사용자가 없으면 없다는 에러
        if (userCount == 0) resultCode = ZianErrCode.CUSTOM_NOT_FOUNT_USER.code();
        //사용자가 있으면 사용자 정보 리턴
        return new ApiResultObjectDTO(
                userMapper.selectUserInfo(userId, SecurityUtil.encryptSHA256(userPwd)),
                resultCode
        );
    }

    @Transactional(readOnly = true)
    public ApiResultCodeDTO checkDuplicateUser(String userId) {
        int resultCode = OK.value();

        if ("".equals(userId)) resultCode = ZianErrCode.BAD_REQUEST.code();
        //사용자 아이디 중복 여부 확인
        Integer userCount = userMapper.selectDuplicatedUserId(userId);

        if (userCount > 0) {
            resultCode = ZianErrCode.CUSTOM_DUPLICATED_USER_ID.code();
        }
        return new ApiResultCodeDTO(USER_COUNT, userCount, resultCode);
    }

    /**
     * 회원가입
     * @param tUserVO
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO regUser(TUserVO tUserVO) throws Exception {
        int resultCode = OK.value();
        int userKey = 0;
        if (tUserVO == null) resultCode = ZianErrCode.BAD_REQUEST.code();

        //사용자 아이디 중복 여부 확인
        Integer userCount = userMapper.selectDuplicatedUserId(tUserVO.getUserId());
        if (userCount > 0) {
            resultCode = ZianErrCode.CUSTOM_DUPLICATED_USER_ID.code();
        } else {
            TUserVO regUser = new TUserVO(tUserVO);
            userMapper.insertUserInfo(regUser);

            if (regUser.getUserKey() == 0) resultCode = ZianErrCode.CUSTOM_FAIL_REG_USER.code();
            userKey = regUser.getUserKey();
            //회원가입에 의한 마일리지 주입
            paymentService.injectUserPoint("U", userKey, 3000, 0, "");
            //알기사접속
            Connection conn = null;
            String user = "ZIANEDU";
            String pwd = "wldks0815!";
            String url = "jdbc:oracle:thin:@118.217.181.175:1521:ZIAN";
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, user, pwd);

            CallableStatement cs = conn.prepareCall("INSERT INTO TB_MA_MEMBER( USER_ID, USER_NM, USER_NICKNM, SEX,USER_ROLE,USER_PWD, EMAIL,PHONE_NO ,USER_POINT,ISUSE,REG_DT,REG_ID, UPD_DT,PHONERECV_YN,EMAILRECV_YN,ZIP_CODE,ADDRESS1,ADDRESS2,MEMBER_GRADE,USER_POSITION,ADMIN_ROLE)" +
                "                                       VALUES( ?, ?, ?, ? , 'USER', ?, ?, ?, 0,'Y', SYSDATE,?, SYSDATE, 'Y','Y', ?, ?, ?,'일반','수강생', '')");
            cs.setString(1, regUser.getUserId());
            cs.setString(2, regUser.getName());
            cs.setString(3, regUser.getName());
            cs.setString(4, regUser.getGender() == 0 ? "M" : "F");
            cs.setString(5, regUser.getPwd());
            cs.setString(6, regUser.getEmail());
            cs.setString(7, regUser.getTelephoneMobile());
            cs.setString(8, String.valueOf(userKey));
            cs.setString(9, regUser.getZipcode());
            cs.setString(10, regUser.getAddressRoad());
            cs.setString(11, regUser.getAddress());
            cs.execute();

            cs.close();
            conn.close();
        }
        return new ApiResultCodeDTO(USER_KEY, userKey, resultCode);
    }

    /**
     * 사용자 기본정보 수정
     * @param tUserVO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultObjectDTO modifyUserInfo(TUserVO tUserVO) throws Exception {
        int resultCode = OK.value();
        TUserVO resultTUserVO = new TUserVO();

        if (tUserVO == null || tUserVO.getUserKey() == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TUserVO modifyTUserVO = new TUserVO(tUserVO);
            userMapper.updateUserInfo(modifyTUserVO);
            resultTUserVO = userMapper.selectUserInfoByUserKey(tUserVO.getUserKey());

            //알기사 접속
            Connection conn = null;
            String user = "ZIANEDU";
            String pwd = "wldks0815!";
            String url = "jdbc:oracle:thin:@118.217.181.175:1521:ZIAN";
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, user, pwd);

            CallableStatement cs = conn.prepareCall("UPDATE TB_MA_MEMBER SET PHONE_NO=?, ZIP_CODE=?,ADDRESS1=?,ADDRESS2=?,UPD_DT=SYSDATE WHERE USER_ID = ? ");
            cs.setString(1, modifyTUserVO.getTelephoneMobile());
            cs.setString(2, modifyTUserVO.getZipcode());
            cs.setString(3, modifyTUserVO.getAddressRoad());
            cs.setString(4, modifyTUserVO.getAddress());
            cs.setString(5, resultTUserVO.getUserId());
            cs.execute();

            cs.close();
            conn.close();
        }
        return new ApiResultObjectDTO( resultTUserVO, resultCode );
    }

    /**
     * 사용자 비밀번호 변경
     * @param userKey
     * @param currentUserPwd
     * @param changeUserPwd
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO modifyUserPassword(int userKey, String currentUserPwd,  String changeUserPwd) throws Exception {
        int resultCode = OK.value();

        if (userKey == 0 && "".equals(Util.isNullValue(currentUserPwd, "")) && "".equals(Util.isNullValue(changeUserPwd, ""))) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            //비밀번호 길이 제한
            if (currentUserPwd.length() < 4 || changeUserPwd.length() < 4) {
                resultCode = ZianErrCode.CUSTOM_SHORT_USER_PASSWORD.code();
            } else {
                //현재 비밀번호로 맞는지 확인
                int userCount = userMapper.selectUserCountAtChangePasswd(userKey, SecurityUtil.encryptSHA256(currentUserPwd));
                //현재 비밀번호가 맞으면 새로운 비밀번호로 업데이트
                if (userCount == 1) {
                    userMapper.updateUserPassword(userKey, SecurityUtil.encryptSHA256(changeUserPwd));

                    //알기사접속
                    TUserVO resultTUserVO = userMapper.selectUserInfoByUserKey(userKey);
                    Connection conn = null;
                    String user = "ZIANEDU";
                    String pwd = "wldks0815!";
                    String url = "jdbc:oracle:thin:@118.217.181.175:1521:ZIAN";
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    conn = DriverManager.getConnection(url, user, pwd);

                    CallableStatement cs = conn.prepareCall("UPDATE TB_MA_MEMBER SET USER_PWD=? WHERE USER_ID = ?");
                    cs.setString(1, changeUserPwd);
                    cs.setString(2, resultTUserVO.getUserId());
                    cs.execute();
                    cs.close();
                    conn.close();

                } else {
                    //현재 비밀번호가 틀리면 에러
                    resultCode = ZianErrCode.CUSTOM_DIFFERENT_CURRENT_USER_PASSWORD.code();
                }
            }
        }
        return new ApiResultCodeDTO(USER_KEY, userKey, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO requestMemberSecession(String userName, String userId, String userPassword,
                                                   String secessionReason, String memo) {
        int resultCode = OK.value();
        int secessionKey = 0;

        if ("".equals(userName) || "".equals(userId) || "".equals(userPassword)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TUserVO userInfo = userMapper.selectUserInfoAtSecession(userName, userId, SecurityUtil.encryptSHA256(userPassword));
            if (userInfo == null) {
                resultCode = ZianErrCode.CUSTOM_NOT_FOUNT_USER.code();
            } else {
                TUserSecessionVO secessionVO = new TUserSecessionVO(
                        userInfo.getUserKey(), userInfo.getUserId(),  userInfo.getName(), secessionReason, memo
                );
                userMapper.insertUserSecession(secessionVO);
                secessionKey = secessionVO.getSecessionKey();
            }
        }
        return new ApiResultCodeDTO("SECESSION_KEY", secessionKey, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO requestChangeDevice(int userKey, String deviceType) throws Exception {
        int resultCode = OK.value();
        String sendEmailAddress = "";

        if (userKey == 0 && "".equals(deviceType)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            /**
             * 기기변경을 했는지 여부 체크 T_DEVICE_LIMIT 테이블 조건 확인
             */
            //로그 테이블 개수 확인
            int deviceLimitCnt = userMapper.selectDeviceLimitLogCount(userKey, Integer.parseInt(DeviceLimitDeviceType.getDeviceTypeKey(deviceType)));
            TDeviceLimitVO tDeviceLimitVO = userMapper.selectTDeviceLimitInfo(userKey, Integer.parseInt(DeviceLimitDeviceType.getDeviceTypeKey(deviceType)));
            if (tDeviceLimitVO == null) {
                resultCode = ZianErrCode.CUSTOM_DEVICE_LIMIT_NULL.code();
            } else {
                //기기제한에 걸렸을때
                if (deviceLimitCnt == 1) {
                    if (DeviceLimitDeviceType.PC.name().equals(deviceType)) {
                        resultCode = ZianErrCode.CUSTOM_DEVICE_LIMIT_PC.code();
                    } else if (DeviceLimitDeviceType.MOBILE.name().equals(deviceType)) {
                        resultCode = ZianErrCode.CUSTOM_DEVICE_LIMIT_MOBILE.code();
                    }
                } else if (deviceLimitCnt == 0) {
                    //기기변경이 가능할때
                    TUserVO userInfo = userMapper.selectUserInfoByUserKey(userKey);
                    String code = RandomUtil.getRandomNumber(4);    //코드값 생성
                    TDeviceChangeCodeVO codeVO = new TDeviceChangeCodeVO(
                            userKey, code, deviceType, userInfo.getEmail()
                    );
                    userMapper.insertDeviceChangeCode(codeVO);  //코드 정보 테이블 저장
                    if (codeVO.getIdx() > 0 && !"".equals(userInfo.getEmail())) {
                        emailSendService.sendEmail(userInfo.getEmail(), "기기변경 인증 메일", "인증코드 : " + code);    //이메일 발송
                        sendEmailAddress = userInfo.getEmail();
                    }
                } else {
                    resultCode = ZianErrCode.CUSTOM_DEVICE_LIMIT.code();
                }
            }
        }
        return new ApiResultCodeDTO("SEND_EMAIL_ADDRESS", sendEmailAddress, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO confirmChangeDeviceCode(int userKey, String code) {
        int resultCode = OK.value();
        boolean isCodeConfirm = false;

        if (userKey == 0 && "".equals(code)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            int deviceCodeCount = userMapper.selectDeviceChangeCodeCount(userKey, code);

            if (deviceCodeCount == 1) {
                 int deviceCodeDateCheckCount = userMapper.selectDeviceChangeCodeCountByRequestDate(userKey, code);
                 if (deviceCodeDateCheckCount == 1) {
                     /**
                      * 기기변경을 해준다
                      */
                     TDeviceChangeCodeVO changeCodeVO = userMapper.selectDeviceChangeCodeInfo(userKey, code);
                     if (changeCodeVO != null) {
                        TDeviceLimitVO deviceLimitVO = userMapper.selectTDeviceLimitInfo(
                                userKey, Integer.parseInt(DeviceLimitDeviceType.getDeviceTypeKey(changeCodeVO.getDeviceType()))
                        );
                         if (deviceLimitVO != null) {
                             String[] indates = StringUtils.splitComma(deviceLimitVO.getIndate());
                             TDeviceLimitLogVO limitLogVO = new TDeviceLimitLogVO(
                                     deviceLimitVO.getDeviceLimitKey(), deviceLimitVO.getCKey(), deviceLimitVO.getUserKey(), indates[0],
                                     deviceLimitVO.getType(), deviceLimitVO.getDataKey(), deviceLimitVO.getDeviceId(),
                                     deviceLimitVO.getDeviceModel(), deviceLimitVO.getOsVersion(), deviceLimitVO.getAppVersion()
                             );
                             userMapper.insertTDeviceLimitLog(limitLogVO);
                         }
                         userMapper.deleteTDeviceLimit(deviceLimitVO.getDeviceLimitKey());
                         userMapper.updateTDeviceChangeCode(changeCodeVO.getIdx());
                         isCodeConfirm = true;
                     } else {
                        resultCode = ZianErrCode.CUSTOM_DEVICE_CHANGE_CODE_CHECK_FAIL.code();
                     }
                 } else {
                     resultCode = ZianErrCode.CUSTOM_DEVICE_CHANGE_CODE_OVER_TIME.code();   //5분 초과
                 }
            } else if (deviceCodeCount == 0) {
                resultCode = ZianErrCode.CUSTOM_DEVICE_CHANGE_CODE_CHECK_FAIL.code();
            }
        }
        return new ApiResultCodeDTO("CHANGE_CODE_CONFIRM", isCodeConfirm, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getUserInfo(int userKey) {
        TUserVO userVO = new TUserVO();

        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            userVO = userMapper.selectUserInfoByUserKey(userKey);
        }
        return new ApiResultObjectDTO(userVO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultCodeDTO confirmUserPassword(int userKey, String userPwd) {
        boolean isConfirm = false;

        if (userKey == 0 && "".equals(userPwd)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            int userCount = userMapper.selectUserCountAtChangePasswd(userKey, SecurityUtil.encryptSHA256(userPwd));
            if (userCount == 1) isConfirm = true;
        }
        return new ApiResultCodeDTO("USER_CONFIRM", isConfirm, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getUserInfoByMobileNumber(String mobileNumber) {
        TUserVO userVO = new TUserVO();

        if ("".equals(mobileNumber)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            userVO = userMapper.selectUserInfoByMobileNumber(mobileNumber);
        }
        return new ApiResultObjectDTO(userVO, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO modifyUserPasswordByMobileNumber(int userKey, String changeUserPwd) {
        int resultCode = OK.value();

        if (userKey == 0 && "".equals(Util.isNullValue(changeUserPwd, ""))) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            //비밀번호 길이 제한
            if (changeUserPwd.length() < 4) {
                resultCode = ZianErrCode.CUSTOM_SHORT_USER_PASSWORD.code();
            } else {
                userMapper.updateUserPassword(userKey, SecurityUtil.encryptSHA256(changeUserPwd));
            }
        }
        return new ApiResultCodeDTO(USER_KEY, userKey, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getUserInfoFromFindPwd(String userId, String mobileNumber) {
        TUserVO userVO = new TUserVO();

        if ("".equals(userId) && "".equals(mobileNumber)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            userVO = userMapper.selectUserInfoFromFindPwd(userId, mobileNumber);
        }
        return new ApiResultObjectDTO(userVO, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO confirmDuplicateDevice(int userKey, int deviceType, String deviceId, int jLecKey, String osVersion, String appVersion) {
        boolean bl = false;
        int resultCode = OK.value();

        if (userKey == 0 && "".equals(deviceId)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            int deviceLimitCountByUserKey = userMapper.selectTDeviceLimitCountByDeviceId(userKey, deviceType, "");
            int deviceLimitCountByDeviceId = userMapper.selectTDeviceLimitCountByDeviceId(0, deviceType, deviceId);
            if (deviceLimitCountByUserKey > 0 && deviceLimitCountByDeviceId > 0) {  //사용자 키와 사용자 기기가 일치할때
                this.updateTOrderLecStartDt(jLecKey);
                bl = true;
            } else if (deviceLimitCountByUserKey == 0 && deviceLimitCountByDeviceId == 0) { //사용자가 처음 기기로 동영상을 플레이할때
                int jGKey = productMapper.selectVideoGoodsJGKey(jLecKey);
                TDeviceLimitVO limitVO = new TDeviceLimitVO();
                if (deviceType == 0) {
                    limitVO = new TDeviceLimitVO(userKey, deviceType, jGKey, deviceId);
                } else if (deviceType == 1) {
                    limitVO = new TDeviceLimitVO(userKey, deviceType, jGKey, deviceId, osVersion, appVersion);
                }
                userMapper.insertTDeviceLimit(limitVO);
//                TDeviceLimitVO deviceLimitVO = userMapper.selectTDeviceLimitInfo(userKey, deviceType);
//                if (deviceLimitVO != null) {
//                    String[] indates = StringUtils.splitComma(deviceLimitVO.getIndate());
//                    TDeviceLimitLogVO limitLogVO = new TDeviceLimitLogVO(
//                            deviceLimitVO.getDeviceLimitKey(), deviceLimitVO.getCKey(), deviceLimitVO.getUserKey(), indates[0],
//                            deviceLimitVO.getType(), deviceLimitVO.getDataKey(), deviceLimitVO.getDeviceId(),
//                            deviceLimitVO.getDeviceModel(), deviceLimitVO.getOsVersion(), deviceLimitVO.getAppVersion()
//                    );
//                    userMapper.insertTDeviceLimitLog(limitLogVO);
//                }
                this.updateTOrderLecStartDt(jLecKey);
                bl = true;
            }
        }
        return new ApiResultCodeDTO("RESULT", bl, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateTOrderLecStartDt(int jLecKey) {
        if (jLecKey == 0) return;
        String startDt = productMapper.selectTOrderLecStartDt(jLecKey);
        if (!"".equals(startDt)) {
            productMapper.updateTOrderLecStartDt(jLecKey);
        }
    }
}

