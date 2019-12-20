package com.zianedu.api.mapper;

import com.zianedu.api.vo.TDeviceChangeCodeVO;
import com.zianedu.api.vo.TUserSecessionVO;
import com.zianedu.api.vo.TUserVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    /** SELECT **/
    TUserVO selectUserInfo(@Param("userId") String userId, @Param("userPwd") String userPwd);

    Integer selectUserCount(@Param("userId") String userId, @Param("userPwd") String userPwd);

    Integer selectDuplicatedUserId(@Param("userId") String userId);

    TUserVO selectUserInfoByUserKey(@Param("userKey") int userKey);

    TUserVO selectUserInfoAtSecession(@Param("userName") String userName, @Param("userId") String userId,
                                      @Param("userPassword") String userPassword);

    Integer selectUserCountAtChangePasswd(@Param("userKey") int userId, @Param("userPwd") String userPwd);

    Integer selectUserCurrentPoint(@Param("userKey") int userKey);

    int selectDeviceLimitCount(@Param("userKey") int userKey, @Param("deviceType") int deviceType);

    /** INSERT **/
    Integer insertUserInfo(TUserVO tUserVO);

    Integer insertUserSecession(TUserSecessionVO tUserSecessionVO);

    Integer insertDeviceChangeCode(TDeviceChangeCodeVO tDeviceChangeCodeVO);

    /** UPDATE **/
    void updateUserInfo(TUserVO tUserVO);

    void updateUserPassword(@Param("userKey") int userKey, @Param("userPwd") String userPwd);
}
