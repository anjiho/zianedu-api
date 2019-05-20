package com.zianedu.api.service;

import com.zianedu.api.define.datasource.GoodsType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.*;
import com.zianedu.api.mapper.OrderMapper;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.mapper.UserMapper;
import com.zianedu.api.utils.StringUtils;
import com.zianedu.api.vo.CartListVO;
import com.zianedu.api.vo.TUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class OrderService {

    protected final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 장바구니 리스트
     * @param userKey
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultObjectDTO getUserCartList(int userKey) {
        int resultCode = OK.value();

        int orderPrice = 0;
        int deliveryPrice = 0;
        int totalPoint = 0;

        List<CartListVO>academyCartInfo = new ArrayList<>();
        List<CartListVO>videoCartInfo = new ArrayList<>();
        List<CartListVO>promotionCartInfo = new ArrayList<>();
        List<CartListVO>bookCartInfo = new ArrayList<>();
        List<CartListVO>examCartInfo = new ArrayList<>();

        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            academyCartInfo = orderMapper.selectCartList(userKey, GoodsType.ACADEMY.getGoodsTypeKey());
            videoCartInfo = orderMapper.selectCartList(userKey, GoodsType.VIDEO.getGoodsTypeKey());
            promotionCartInfo = orderMapper.selectCartList(userKey, GoodsType.PACKAGE.getGoodsTypeKey());
            bookCartInfo = orderMapper.selectCartList(userKey, GoodsType.BOOK.getGoodsTypeKey());
            examCartInfo = orderMapper.selectCartList(userKey, GoodsType.EXAM.getGoodsTypeKey());

            if (academyCartInfo.size() > 0) {
                for (CartListVO academyList : academyCartInfo) {
                    orderPrice += academyList.getSellPrice();
                    totalPoint += academyList.getPoint();

                    academyList.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(academyList.getPrice())) + "원");
                    academyList.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(academyList.getSellPrice())) + "원");
                    academyList.setPointName(StringUtils.addThousandSeparatorCommas(String.valueOf(academyList.getPoint())) + "점");
                }
            }

            if (videoCartInfo.size() > 0) {
                for (CartListVO videoList : videoCartInfo) {
                    orderPrice += videoList.getSellPrice();
                    totalPoint += videoList.getPoint();

                    int subjectCount = productMapper.selectVideoSubjectCount(videoList.getGKey());
                    videoList.setSubjectCount(subjectCount + "강");
                    videoList.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(videoList.getPrice())) + "원");
                    videoList.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(videoList.getSellPrice())) + "원");
                    videoList.setPointName(StringUtils.addThousandSeparatorCommas(String.valueOf(videoList.getPoint())) + "점");
                }
            }

            if (promotionCartInfo.size() > 0) {
                for (CartListVO promotionList : promotionCartInfo) {
                    if (promotionList.getKind() == 0) {
                        orderPrice += promotionList.getLinkSellPrice();

                        promotionList.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(promotionList.getLinkPrice())) + "원");
                        promotionList.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(promotionList.getLinkSellPrice())) + "원");
                        promotionList.setPointName(StringUtils.addThousandSeparatorCommas(String.valueOf(promotionList.getPoint())) + "점");

                    } else if (promotionList.getKind() == 12) {
                        orderPrice += promotionList.getSellPrice();

                        promotionList.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(promotionList.getPrice())) + "원");
                        promotionList.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(promotionList.getSellPrice())) + "원");
                        promotionList.setPointName(StringUtils.addThousandSeparatorCommas(String.valueOf(promotionList.getPoint())) + "점");
                    }
                    totalPoint += promotionList.getPoint();
                }
            }

            if (bookCartInfo.size() > 0) {
                for (CartListVO bookList : bookCartInfo) {
                    orderPrice += bookList.getSellPrice();
                    totalPoint += bookList.getPoint();

                    bookList.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(bookList.getPrice())) + "원");
                    bookList.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(bookList.getSellPrice())) + "원");
                    bookList.setPointName(StringUtils.addThousandSeparatorCommas(String.valueOf(bookList.getPoint())) + "점");
                }
                //배송비 추가
                deliveryPrice = 2500;
            }

            if (examCartInfo.size() > 0) {
                for (CartListVO examList : examCartInfo) {
                    orderPrice += examList.getSellPrice();
                    totalPoint += examList.getPoint();

                    examList.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(examList.getPrice())) + "원");
                    examList.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(examList.getSellPrice())) + "원");
                    examList.setPointName(StringUtils.addThousandSeparatorCommas(String.valueOf(examList.getPoint())) + "점");
                }
            }
            int totalPrice = (orderPrice + deliveryPrice);
            CartResultDTO cartResultDTO = new CartResultDTO(
                    orderPrice, deliveryPrice, totalPrice, totalPoint,
                    academyCartInfo, videoCartInfo, promotionCartInfo, bookCartInfo, examCartInfo
            );
            return new ApiResultObjectDTO(cartResultDTO, resultCode);
        }
        return null;
    }

    /**
     * 주문서 작성 > 장바구니에 담은 상품 주문서 작성으로 갈때
     * @param userKey
     * @param cartKeys
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultObjectDTO getOrderSheetFromCart(int userKey, Integer[] cartKeys) {
        int resultCode = OK.value();

        OrderSheetDTO orderSheetDTO = new OrderSheetDTO();

        if (cartKeys == null || cartKeys.length == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            int totalProductPrice = 0;
            int totalPoint = 0;
            int deliveryPrice = 0;
            int videoPrice = 0;
            int academyPrice = 0;
            int promotionPrice = 0;
            int bookPrice = 0;
            int examPrice = 0;

            List<OrderProductListDTO>orderProductList = new ArrayList<>();

            for (Integer cartKey : cartKeys) {
                CartListVO cartInfo = orderMapper.selectOrderListByCartKey(cartKey);

                OrderProductListDTO orderProductListDTO = new OrderProductListDTO();

                //프로모션 상품일때
                if (cartInfo.getType() == 5) {
                    //자유패키지
                    if (cartInfo.getKind() == 0) {
                        orderProductListDTO = new OrderProductListDTO(
                                GoodsType.getGoodsTypeStr(cartInfo.getType()), cartInfo.getGoodsName(),
                                cartInfo.getCnt(), cartInfo.getLinkSellPrice(), cartInfo.getKind()
                        );
                        totalProductPrice += cartInfo.getLinkSellPrice();
                        totalPoint += cartInfo.getPoint();
                        promotionPrice += cartInfo.getLinkSellPrice();
                    //지안패스
                    } else if (cartInfo.getKind() == 12) {
                        orderProductListDTO = new OrderProductListDTO(
                                GoodsType.getGoodsTypeStr(cartInfo.getType()), cartInfo.getGoodsName(),
                                cartInfo.getCnt(), cartInfo.getSellPrice(), cartInfo.getKind()
                        );
                        totalProductPrice += cartInfo.getSellPrice();
                        totalPoint += cartInfo.getPoint();
                        promotionPrice += cartInfo.getSellPrice();
                    }
                } else {
                    orderProductListDTO = new OrderProductListDTO(
                            GoodsType.getGoodsTypeStr(cartInfo.getType()), cartInfo.getGoodsName(),
                            cartInfo.getCnt(), cartInfo.getSellPrice(), cartInfo.getKind()
                    );
                    totalProductPrice += cartInfo.getSellPrice();
                    totalPoint += cartInfo.getPoint();
                    //동영상 상품 합계
                    if (cartInfo.getType() == 1) {
                        videoPrice += cartInfo.getSellPrice();
                    }
                    //학원실강 상품 합계
                    if (cartInfo.getType() == 2) {
                        academyPrice += cartInfo.getSellPrice();
                    }
                    //도서 상품 합계
                    if (cartInfo.getType() == 3) {
                        bookPrice += cartInfo.getSellPrice();
                        deliveryPrice += 2500;
                    }
                    //모의고사 상품 합계
                    if (cartInfo.getType() == 4) {
                        examPrice += cartInfo.getSellPrice();
                    }
                }
                //주문상품 리스트
                orderProductList.add(orderProductListDTO);
            }
            //상품총합
            ProductTotalPriceDTO productTotalPrice = new ProductTotalPriceDTO(
                    totalProductPrice, totalPoint, deliveryPrice
            );
            //상품별 상품 금액
            GroupTotalPriceDTO productGroupPrice = new GroupTotalPriceDTO(
                videoPrice, academyPrice, promotionPrice, bookPrice, examPrice, deliveryPrice
            );
            //현재 보유 포인트
            int currentPoint = userMapper.selectUserCurrentPoint(userKey);
            //주문자 정보
            TUserVO orderUserInfo = userMapper.selectUserInfoByUserKey(userKey);

            orderSheetDTO = new OrderSheetDTO(
                    orderProductList, productTotalPrice, productGroupPrice, currentPoint, orderUserInfo
            );
        }
        return new ApiResultObjectDTO(orderSheetDTO, resultCode);
    }

    /**
     * 장바구니 삭제
     * @param cartKey
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO deleteCartInfo(int cartKey) {
        int resultCode = OK.value();

        if (cartKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            orderMapper.deleteCartInfo(cartKey);
        }
        return new ApiResultCodeDTO("CART_KEY", cartKey, resultCode);
    }
}
