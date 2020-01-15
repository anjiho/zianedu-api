package com.zianedu.api.mapper;

import com.zianedu.api.vo.*;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    /** SELECT **/
    TUserVO selectUserInfo(@Param("userId") String userId, @Param("userPwd") String userPwd);

    Integer selectUserCount(@Param("userId") String userId, @Param("userPwd") String userPwd);

    Integer selectDuplicatedUserId(@Param("userId") String userId);

    TUserVO selectUserInfoByUserKey(@Param("userKey") int userKey);

    TUserVO selectUserInfoByMobileNumber(@Param("mobileNumber") String mobileNumber);

    TUserVO selectUserInfoAtSecession(@Param("userName") String userName, @Param("userId") String userId,
                                      @Param("userPassword") String userPassword);

    Integer selectUserCountAtChangePasswd(@Param("userKey") int userId, @Param("userPwd") String userPwd);

    Integer selectUserCurrentPoint(@Param("userKey") int userKey);

    int selectDeviceLimitLogCount(@Param("userKey") int userKey, @Param("deviceType") int deviceType);

    int selectDeviceChangeCodeCount(@Param("userKey") int userKey, @Param("code") String code);

    int selectDeviceChangeCodeCountByRequestDate(@Param("userKey") int userKey, @Param("code") String code);

    TDeviceChangeCodeVO selectDeviceChangeCodeInfo(@Param("userKey") int userKey, @Param("code") String code);

    TDeviceLimitVO selectTDeviceLimitInfo(@Param("userKey") int userKey, @Param("deviceType") int deviceType);

    /** INSERT **/
    Integer insertUserInfo(TUserVO tUserVO);

    Integer insertUserSecession(TUserSecessionVO tUserSecessionVO);

    Integer insertDeviceChangeCode(TDeviceChangeCodeVO tDeviceChangeCodeVO);

    void insertTDeviceLimitLog(TDeviceLimitLogVO tDeviceLimitLogVO);

    /** UPDATE **/
    void updateUserInfo(TUserVO tUserVO);

    void updateUserPassword(@Param("userKey") int userKey, @Param("userPwd") String userPwd);

    void updateTDeviceChangeCode(@Param("idx") int idx);

    /** DELETE **/
    void deleteTDeviceLimit(@Param("deviceLimitKey") int deviceLimitKey);
}
