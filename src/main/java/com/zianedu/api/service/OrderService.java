package com.zianedu.api.service;

import com.google.gson.JsonArray;
import com.zianedu.api.define.datasource.*;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.*;
import com.zianedu.api.mapper.OrderMapper;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.mapper.UserMapper;
import com.zianedu.api.utils.*;
import com.zianedu.api.vo.*;
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
public class OrderService extends PagingSupport {

    protected final Logger logger = LoggerFactory.getLogger(OrderService.class);

    protected int resultCode = OK.value();

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

        int academyOrderPrice = 0;
        int videoOrderPrice = 0;
        int promotionOrderPrice = 0;
        int bookOrderPrice = 0;
        int examOrderPrice = 0;
        int retakeOrderPrice = 0;

        List<CartListVO>academyCartInfo = new ArrayList<>();
        List<CartListVO>videoCartInfo = new ArrayList<>();
        List<CartListVO>promotionCartInfo = new ArrayList<>();
        List<CartListVO>bookCartInfo = new ArrayList<>();
        List<CartListVO>examCartInfo = new ArrayList<>();
        List<CartListVO>retakeVideoCartInfo = new ArrayList<>();

        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            academyCartInfo = orderMapper.selectCartList(userKey, GoodsType.ACADEMY.getGoodsTypeKey());
            videoCartInfo = orderMapper.selectCartList(userKey, GoodsType.VIDEO.getGoodsTypeKey());
            promotionCartInfo = orderMapper.selectCartList(userKey, GoodsType.PACKAGE.getGoodsTypeKey());
            bookCartInfo = orderMapper.selectCartList(userKey, GoodsType.BOOK.getGoodsTypeKey());
            examCartInfo = orderMapper.selectCartList(userKey, GoodsType.EXAM.getGoodsTypeKey());
            retakeVideoCartInfo = orderMapper.selectCartInfoByRetake(userKey);

            if (academyCartInfo.size() > 0) {
                for (CartListVO academyList : academyCartInfo) {
                    orderPrice += academyList.getSellPrice();
                    totalPoint += academyList.getPoint();
                    academyOrderPrice += academyList.getSellPrice();

                    academyList.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(academyList.getPrice())) + "원");
                    academyList.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(academyList.getSellPrice())) + "원");
                    academyList.setPointName(StringUtils.addThousandSeparatorCommas(String.valueOf(academyList.getPoint())) + "점");
                }
            }

            if (videoCartInfo.size() > 0) {
                for (CartListVO videoList : videoCartInfo) {
                    orderPrice += videoList.getSellPrice();
                    totalPoint += videoList.getPoint();
                    videoOrderPrice += videoList.getSellPrice();

                    int subjectCount = productMapper.selectVideoSubjectCount(videoList.getGKey());
                    videoList.setSubjectCount(subjectCount + "강");
                    videoList.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(videoList.getPrice())) + "원");
                    videoList.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(videoList.getSellPrice())) + "원");
                    videoList.setPointName(StringUtils.addThousandSeparatorCommas(String.valueOf(videoList.getPoint())) + "점");

                }
                if (videoCartInfo.size() == 2) videoOrderPrice = ZianUtils.calcPercent(videoOrderPrice, 10);
                else if (videoCartInfo.size() > 2) videoOrderPrice = ZianUtils.calcPercent(videoOrderPrice, 20);
            }

            if (promotionCartInfo.size() > 0) {
                for (CartListVO promotionList : promotionCartInfo) {
//                    if (promotionList.getKind() == 0) {
//                        orderPrice += promotionList.getSellPrice();
//                        promotionOrderPrice += promotionList.getSellPrice();
//
//                        TPromotionVO promotionVO = productMapper.selectTPromotionInfoByGKey(promotionList.getGKey());
//                        promotionList.setPmType(promotionVO.getPmType());
//
//                        promotionList.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(promotionList.getPrice())) + "원");
//                        promotionList.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(promotionList.getSellPrice())) + "원");
//                        promotionList.setPointName(StringUtils.addThousandSeparatorCommas(String.valueOf(promotionList.getPoint())) + "점");
//
//                    } else {
//                        orderPrice += promotionList.getSellPrice();
//                        promotionOrderPrice += promotionList.getSellPrice();
//
//                        TPromotionVO promotionVO = productMapper.selectTPromotionInfoByGKey(promotionList.getGKey());
//                        promotionList.setPmType(promotionVO.getPmType());
//
//                        promotionList.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(promotionList.getPrice())) + "원");
//                        promotionList.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(promotionList.getSellPrice())) + "원");
//                        promotionList.setPointName(StringUtils.addThousandSeparatorCommas(String.valueOf(promotionList.getPoint())) + "점");
//                    }
                    orderPrice += promotionList.getSellPrice();
                    promotionOrderPrice += promotionList.getSellPrice();

                    TPromotionVO promotionVO = productMapper.selectTPromotionInfoByGKey(promotionList.getGKey());
                    promotionList.setPmType(promotionVO.getPmType());

                    promotionList.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(promotionList.getPrice())) + "원");
                    promotionList.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(promotionList.getSellPrice())) + "원");
                    promotionList.setPointName(StringUtils.addThousandSeparatorCommas(String.valueOf(promotionList.getPoint())) + "점");

                    totalPoint += promotionList.getPoint();
                }
            }

            if (bookCartInfo.size() > 0) {
                //int bookPrice = 0;
                for (CartListVO bookList : bookCartInfo) {
                    int bookCnt = bookList.getCnt();
                    for (int i = 0; i < bookList.getCnt(); i++) {
                        //bookPrice += bookList.getSellPrice();
                        orderPrice += bookList.getSellPrice();
                        bookOrderPrice += bookList.getSellPrice();
                    }
                    totalPoint += bookList.getPoint();
                    bookList.setPrice(bookList.getPrice() * bookCnt);
                    bookList.setSellPrice(bookList.getSellPrice() * bookCnt);

                    bookList.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(bookList.getPrice())) + "원");
                    bookList.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(bookList.getSellPrice())) + "원");
                    bookList.setPointName(StringUtils.addThousandSeparatorCommas(String.valueOf(bookList.getPoint())) + "점");
                }
                //배송비 추가
