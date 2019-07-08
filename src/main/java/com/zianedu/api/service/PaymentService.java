package com.zianedu.api.service;

import com.zianedu.api.define.datasource.PromotionPmType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.mapper.OrderMapper;
import com.zianedu.api.mapper.PaymentMapper;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.utils.GsonUtil;
import com.zianedu.api.utils.StringUtils;
import com.zianedu.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;

import static org.springframework.http.HttpStatus.OK;

@Service
public class PaymentService {

    private static final Pattern COMMA = Pattern.compile(",");

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * T_ORDER 테이블 정보 저장하기
     * @param orderVO
     * @param orderGoodsList
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int saveOrder(OrderVO orderVO, List<OrderGoodsListVO>orderGoodsList) {
        int jKey = 0;
        if (orderVO != null) {
            String uniqueTypeList = null;
            String uniqueExtendDayList = null;

            StringJoiner sj = new StringJoiner(",");
            StringJoiner sj2 = new StringJoiner(",");
            for (OrderGoodsListVO vo : orderGoodsList) {

                if (!COMMA.matcher(String.valueOf(vo.getKind())).matches()) {
                    sj.add(String.valueOf(vo.getKind()));
                }
                uniqueTypeList = sj.toString();

                if (!COMMA.matcher(String.valueOf(vo.getExtendDay())).matches()) {
                    sj2.add(String.valueOf(vo.getExtendDay()));
                }
                uniqueExtendDayList = sj2.toString();
            }
            TOrderVO tOrderVO = new TOrderVO(orderVO, uniqueTypeList, uniqueExtendDayList);
            paymentMapper.insertTOrder(tOrderVO);
            jKey = tOrderVO.getJKey();
        }
        return jKey;
    }

