package com.zianedu.api.mapper;

import com.zianedu.api.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {

    /** SELECT **/
    List<CartListVO> selectCartList(@Param("userKey") int userKey, @Param("type") int type);

    CartListVO selectOrderListByCartKey(@Param("cartKey") int cartKey);

    List<CartListVO> selectOrderListByImmediatelyBuy(@Param("gKeys") List<Integer>gKeys);

    CartListVO selectOrderListByImmediatelyBuyFromPriceKey(@Param("gKey") int gKey, @Param("priceKey") int priceKey);

    List<CartListVO> selectOrderListByImmediatelyBuyAtFree(@Param("ctgKey") int ctgKey);

    List<OrderDeliveryListVO> selectUserOrderDeliveryList(@Param("userKey") int userKey, @Param("startDate") String startDate,
                                                    @Param("endDate") String endDate);

    List<String> selectGoodsNameFromOrderList(@Param("jKey") int jKey);

    List<OrderGoodsDetailVO> selectOrderGoodsDetailInfo(@Param("jKey") int jKey);

    DeliveryAddressVO selectDeliveryAddressInfo(@Param("jKey") int jKey);

    PaymentVO selectUserPaymentInfo(@Param("jKey") int jKey);

    List<PointListVO> selectUserPointListInfo(@Param("userKey") int userKey);

    TCartVO selectCartInfoByCartKey(@Param("cartKey") int cartKey);

    List<TCartLinkGoodsVO> selectCartLinkGoodsList(@Param("cartKey") int cartKey);

    Integer selectCartCountByUserKey(@Param("userKey") int userKey);

    List<OrderDeliveryListVO> selectUserOrderList(@Param("userKey") int userKey, @Param("startDate") String startDate, @Param("endDate") String endDate,
                                                  @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    int selectUserOrderListCount(@Param("userKey") int userKey, @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<OrderDetailInfoVO> selectUserOrderDetailInfo(@Param("jKey") int jKey);

    /** INSERT **/
    void insertTCart(TCartVO tCartVO);

    void insertTCartLinkGoods(@Param("cartKey") int cartKey, @Param("gKey") int gKey, @Param("priceKey") int priceKey);

    void insertTOrderPromotion(TOrderPromotionVO tOrderPromotionVO);

    /** UPDATE **/

    /** DELETE **/
    void deleteCartInfo(@Param("cartKey") int cartKey);
}