//                if (bookPrice < 30000) {
//                    deliveryPrice = 2500;
//                }
            }

            if (examCartInfo.size() > 0) {
                for (CartListVO examList : examCartInfo) {
                    orderPrice += examList.getSellPrice();
                    examOrderPrice += examList.getSellPrice();
                    totalPoint += examList.getPoint();

                    examList.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(examList.getPrice())) + "원");
                    examList.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(examList.getSellPrice())) + "원");
                    examList.setPointName(StringUtils.addThousandSeparatorCommas(String.valueOf(examList.getPoint())) + "점");
                }
            }

            if (retakeVideoCartInfo.size() > 0) {
                for (CartListVO retakeList : retakeVideoCartInfo) {
                    orderPrice += retakeList.getLinkSellPrice();
                    retakeOrderPrice += retakeList.getLinkSellPrice();

                    int subjectCount = productMapper.selectVideoSubjectCount(retakeList.getGKey());
                    retakeList.setSubjectCount(subjectCount + "강");
                    retakeList.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(retakeList.getLinkPrice())) + "원");
                    retakeList.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(retakeList.getLinkSellPrice())) + "원");
                    retakeList.setPointName("0점");
                }
            }

            if (bookOrderPrice > 0 && bookOrderPrice < 30000) {
                deliveryPrice = 2500;
            }
            int totalPrice = ( ( academyOrderPrice + videoOrderPrice + promotionOrderPrice + bookOrderPrice + examOrderPrice + retakeOrderPrice ));
            CartResultDTO cartResultDTO = new CartResultDTO(
                    totalPrice, deliveryPrice, (totalPrice + deliveryPrice), totalPoint,
                    academyCartInfo, videoCartInfo, promotionCartInfo, bookCartInfo, examCartInfo, retakeVideoCartInfo
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
            int retakePrice = 0;

            List<OrderProductListDTO>orderProductList = new ArrayList<>();
            int retakePoint = 0;
            int videoProductCnt = 0;
            for (Integer cartKey : cartKeys) {
                CartListVO cartInfo = orderMapper.selectOrderListByCartKey(cartKey);

                OrderProductListDTO orderProductListDTO = new OrderProductListDTO();

                //프로모션 상품일때
                if (cartInfo.getType() == 5) {
                    //자유패키지
//                    if (cartInfo.getKind() == 0) {
//                        orderProductListDTO = new OrderProductListDTO(
//                                cartInfo.getGKey(), cartInfo.getPriceKey(), cartInfo.getCartKey(), cartInfo.getType(),
//                                GoodsType.getGoodsTypeStr(cartInfo.getType()), cartInfo.getGoodsName(),
//                                cartInfo.getCnt(), cartInfo.getLinkSellPrice(), cartInfo.getKind(), cartInfo.getExtendDay(), 0
//                        );
//                        totalProductPrice += cartInfo.getLinkSellPrice();
//                        totalPoint += cartInfo.getPoint();
//                        promotionPrice += cartInfo.getLinkSellPrice();
//                    //지안패스
//                    } else if (cartInfo.getKind() == 12) {
//                        orderProductListDTO = new OrderProductListDTO(
//                                cartInfo.getGKey(), cartInfo.getPriceKey(), cartInfo.getCartKey(), cartInfo.getType(),
//                                GoodsType.getGoodsTypeStr(cartInfo.getType()), cartInfo.getGoodsName(),
//                                cartInfo.getCnt(), cartInfo.getSellPrice(), cartInfo.getKind(), cartInfo.getExtendDay(), 0
//                        );
//                        totalProductPrice += cartInfo.getSellPrice();
//                        totalPoint += cartInfo.getPoint();
//                        promotionPrice += cartInfo.getSellPrice();
//                    }
                    TPromotionVO promotionVO = productMapper.selectTPromotionInfoByGKey(cartInfo.getGKey());

                    orderProductListDTO = new OrderProductListDTO(
                            cartInfo.getGKey(), cartInfo.getPriceKey(), cartInfo.getCartKey(), cartInfo.getType(),
                            GoodsType.getGoodsTypeStr(cartInfo.getType(), cartInfo.getExtendDay()), cartInfo.getGoodsName(),
                            cartInfo.getCnt(), cartInfo.getSellPrice(), cartInfo.getKind(), cartInfo.getExtendDay(), promotionVO.getPmType()
                    );
                    totalProductPrice += cartInfo.getSellPrice();
                    totalPoint += cartInfo.getPoint();
                    promotionPrice += cartInfo.getSellPrice();

//                    if (cartInfo.getKind() > 0) {
//                        TPromotionVO promotionVO = productMapper.selectTPromotionInfoByGKey(cartInfo.getGKey());
//
//                        orderProductListDTO = new OrderProductListDTO(
//                                cartInfo.getGKey(), cartInfo.getPriceKey(), cartInfo.getCartKey(), cartInfo.getType(),
//                                GoodsType.getGoodsTypeStr(cartInfo.getType(), cartInfo.getExtendDay()), cartInfo.getGoodsName(),
//                                cartInfo.getCnt(), cartInfo.getSellPrice(), cartInfo.getKind(), cartInfo.getExtendDay(), promotionVO.getPmType()
//                        );
//                        totalProductPrice += cartInfo.getSellPrice();
//                        totalPoint += cartInfo.getPoint();
//                        promotionPrice += cartInfo.getSellPrice();
//                    }
                } else {
                    if (cartInfo.getExtendDay() == -1) {
                        orderProductListDTO = new OrderProductListDTO(
                                cartInfo.getGKey(), cartInfo.getPriceKey(), cartInfo.getCartKey(), cartInfo.getType(),
                                GoodsType.getGoodsTypeStr(cartInfo.getType(), cartInfo.getExtendDay()), cartInfo.getGoodsName(),
                                cartInfo.getCnt(), cartInfo.getSellPrice(), cartInfo.getKind(), cartInfo.getExtendDay(), 0
                        );
                    } else {
                        retakePoint = (cartInfo.getPoint() / cartInfo.getLimitDay()) * cartInfo.getExtendDay();
                        orderProductListDTO = new OrderProductListDTO(
                                cartInfo.getGKey(), cartInfo.getPriceKey(), cartInfo.getCartKey(), cartInfo.getType(),
                                GoodsType.getGoodsTypeStr(cartInfo.getType(), cartInfo.getExtendDay()), cartInfo.getGoodsName(),
                                cartInfo.getCnt(), cartInfo.getLinkSellPrice(), cartInfo.getKind(), cartInfo.getExtendDay(), 0
                        );
                    }
                    //totalProductPrice += cartInfo.getSellPrice();
                    if (cartInfo.getExtendDay() == -1) {
                        totalPoint += cartInfo.getPoint();
                    }
                    //동영상 상품 합계
                    if (cartInfo.getType() == 1 && cartInfo.getExtendDay() == -1) {
                        videoPrice += cartInfo.getSellPrice();
                        videoProductCnt++;
                    } else if (cartInfo.getType() == 1 && cartInfo.getExtendDay() > -1) {
                        retakePrice += cartInfo.getLinkSellPrice();
                        totalPoint += retakePoint;
                    }

                    //학원실강 상품 합계
                    if (cartInfo.getType() == 2) {
                        academyPrice += cartInfo.getSellPrice();
                    }
                    //도서 상품 합계
                    if (cartInfo.getType() == 3) {
                        bookPrice += cartInfo.getSellPrice() * cartInfo.getCnt();
                    }
                    //모의고사 상품 합계
                    if (cartInfo.getType() == 4) {
                        examPrice += cartInfo.getSellPrice();
                    }
                }
                //주문상품 리스트
                orderProductList.add(orderProductListDTO);

            }

            if (videoProductCnt == 2) videoPrice = ZianUtils.calcPercent(videoPrice, 10);
            else if (videoProductCnt > 2) videoPrice = ZianUtils.calcPercent(videoPrice, 20);

            if (bookPrice > 0 && bookPrice < 30000) {
                deliveryPrice = 2500;
            }

            totalProductPrice = promotionPrice + videoPrice + academyPrice + bookPrice + examPrice + retakePrice;
            //상품총합
            ProductTotalPriceDTO productTotalPrice = new ProductTotalPriceDTO(
                    totalProductPrice, totalPoint, deliveryPrice
            );
            //상품별 상품 금액
            GroupTotalPriceDTO productGroupPrice = new GroupTotalPriceDTO(
                videoPrice, academyPrice, promotionPrice, bookPrice, examPrice, deliveryPrice, retakePrice
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
     * 주문서 작성 > 일반 상품 > '바로신청' 버튼으로 주문서 작성으로 갈때
     * @param userKey
     * @param priceKeys
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultObjectDTO getOrderSheetFromImmediatelyBuy(int userKey, Integer[] priceKeys) {
        int resultCode = OK.value();

        OrderSheetDTO orderSheetDTO = new OrderSheetDTO();

        if (priceKeys == null || priceKeys.length == 0) {
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
            int retakePrice = 0;

            List<OrderProductListDTO>orderProductList = new ArrayList<>();

            List<Integer>priceKeyList = StringUtils.integerArrayToArrayList(priceKeys);
            List<CartListVO> buyProductList = orderMapper.selectOrderListByImmediatelyBuyFromPriceKeyList(priceKeyList);

            if (buyProductList.size() > 0) {
                int videoProductCnt = 0;
                for (CartListVO product : buyProductList) {
                    int count = 0;
                    if (product.getCnt() == 0) count = 1;

                    OrderProductListDTO orderProductListDTO = new OrderProductListDTO(
                            product.getGKey(), product.getPriceKey(), product.getCartKey(), product.getType(),
                            GoodsType.getGoodsTypeStr(product.getType(), -1), product.getGoodsName(),
                            count, product.getSellPrice(), product.getKind(), -1, product.getPmType()
                    );
                    //totalProductPrice += product.getSellPrice();
                    totalPoint += product.getPoint();
                    //동영상 상품 합계
                    if (product.getType() == 1) {
                        videoPrice += product.getSellPrice();
                        videoProductCnt++;
                    }
                    //학원실강 상품 합계
                    if (product.getType() == 2) {
                        academyPrice += product.getSellPrice();
                    }
                    //도서 상품 합계
                    if (product.getType() == 3) {
                        bookPrice += product.getSellPrice();
                        //deliveryPrice += 2500;
                    }
                    //모의고사 상품 합계
                    if (product.getType() == 4) {
                        examPrice += product.getSellPrice();
                    }

                    if (product.getType() == 5) {
                        promotionPrice += product.getSellPrice();
                    }
                    orderProductList.add(orderProductListDTO);
                }

                if (videoProductCnt == 2) videoPrice = ZianUtils.calcPercent(videoPrice, 10);
                else if (videoProductCnt > 2) videoPrice = ZianUtils.calcPercent(videoPrice, 20);

                if (bookPrice > 0 && bookPrice < 30000) {
                    deliveryPrice = 2500;
                }

                totalProductPrice = promotionPrice + videoPrice + academyPrice + bookPrice + examPrice + deliveryPrice;
                //상품총합
                ProductTotalPriceDTO productTotalPrice = new ProductTotalPriceDTO(
                        totalProductPrice, totalPoint, deliveryPrice
                );
                //상품별 상품 금액
                GroupTotalPriceDTO productGroupPrice = new GroupTotalPriceDTO(
                        videoPrice, academyPrice, promotionPrice, bookPrice, examPrice, deliveryPrice, retakePrice
                );
                //현재 보유 포인트
                int currentPoint = userMapper.selectUserCurrentPoint(userKey);
                //주문자 정보
                TUserVO orderUserInfo = userMapper.selectUserInfoByUserKey(userKey);

                orderSheetDTO = new OrderSheetDTO(
                        orderProductList, productTotalPrice, productGroupPrice, currentPoint, orderUserInfo
                );
            }
        }
        return new ApiResultObjectDTO(orderSheetDTO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getOrderSheetFromImmediatelyBuyAtBookStore(int userKey, Integer[] priceKeys, int bookCount) {
        int resultCode = OK.value();

        OrderSheetDTO orderSheetDTO = new OrderSheetDTO();

        if (priceKeys == null || priceKeys.length == 0) {
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
            int retakePrice = 0;

            List<OrderProductListDTO>orderProductList = new ArrayList<>();

            List<Integer>priceKeyList = StringUtils.integerArrayToArrayList(priceKeys);
            List<CartListVO> buyProductList = orderMapper.selectOrderListByImmediatelyBuyFromPriceKeyList(priceKeyList);

            if (buyProductList.size() > 0) {
                int videoProductCnt = 0;
                for (CartListVO product : buyProductList) {

                    OrderProductListDTO orderProductListDTO = new OrderProductListDTO(
                            product.getGKey(), product.getPriceKey(), product.getCartKey(), product.getType(),
                            GoodsType.getGoodsTypeStr(product.getType(), -1), product.getGoodsName(),
                            bookCount, product.getSellPrice(), product.getKind(), -1, product.getPmType()
                    );
                    //totalProductPrice += product.getSellPrice();
                    totalPoint += (product.getPoint() * bookCount);
                    //동영상 상품 합계
                    if (product.getType() == 1) {
                        videoPrice += product.getSellPrice();
                        videoProductCnt++;
                    }
                    //학원실강 상품 합계
                    if (product.getType() == 2) {
                        academyPrice += product.getSellPrice();
                    }
                    //도서 상품 합계
                    if (product.getType() == 3) {
                        bookPrice += (product.getSellPrice() * bookCount);
                        //deliveryPrice += 2500;
                    }
                    //모의고사 상품 합계
                    if (product.getType() == 4) {
                        examPrice += product.getSellPrice();
                    }
                    orderProductList.add(orderProductListDTO);
                }

                if (videoProductCnt == 2) videoPrice = ZianUtils.calcPercent(videoPrice, 10);
                else if (videoProductCnt > 2) videoPrice = ZianUtils.calcPercent(videoPrice, 20);

                if (bookPrice > 0 && bookPrice < 30000) {
                    deliveryPrice = 2500;
                }

                totalProductPrice = promotionPrice + videoPrice + academyPrice + bookPrice + examPrice + deliveryPrice;
                //상품총합
                ProductTotalPriceDTO productTotalPrice = new ProductTotalPriceDTO(
                        totalProductPrice, totalPoint, deliveryPrice
                );
                //상품별 상품 금액
                GroupTotalPriceDTO productGroupPrice = new GroupTotalPriceDTO(
                        videoPrice, academyPrice, promotionPrice, bookPrice, examPrice, deliveryPrice, retakePrice
                );
                //현재 보유 포인트
                int currentPoint = userMapper.selectUserCurrentPoint(userKey);
                //주문자 정보
                TUserVO orderUserInfo = userMapper.selectUserInfoByUserKey(userKey);

                orderSheetDTO = new OrderSheetDTO(
                        orderProductList, productTotalPrice, productGroupPrice, currentPoint, orderUserInfo
                );
            }
        }
        return new ApiResultObjectDTO(orderSheetDTO, resultCode);
    }

    /**
     * 주문서 작성 > 자유 패키지 > '바로신청' 버튼으로 주문서 작성으로 갈때
     * @param userKey
     * @param goodsInfoList
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultObjectDTO getOrderSheetInfoFromImmediatelyAtFreePackage(int userKey, List<GoodsInfoVO> goodsInfoList) {
        int resultCode = OK.value();

        OrderSheetDTO orderSheetDTO = new OrderSheetDTO();

        if (userKey == 0 || goodsInfoList.size() == 0) {
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
            int linkPrice = 0;
            int linkSellPrice = 0;

            //List<Integer>gKeyList = StringUtils.integerArrayToArrayList(gKeys);
            List<CartListVO> buyProductList = new ArrayList<>();
            for (GoodsInfoVO vo : goodsInfoList) {
                CartListVO cartListVO = orderMapper.selectOrderListByImmediatelyBuyFromPriceKey(vo.getGKey(), vo.getPriceKey());
                buyProductList.add(cartListVO);

                linkPrice += cartListVO.getPrice();
                totalProductPrice += cartListVO.getSellPrice();
            }

            int calcTotalPrice = 0;
            if (buyProductList.size() == 2) {
                calcTotalPrice = (int)(totalProductPrice - (totalProductPrice * 0.1));
            } else if (buyProductList.size() > 2) {
                calcTotalPrice = (int)(totalProductPrice - (totalProductPrice * 0.2));
            }

            if (buyProductList.size() > 0) {
                TCartVO cartVO = new TCartVO(userKey, totalProductPrice, calcTotalPrice);

                // T_CART 테이블에 저장
                orderMapper.insertTCart(cartVO);
                //저장된 카드 테이블 키값
                int cartKey = cartVO.getCartKey();

                for (CartListVO product : buyProductList) {
                    // T_CART_LINK_GOODS 테이블에 상품목록 저장
                    orderMapper.insertTCartLinkGoods(cartKey, product.getGKey(), product.getPriceKey());
                }

                OrderProductListDTO orderProductListDTO = new OrderProductListDTO(
                        ZianCoreCode.FREE_PACKAGE_GOODS_KEY, ZianCoreCode.FREE_PACKAGE_PRICE_KEY, cartKey, ZianCoreCode.FREE_PACKAGE_GOODS_TYPE,
                        "프로모션", "자유패키지", 1, calcTotalPrice, 0, -1, PromotionPmType.FREE_PACKAGE.getPromotionPmKey()
                );
                //상품총합
                ProductTotalPriceDTO productTotalPrice = new ProductTotalPriceDTO(
                        calcTotalPrice, totalPoint, deliveryPrice
                );
                //상품별 상품 금액
                GroupTotalPriceDTO productGroupPrice = new GroupTotalPriceDTO(
                        videoPrice, academyPrice, calcTotalPrice, bookPrice, examPrice, deliveryPrice, 0
                );
                //현재 보유 포인트
                int currentPoint = userMapper.selectUserCurrentPoint(userKey);
                //주문자 정보
                TUserVO orderUserInfo = userMapper.selectUserInfoByUserKey(userKey);

                orderSheetDTO = new OrderSheetDTO(
                        orderProductListDTO, productTotalPrice, productGroupPrice, currentPoint, orderUserInfo
                );
            }
        }
        return new ApiResultObjectDTO(orderSheetDTO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getOrderSheetInfoFromImmediatelyAtSpecialPackage(int userKey, List<GoodsInfoVO> goodsInfoList) {
        int resultCode = OK.value();

        OrderSheetDTO orderSheetDTO = new OrderSheetDTO();

        if (userKey == 0 || goodsInfoList.size() == 0) {
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
            int calcTotalPrice = 0;
            int linkSellPrice = 0;


            List<OrderProductListDTO>orderProductList = new ArrayList<>();
            List<CartListVO> buyProductList = new ArrayList<>();

            for (GoodsInfoVO vo : goodsInfoList) {
                CartListVO cartListVO = orderMapper.selectOrderListByImmediatelyBuyFromPriceKey(vo.getGKey(), vo.getPriceKey());
                buyProductList.add(cartListVO);
                //T_CART 테이블 저장
                TCartVO tCartVO = new TCartVO(userKey, cartListVO.getGKey(), cartListVO.getPriceKey(), 1, 833);
                orderMapper.insertTCart(tCartVO);
                //저장된 T_CART 테이블 키값
                int cartKey = tCartVO.getCartKey();

                OrderProductListDTO dto = new OrderProductListDTO(
                    cartListVO.getGKey(), cartListVO.getPriceKey(), cartKey, ZianCoreCode.FREE_PACKAGE_GOODS_TYPE, "프로모션",
                    cartListVO.getGoodsName(), 1, cartListVO.getSellPrice(), cartListVO.getKind(), -1, PromotionPmType.SPECIAL_PACKAGE.getPromotionPmKey()
                );
                orderProductList.add(dto);

                calcTotalPrice += cartListVO.getSellPrice();
            }
            //상품총합
            ProductTotalPriceDTO productTotalPrice = new ProductTotalPriceDTO(
                    calcTotalPrice, 0, 0
            );
            //상품별 상품 금액
            GroupTotalPriceDTO productGroupPrice = new GroupTotalPriceDTO(
                    videoPrice, academyPrice, calcTotalPrice, bookPrice, examPrice, deliveryPrice, 0
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
     * 주문배송조회 리스트
     * @param userKey
     * @param startDate
     * @param endDate
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultListDTO getUserOrderDeliveryList(int userKey, String startDate, String endDate) {
        int resultCode = OK.value();

        List<OrderDeliveryListVO>orderDeliveryList = new ArrayList<>();
        if (userKey == 0
                && "".equals(Util.isNullValue(startDate, ""))
                && "".equals(Util.isNullValue(endDate, ""))) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            orderDeliveryList = orderMapper.selectUserOrderDeliveryList(
                    userKey, Util.isNullValue(startDate, ""), Util.isNullValue(endDate, "")
            );
            if (orderDeliveryList.size() > 0) {
                for (OrderDeliveryListVO vo : orderDeliveryList) {
                    List<String> goodsNameList = orderMapper.selectGoodsNameFromOrderList(vo.getJKey());
                    vo.setGoodsNameList(goodsNameList);
                    vo.setPayStatusName(OrderPayStatusType.getOrderPayStatusStr(vo.getPayStatus()));
                    vo.setPriceName(StringUtils.addThousandSeparatorCommas( String.valueOf(vo.getPrice())) + "원");
                }
            }
        }
        return new ApiResultListDTO(orderDeliveryList, resultCode);
    }

    /**
     * 주문배송조회 상세
     * @param userKey
     * @param jKey
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultObjectDTO getUserOrderDeliveryDetailInfo(int userKey, int jKey) {
        int resultCode = OK.value();

        List<OrderGoodsDetailVO> orderGoodsDetailList = new ArrayList<>();
        TUserVO orderUserInfo = new TUserVO();
        DeliveryAddressVO deliveryAddressInfo = new DeliveryAddressVO();
        PaymentVO paymentInfo = new PaymentVO();

        if (jKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            orderGoodsDetailList = orderMapper.selectOrderGoodsDetailInfo(jKey);

            if (orderGoodsDetailList.size() > 0) {
                for (OrderGoodsDetailVO vo : orderGoodsDetailList) {
                    vo.setProductType(GoodsType.getGoodsTypeStr(vo.getType(), vo.getExtendDay()));
                    vo.setPriceName(StringUtils.addThousandSeparatorCommas( String.valueOf(vo.getSellPrice())) + "원");
                }
            }
            //주문자 정보
            orderUserInfo = userMapper.selectUserInfoByUserKey(userKey);
            //배송지 정보
            deliveryAddressInfo = orderMapper.selectDeliveryAddressInfo(jKey);
            //결제,배송정보
            paymentInfo = orderMapper.selectUserPaymentInfo(jKey);
            if (paymentInfo != null) {
                paymentInfo.setPayStatusName(OrderPayStatusType.getOrderPayStatusStr(paymentInfo.getPayStatus()));
                paymentInfo.setPayTypeName(OrderPayType.getOrderPayTypeStr(paymentInfo.getPayType()));
                paymentInfo.setDeliveryStatusName(DeliveryStatusType.getDeliveryStatusName(paymentInfo.getDeliveryStatus()));
                paymentInfo.setPricePayName(StringUtils.addThousandSeparatorCommas(String.valueOf(paymentInfo.getPricePay())) + "원");
            }
        }
        OrderDetailDTO orderDetailInfo = new OrderDetailDTO(
                orderGoodsDetailList, orderUserInfo, deliveryAddressInfo, paymentInfo);

        return new ApiResultObjectDTO(orderDetailInfo, resultCode);
    }

    /**
     * 마일리지 목록
     * @param userKey
     * @return
     */
    @Transactional(readOnly = true)
    public ApiPagingResultDTO getUserPointList(int userKey, int sPage, int listLimit) {
        int resultCode = OK.value();

        int totalCount = 0;
        int startNumber = getPagingStartNumber(sPage, listLimit);

        //UserPointInfoVO pointInfoVO = null;
        List<PointListVO> pointList = new ArrayList<>();

        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            //pointInfoVO = orderMapper.selectUserPointInfo(userKey);
            totalCount = orderMapper.selectUserPointListInfoCount(userKey);
            pointList = orderMapper.selectUserPointListInfo(userKey, startNumber, listLimit);

            if (pointList.size() > 0) {

                for (PointListVO vo : pointList) {
                    String pointDesc = "";
                    if (vo.getDescType() == 0) pointDesc = vo.getDescription();
                    else if (vo.getDescType() == 1) pointDesc = "상품구매 마일리지 획득";
                    else if (vo.getDescType() == 2) pointDesc = "상품구매 마일리지 사용";
                    else if (vo.getDescType() == 3) pointDesc = "상품구매 주문취소 마일리지 반환";
                    else if (vo.getDescType() == 4) pointDesc = "결제취소 사용한 마일리지 반환";
                    else if (vo.getDescType() == 5) pointDesc = "결제취소 획득한 마일리지 반환";

                    vo.setPointDesc(pointDesc);
                    //vo.setPointName(StringUtils.addThousandSeparatorCommas(String.valueOf(vo.getPoint())) + "점");
                }
            }
        }
        //PointInfoDTO pointInfo = new PointInfoDTO(pointInfoVO, pointList);

        return new ApiPagingResultDTO(totalCount, pointList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getUserPointInfo(int userKey) {
        int resultCode = OK.value();

        UserPointInfoVO pointInfoVO = null;

        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            pointInfoVO = orderMapper.selectUserPointInfo(userKey);
        }
        return new ApiResultObjectDTO(pointInfoVO, resultCode);
    }

    /**
     * 장바구니 삭제
     * @param cartKeys
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO deleteCartInfo(Integer[] cartKeys) {
        int resultCode = OK.value();

        String resultKey = "";
        if (cartKeys.length == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            for (Integer cartKey : cartKeys) {
                orderMapper.deleteCartInfo(cartKey);
            }
            resultKey = StringUtils.implodeFromInteger(",", cartKeys);
        }
        return new ApiResultCodeDTO("CART_KEY", resultKey, resultCode);
    }

    /**
     * 장바구니 저장
     * @param saveCartInfo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO saveCart(String saveCartInfo) {
        int resultCode = OK.value();
        String resultKey = "";

        if ("".equals(saveCartInfo)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            JsonArray saveCartInfoJson = GsonUtil.convertStringToJsonArray(saveCartInfo);
            List<SaveCartVO>saveCartList = GsonUtil.getObjectFromJsonArray(saveCartInfoJson, SaveCartVO.class);

            if (saveCartList.size() > 0) {
                List<Integer>cartKeyList = new ArrayList<>();
                for (SaveCartVO vo : saveCartList) {
                    TCartVO cartVO = new TCartVO(
                            vo.getUserKey(), vo.getGKey(), vo.getPriceKey(), vo.getGCount()
                    );
                    orderMapper.insertTCart(cartVO);
                    cartKeyList.add(cartVO.getCartKey());
                }
                Integer[] cartKeyArray = StringUtils.arrayIntegerListToStringArray(cartKeyList);
                resultKey = StringUtils.implodeFromInteger(",", cartKeyArray);
            }
        }
        return new ApiResultCodeDTO("CART_KEY", resultKey, resultCode);
    }

    /**
     * 장바구니 저장
     * @param saveCartInfo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO saveCartAtRetake(String saveCartInfo) {
        int resultCode = OK.value();
        String resultKey = "";

        if ("".equals(saveCartInfo)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            JsonArray saveCartInfoJson = GsonUtil.convertStringToJsonArray(saveCartInfo);
            List<SaveCartVO>saveCartList = GsonUtil.getObjectFromJsonArray(saveCartInfoJson, SaveCartVO.class);

            if (saveCartList.size() > 0) {
                List<Integer>cartKeyList = new ArrayList<>();
                for (SaveCartVO vo : saveCartList) {
                    VideoLectureDetailVO lectureInfo = productMapper.selectGoodsInfoByRetake(vo.getGKey(), vo.getPriceKey());
                    int sellPrice = 0;
                    int extendDay = 0;
                    if (lectureInfo.getType() == 1) {
                        if (vo.getExtendDay() == 0) {
                            sellPrice = ZianUtils.calcPercent(lectureInfo.getSellPrice(), lectureInfo.getExtendPercent());
                        } else {
                            sellPrice = (lectureInfo.getSellPrice() / lectureInfo.getLimitDay()) * vo.getExtendDay();
                            extendDay = vo.getExtendDay();
                        }
                    } else if (lectureInfo.getType() == 5) {
                        sellPrice = (lectureInfo.getSellPrice() / lectureInfo.getKind() ) * (vo.getExtendDay() / 30);
                        extendDay = vo.getExtendDay();
                    }
                    TCartVO cartVO = new TCartVO(
                            vo.getUserKey(), vo.getGKey(), vo.getPriceKey(), vo.getGCount(),
                            String.valueOf(extendDay), lectureInfo.getPrice(), StringUtils.convertSipWonZero(sellPrice)
                    );
                    orderMapper.insertTCart(cartVO);
                    cartKeyList.add(cartVO.getCartKey());
                }
                Integer[] cartKeyArray = StringUtils.arrayIntegerListToStringArray(cartKeyList);
                resultKey = StringUtils.implodeFromInteger(",", cartKeyArray);
            }
        }
        return new ApiResultCodeDTO("CART_KEY", resultKey, resultCode);
    }

    /**
     * 자유패키지 장바구니 저장
     * @param userKey
     * @param gKeys
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultCodeDTO saveCartFreePackage(int userKey, Integer[] gKeys) {
        int resultCode = OK.value();
        String resultKey = "";

        if (gKeys == null || gKeys.length == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            int linkPirce = 0;
            int linkSellPirce = 0;

            List<Integer>gKeyList = StringUtils.integerArrayToArrayList(gKeys);
            List<CartListVO> buyProductList = orderMapper.selectOrderListByImmediatelyBuy(gKeyList);

            if (buyProductList.size() > 0) {
                for (CartListVO product : buyProductList) {
                    linkPirce += product.getSellPrice();
                }

                if (buyProductList.size() == 2) {
                    linkSellPirce = (int)(linkPirce - (linkPirce * 0.1));
                } else if (buyProductList.size() > 2) {
                    linkSellPirce = (int)(linkPirce - (linkPirce * 0.2));
                }

                TCartVO cartVO = new TCartVO(
                    userKey, linkPirce, linkSellPirce
                );
                orderMapper.insertTCart(cartVO);
                resultKey = String.valueOf(cartVO.getCartKey());
            }
        }
        return new ApiResultCodeDTO("CART_KEY", resultKey, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getUserOrderList(int userKey, String startDate, String endDate, int sPage, int listLimit) {
        List<OrderDeliveryListVO> orderList = new ArrayList<>();
        int totalCount = 0;
        int startNumber = getPagingStartNumber(sPage, listLimit);

        if (userKey == 0 && "".equals(startDate) && "".equals(endDate)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            totalCount = orderMapper.selectUserOrderListCount(userKey, startDate, endDate);
            orderList = orderMapper.selectUserOrderList(userKey, startDate, endDate, startNumber, listLimit);
            if (orderList.size() > 0) {
                for (OrderDeliveryListVO vo : orderList) {
                    if (vo.getOrderGoodsCount() > 1) {
                        vo.setOrderGoodsName(vo.getOrderGoodsName() + "외" + vo.getOrderGoodsCount());
                    }
                    vo.setSellPriceName(vo.getSellPriceName() + "원");
                    vo.setJId("[" + vo.getJId() + "]");
                    vo.setDeliveryStatusName(DeliveryStatusType.getDeliveryStatusName(vo.getDeliveryStatus()));
                    if (vo.getPayStatus() == 0) vo.setPayStatusName("결제대기");
                    else if (vo.getPayStatus() == 2) vo.setPayStatusName("결제완료");
                }
            }
        }
        return new ApiPagingResultDTO(totalCount, orderList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getUserOrderDetailInfo(int jKey) {
        UserOrderDetailDTO userOrderDetailDTO = null;

        if (jKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            List<OrderDetailInfoVO> orderList = orderMapper.selectUserOrderDetailInfo(jKey);
            TUserVO orderUserInfo = new TUserVO();
            DeliveryAddressVO deliveryAddressInfo = new DeliveryAddressVO();
            PaymentVO paymentInfo = new PaymentVO();

            if (orderList.size() > 0) {
                for (OrderDetailInfoVO vo : orderList) {
                    vo.setTypeName(GoodsType.getGoodsTypeStr(vo.getType(), vo.getExtendDay()));
                    if (vo.getPayType() != 20 && vo.getType() == 1 && vo.getJLecKey() > 0) {
                        vo.setReviewYn(true);
                    }
                }
            }
            //주문자 정보
            orderUserInfo = userMapper.selectUserInfoByUserKey(orderList.get(0).getUserKey());
            //배송지 정보
            deliveryAddressInfo = orderMapper.selectDeliveryAddressInfo(jKey);
            //결제,배송정보
            paymentInfo = orderMapper.selectUserPaymentInfo(jKey);
            if (paymentInfo != null) {
                paymentInfo.setPayStatusName(OrderPayStatusType.getOrderPayStatusStr(paymentInfo.getPayStatus()));
                paymentInfo.setPayTypeName(OrderPayType.getOrderPayTypeStr(paymentInfo.getPayType()));
                paymentInfo.setDeliveryStatusName(DeliveryStatusType.getDeliveryStatusName(paymentInfo.getDeliveryStatus()));
                paymentInfo.setPricePayName(StringUtils.addThousandSeparatorCommas(String.valueOf(paymentInfo.getPricePay())) + "원");
                paymentInfo.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(paymentInfo.getPrice())) + "원");
            }
            userOrderDetailDTO = new UserOrderDetailDTO(orderList, orderUserInfo, deliveryAddressInfo, paymentInfo);
        }
        return new ApiResultObjectDTO(userOrderDetailDTO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getOrderSheetInfoFromImmediatelyAtRetake(int userKey, String productInfo) {
        int resultCode = OK.value();

        OrderSheetDTO orderSheetDTO = new OrderSheetDTO();

        if (productInfo == null || productInfo.length() == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            JsonArray productInfoJson = GsonUtil.convertStringToJsonArray(productInfo);
            List<SaveCartVO> retakeProductInfo = GsonUtil.getObjectFromJsonArray(productInfoJson, SaveCartVO.class);

            int totalProductPrice = 0;
            int totalPoint = 0;
            int deliveryPrice = 0;
            int videoPrice = 0;
            int academyPrice = 0;
            int promotionPrice = 0;
            int bookPrice = 0;
            int examPrice = 0;
            int retakePrice = 0;

            List<OrderProductListDTO>orderProductList = new ArrayList<>();

            List<Integer>priceKeyList = new ArrayList<>();
            for (SaveCartVO vo : retakeProductInfo) {
                priceKeyList.add(vo.getPriceKey());
            }

            List<CartListVO> buyProductList = orderMapper.selectOrderListByImmediatelyBuyFromPriceKeyList(priceKeyList);

            if (buyProductList.size() > 0) {
                int videoProductCnt = 0;
                for (SaveCartVO retakeInfo : retakeProductInfo) {

                    for (CartListVO product : buyProductList) {
                        int sellPrice = 0;
                        int point = 0;
                        if (product.getType() == 1) {
                            if (retakeInfo.getExtendDay() == 0) {
                                sellPrice = ZianUtils.calcPercent(product.getSellPrice(), product.getExtendPercent());
                            } else {
                                sellPrice = (product.getSellPrice() / product.getLimitDay()) * retakeInfo.getExtendDay();
                                point = (product.getPoint() / product.getLimitDay()) * retakeInfo.getExtendDay();
                            }
                        } else if (product.getType() == 5) {
                            sellPrice = (product.getSellPrice() / product.getKind() ) * (retakeInfo.getExtendDay() / 30);
                        }
                        OrderProductListDTO orderProductListDTO = new OrderProductListDTO(
                                product.getGKey(), product.getPriceKey(), product.getCartKey(), product.getType(),
                                GoodsType.getGoodsTypeStr(product.getType(), retakeInfo.getExtendDay()), product.getGoodsName(),
                                1, StringUtils.convertSipWonZero(sellPrice), product.getKind(), retakeInfo.getExtendDay(), product.getPmType()
                        );
                        //totalProductPrice += product.getSellPrice();
                        totalPoint += point;
                        if (retakeInfo.getExtendDay() == -1) {
                            totalPoint += product.getPoint();
                        }
                        //동영상 상품 합계
                        if (product.getType() == 1 && retakeInfo.getExtendDay() == -1) {
                            videoPrice += product.getSellPrice();
                            videoProductCnt++;
                        } else if (product.getType() == 1 && retakeInfo.getExtendDay() > -1) {
                            retakePrice += sellPrice;
                        }
                        //학원실강 상품 합계
                        if (product.getType() == 2) {
                            academyPrice += product.getSellPrice();
                        }
                        //도서 상품 합계
                        if (product.getType() == 3) {
                            bookPrice += product.getSellPrice();
                            //deliveryPrice += 2500;
                        }
                        //모의고사 상품 합계
                        if (product.getType() == 4) {
                            examPrice += product.getSellPrice();
                        }
                        orderProductList.add(orderProductListDTO);
                    }

                    if (videoProductCnt == 2) videoPrice = ZianUtils.calcPercent(videoPrice, 10);
                    else if (videoProductCnt > 2) videoPrice = ZianUtils.calcPercent(videoPrice, 20);

                    if (bookPrice > 0 && bookPrice < 30000) {
                        deliveryPrice = 2500;
                    }

                    totalProductPrice = promotionPrice + videoPrice + academyPrice + bookPrice + examPrice + deliveryPrice + StringUtils.convertSipWonZero(retakePrice);
                    //상품총합
                    ProductTotalPriceDTO productTotalPrice = new ProductTotalPriceDTO(
                            totalProductPrice, totalPoint, deliveryPrice
                    );
                    //상품별 상품 금액
                    GroupTotalPriceDTO productGroupPrice = new GroupTotalPriceDTO(
                            videoPrice, academyPrice, promotionPrice, bookPrice, examPrice, deliveryPrice, StringUtils.convertSipWonZero(retakePrice)
                    );
                    //현재 보유 포인트
                    int currentPoint = userMapper.selectUserCurrentPoint(userKey);
                    //주문자 정보
                    TUserVO orderUserInfo = userMapper.selectUserInfoByUserKey(userKey);

                    orderSheetDTO = new OrderSheetDTO(
                            orderProductList, productTotalPrice, productGroupPrice, currentPoint, orderUserInfo
                    );
                }
            }
        }
        return new ApiResultObjectDTO(orderSheetDTO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultCodeDTO getAvailabilityLectureReview(int userKey, int jLecKey) {
        int resultCode = OK.value();

        boolean bl = false;
        if (userKey == 0 && jLecKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            int cnt = orderMapper.selectTBbsDataCountByJLecKey(userKey, jLecKey);
            if (cnt == 0) bl = true;
        }
        return new ApiResultCodeDTO("RESULT", bl, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO addLectureLimitDay(int jLecKey) {
        int resultCode = OK.value();

        if (jLecKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            orderMapper.updateTOderLecLimitDay(jLecKey, 3);
        }
        return new ApiResultCodeDTO("RESULT", jLecKey, resultCode);
    }
}