    /**
     * TODO =======> 특별패키지 결제 로직 (2019.07. 08)
     * T_ORDER_GOODS 테이블 정보 저장하기
     * @param jKey
     * @param orderVO
     * @param orderGoodsList
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int saveOrderGoodsList(int jKey, OrderVO orderVO, List<OrderGoodsListVO> orderGoodsList) throws Exception {
        if (jKey > 0) {
            /**
             * TODO T_ORDER_GOODS 테이블 저장하기
             */
            for (OrderGoodsListVO vo : orderGoodsList) {
                TOrderGoodsVO tOrderGoodsVO = null;
                TOrderLecVO tOrderLecVO = null;
                int cartGoodsLinkCartKey = 0;
                //상품 기본정보 조회
                TGoodsVO tGoodsVO = productMapper.selectTGoodsInfo(vo.getGKey());
                TGoodsPriceOptionVO priceOptionVO = productMapper.selectGoodsPriceOptionByPriceKey(vo.getPriceKey());

                TLecVO tLecVO = null;
                //동영상 상품일때
                if (tGoodsVO.getType() == 1 || tGoodsVO.getType() == 2) {
                    /**
                     * TODO 동영상, 학원상품일때
                     */
                    //상품의 강사이름 조회
                    List<String> teacherNameList = productMapper.selectTeacherNameListByVideoProduct(vo.getGKey());
                    //상품의 강좌 조회
                    tLecVO = productMapper.selectTLecInfo(vo.getGKey());

                    String teacherName = "";
                    //선생님 이름 만들기
                    if (teacherNameList.size() > 0) {
                        teacherName = StringUtils.implodeList(",", teacherNameList);
                    }

                    tOrderGoodsVO = new TOrderGoodsVO(
                            jKey, orderVO.getUserKey(), vo.getGKey(), StringUtils.convertLongToInt(vo.getCartKey()), vo.getPriceKey(), priceOptionVO.getPrice(),
                            priceOptionVO.getSellPrice(), priceOptionVO.getPoint(),
                            tGoodsVO.getType(), 0, priceOptionVO.getKind(), vo.getExtendDay(), 0, tLecVO.getExamYear(), tLecVO.getClassGroupCtgKey(),
                            tLecVO.getSubjectCtgKey(), teacherName, tGoodsVO.getName()
                    );
                } else if (tGoodsVO.getType() == 3 || tGoodsVO.getType() == 4) {
                    /**
                     * TODO 도서, 모의고사상품일때
                     */
                    tOrderGoodsVO = new TOrderGoodsVO(
                            jKey, orderVO.getUserKey(), vo.getGKey(), StringUtils.convertLongToInt(vo.getCartKey()), vo.getPriceKey(), priceOptionVO.getPrice(),
                            priceOptionVO.getSellPrice(), priceOptionVO.getPoint(),
                            tGoodsVO.getType(), 0, priceOptionVO.getKind(), vo.getExtendDay(), 0, 0, 0,
                            0, "", tGoodsVO.getName()
                    );
                } else if (tGoodsVO.getType() == 5) {
                    /**
                     * TODO 패키지 상품일때
                     */
                    //자유패키지일때
                    if (vo.getPmType() == 2) {
                        cartGoodsLinkCartKey = StringUtils.convertLongToInt(vo.getCartKey());
                        TCartVO cartVO = orderMapper.selectCartInfoByCartKey(StringUtils.convertLongToInt(vo.getCartKey()));

                        tOrderGoodsVO = new TOrderGoodsVO(
                                jKey, orderVO.getUserKey(), vo.getGKey(), StringUtils.convertLongToInt(vo.getCartKey()), vo.getPriceKey(), cartVO.getLinkPrice(),
                                cartVO.getLinkSellPrice(), priceOptionVO.getPoint(),
                                tGoodsVO.getType(), vo.getPmType(), priceOptionVO.getKind(), vo.getExtendDay(), cartVO.getCtgKey(), 0, 0,
                                0, "", PromotionPmType.FREE_PACKAGE.getPromotionPmStr()
                        );
                    }
                }
                //T_ORDER_GOODS 결제 상품 저장
                paymentMapper.insertTOrderGoods(tOrderGoodsVO);
                int jGKey = tOrderGoodsVO.getJGKey();

                //결제완료이고 동영상상품이면 T_ORDER_LEC 테이블에 정보 저장
                if (orderVO.getPayStatus() == 2 && tGoodsVO.getType() == 1) {
                    /**
                     * TODO 동영상 상품이 정상결제일때 필요한 정보 저장
                     */
                    tOrderLecVO = new TOrderLecVO(
                            jGKey, 0, "", tLecVO.getLimitDay(), tLecVO.getMultiple()
                    );
                    if (tOrderLecVO != null) {
                        paymentMapper.insertTOrderLec(tOrderLecVO);
                    }
                } else if (orderVO.getPayStatus() == 2
                            && tGoodsVO.getType() == 5
                            && vo.getPmType() == PromotionPmType.FREE_PACKAGE.getPromotionPmKey()) {    //결제완료이고 자유패키지상품이면
                    /**
                     * TODO 자유패키지 상품이 정상결제일때 필요한 정보 저장
                     */
                    TOrderPromotionVO promotionVO = new TOrderPromotionVO(jGKey, PromotionPmType.FREE_PACKAGE.getPromotionPmKey());
                    orderMapper.insertTOrderPromotion(promotionVO);

                    List<TCartLinkGoodsVO> cartLinkGoodsList = orderMapper.selectCartLinkGoodsList(cartGoodsLinkCartKey);
                    if (cartLinkGoodsList.size() > 0) {
                        for (TCartLinkGoodsVO linkGoodsVO : cartLinkGoodsList) {
                            TGoodsPriceOptionVO optionVO = productMapper.selectGoodsPriceOptionByPriceKey(linkGoodsVO.getPriceKey());

                            TOrderGoodsVO tOrderGoodsVO2 = new TOrderGoodsVO(
                                    jKey, orderVO.getUserKey(), linkGoodsVO.getGKey(), 0, linkGoodsVO.getPriceKey(), 0,
                                    0, 0, 1, 0, optionVO.getKind(), -1, 0, 0, 0,
                                    0, "", "", promotionVO.getJPmKey()
                            );
                            //T_ORDER_GOODS 결제 상품 저장
                            paymentMapper.insertTOrderGoods(tOrderGoodsVO2);

                            TLecVO lecVO = productMapper.selectTLecInfo(linkGoodsVO.getGKey());
                            tOrderLecVO = new TOrderLecVO(
                                    tOrderGoodsVO2.getJGKey(), 0, "", lecVO.getLimitDay(), lecVO.getMultiple()
                            );
                            paymentMapper.insertTOrderLec(tOrderLecVO);

                        }
                    }
                }
            }
        }
        return 0;
    }
}
