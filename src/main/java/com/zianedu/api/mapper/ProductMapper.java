package com.zianedu.api.mapper;

import com.zianedu.api.vo.AcademySignUpVO;
import com.zianedu.api.vo.OnlineSignUpVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {

    /** SELECT **/
    List<AcademySignUpVO> selectAcademySignUp(@Param("userKey") int userKey);

    List<OnlineSignUpVO> selectPromotionOnlineSignUp(@Param("userKey") int userKey, @Param("deviceType") String deviceType);

    Integer selectOnlineLectureProgressRate(@Param("jLecKey") int jLecKey);

    /** INSERT **/

    /** UPDATE **/
}
