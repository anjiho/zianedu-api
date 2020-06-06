package com.zianedu.api.mapper;

import com.zianedu.api.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OrderMapper {

    /** SELECT **/
    List<CartListVO> selectCartList(@Param("userKey") int userKey, @Param("type") int type);

    CartListVO selectOrderListByCartKey(@Param("cartKey") int cartKey);

    List<CartListVO> selectOrderListByImmediatelyBuy(@Param("gKeys") List<Integer>gKeys);

    List<CartListVO> selectOrderListByImmediatelyBuyFromPriceKeys(@Param("priceKeys") List<Integer>priceKeys);

    List<CartListVO> selectOrderListByImmediatelyBuyFromPriceKeyList(@Param("priceKeys") List<Integer>priceKeys);

    CartListVO selectOrderListByImmediatelyBuyFromPriceKey(@Param("gKey") int gKey, @Param("priceKey") int priceKey);

    List<CartListVO> selectOrderListByImmediatelyBuyAtFree(@Param("ctgKey") int ctgKey);

    List<OrderDeliveryListVO> selectUserOrderDeliveryList(@Param("userKey") int userKey, @Param("startDate") String startDate,
                                                    @Param("endDate") String endDate);

    List<String> selectGoodsNameFromOrderList(@Param("jKey") int jKey);

    List<OrderGoodsDetailVO> selectOrderGoodsDetailInfo(@Param("jKey") int jKey);

    DeliveryAddressVO selectDeliveryAddressInfo(@Param("jKey") int jKey);

    PaymentVO selectUserPaymentInfo(@Param("jKey") int jKey);

    int selectUserCouponListInfoCount(@Param("userKey") int userKey);

    List<CouponListVO> selectUserCouponListInfo(@Param("userKey") int userKey, @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    int selectCouponChkNumber(@Param("couponNumber") String couponNumber);

    int selectCouponOverlapNumber(@Param("couponNumber") String couponNumber);

    CouponOfflineInfoVO selectOfflineKey(@Param("couponNumber") String couponNumber);

    List<PointListVO> selectUserPointListInfo(@Param("userKey") int userKey, @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    int selectUserPointListInfoCount(@Param("userKey") int userKey);

    UserPointInfoVO selectUserPointInfo(@Param("userKey") int userKey);

    TCartVO selectCartInfoByCartKey(@Param("cartKey") int cartKey);

    List<CartListVO> selectCartInfoByRetake(@Param("userKey") int userKey);

    List<TCartLinkGoodsVO> selectCartLinkGoodsList(@Param("cartKey") int cartKey);

    Integer selectCartCountByUserKey(@Param("userKey") int userKey);

    List<OrderDeliveryListVO> selectUserOrderList(@Param("userKey") int userKey, @Param("startDate") String startDate, @Param("endDate") String endDate,
                                                  @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    int selectUserOrderListCount(@Param("userKey") int userKey, @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<OrderDetailInfoVO> selectUserOrderDetailInfo(@Param("jKey") int jKey);

    int selectTBbsDataCountByJLecKey(@Param("userKey") int userKey, @Param("jLecKey") int jLecKey);

    int selectJidChk(@Param("jId") String jId);

    int selectGkey(@Param("jleckey") int jleckey);

    /** INSERT **/
    void insertTCart(TCartVO tCartVO);

    void insertTCartLinkGoods(@Param("cartKey") int cartKey, @Param("gKey") int gKey, @Param("priceKey") int priceKey);

    void insertTOrderPromotion(TOrderPromotionVO tOrderPromotionVO);

    void insertCouponIssue(@Param("couponOffKey") int couponOffKey, @Param("userKey") int userKey, @Param("couponMasterKey") int couponMasterKey);

    /** UPDATE **/
    void updateTOderLecLimitDay(@Param("jLecKey") int jLecKey, @Param("dayCount") int dayCount);

    void updateCouponOfflineNumber(@Param("couponNumber") String couponNumber);

    /** DELETE **/
    void deleteCartInfo(@Param("cartKey") int cartKey);
}
