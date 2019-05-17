package com.zianedu.api.mapper;

import com.zianedu.api.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {

    /** SELECT **/
    List<CartListVO> selectCartList(@Param("userKey") int userKey, @Param("type") int type);

    /** INSERT **/

    /** UPDATE **/
}
