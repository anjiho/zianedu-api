package com.zianedu.api.service;

import com.zianedu.api.define.datasource.ApiResultKeyCode;
import com.zianedu.api.define.datasource.DeviceLimitDeviceType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.define.err.ZianException;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.dto.ApiResultStringDTO;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.mapper.UserMapper;
import com.zianedu.api.utils.RandomUtil;
import com.zianedu.api.utils.SecurityUtil;
import com.zianedu.api.utils.StringUtils;
import com.zianedu.api.utils.Util;
import com.zianedu.api.vo.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class UserService extends ApiResultKeyCode {

    protected int resultCode = OK.value();

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private EmailSendService emailSendService;

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
    public ApiResultCodeDTO regUser(TUserVO tUserVO) {
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
    public ApiResultCodeDTO modifyUserPassword(int userKey, String currentUserPwd,  String changeUserPwd) {
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
                      * TODO 기기변경을 해준다
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

}

