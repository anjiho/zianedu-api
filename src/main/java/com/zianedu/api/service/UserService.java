package com.zianedu.api.service;

import com.zianedu.api.define.datasource.ApiResultKeyCode;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.define.err.ZianException;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.mapper.UserMapper;
import com.zianedu.api.utils.SecurityUtil;
import com.zianedu.api.utils.Util;
import com.zianedu.api.vo.AcademySignUpVO;
import com.zianedu.api.vo.TUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class UserService extends ApiResultKeyCode {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

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

}

