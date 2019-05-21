package com.zianedu.api.mapper;

import com.zianedu.api.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {

    /** SELECT **/
    List<CartListVO> selectCartList(@Param("userKey") int userKey, @Param("type") int type);

    CartListVO selectOrderListByCartKey(@Param("cartKey") int cartKey);

    List<CartListVO> selectOrderListByImmediatelyBuy(@Param("gKeys") List<Integer>gKeys);

    List<CartListVO> selectOrderListByImmediatelyBuyAtFree(@Param("ctgKey") int ctgKey);

    List<OrderDeliveryListVO> selectUserOrderDeliveryList(@Param("userKey") int userKey, @Param("startDate") String startDate,
                                                    @Param("endDate") String endDate);

    List<String> selectGoodsNameFromOrderList(@Param("jKey") int jKey);

    List<OrderGoodsDetailVO> selectOrderGoodsDetailInfo(@Param("jKey") int jKey);

    DeliveryAddressVO selectDeliveryAddressInfo(@Param("jKey") int jKey);

    TOrderVO selectUserOrderInfo(@Param("jKey") int jKey);


    /** INSERT **/

    /** UPDATE **/

    /** DELETE **/
    void deleteCartInfo(@Param("cartKey") int cartKey);
}
