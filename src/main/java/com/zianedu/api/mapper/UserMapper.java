package com.zianedu.api.mapper;

import com.zianedu.api.vo.TUserVO;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    /** SELECT **/
    TUserVO selectUserInfo(@Param("userId") String userId, @Param("userPwd") String userPwd);

    Integer selectUserCount(@Param("userId") String userId, @Param("userPwd") String userPwd);

    Integer selectDuplicatedUserId(@Param("userId") String userId);

    TUserVO selectUserInfoByUserKey(@Param("userKey") int userKey);

    Integer selectUserCountAtChangePasswd(@Param("userKey") int userId, @Param("userPwd") String userPwd);

    Integer selectUserCurrentPoint(@Param("userKey") int userKey);

    /** INSERT **/
    Integer insertUserInfo(TUserVO tUserVO);

    /** UPDATE **/
    void updateUserInfo(TUserVO tUserVO);

    void updateUserPassword(@Param("userKey") int userKey, @Param("userPwd") String userPwd);
}
