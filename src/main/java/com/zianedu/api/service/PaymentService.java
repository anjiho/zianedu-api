package com.zianedu.api.service;

import com.zianedu.api.define.datasource.PointDescType;
import com.zianedu.api.define.datasource.PromotionPmType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.mapper.OrderMapper;
import com.zianedu.api.mapper.PaymentMapper;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.utils.GsonUtil;
import com.zianedu.api.utils.StringUtils;
import com.zianedu.api.vo.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private ExamService examService;

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

            /**
             * TODO 포인트 적립과 사용내역 저장하기
             */
            if (jKey > 0 && !"".equals(orderVO.getJId())) {
                if (orderVO.getPayStatus() == 2) {
                    //물건을 사서 마일리지를 획득할때
                    if (orderVO.getPoint() > 0) {
                        this.injectUserPoint("P", orderVO.getUserKey(), orderVO.getPoint(), jKey, orderVO.getJId());
                    }
                    //물건을 사서 마일이지를 사용할때
                    if (orderVO.getDiscountPoint() > 0) {
                        this.injectUserPoint("M", orderVO.getUserKey(), orderVO.getDiscountPoint(), jKey, orderVO.getJId());
                    }
                }
            }

        }
        return jKey;
    }

    /**
     * T_ORDER_GOODS 테이블 정보 저장하기
     * @param jKey
     * @param orderVO
     * @param orderGoodsList
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrderGoodsList(int jKey, OrderVO orderVO, List<OrderGoodsListVO> orderGoodsList) throws Exception {
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
                    cartGoodsLinkCartKey = StringUtils.convertLongToInt(vo.getCartKey());
                    //자유패키지일때
                    if (vo.getPmType() == PromotionPmType.FREE_PACKAGE.getPromotionPmKey()) {

                        TCartVO cartVO = orderMapper.selectCartInfoByCartKey(StringUtils.convertLongToInt(vo.getCartKey()));
                        tOrderGoodsVO = new TOrderGoodsVO(
                                jKey, orderVO.getUserKey(), vo.getGKey(), StringUtils.convertLongToInt(vo.getCartKey()), vo.getPriceKey(), cartVO.getLinkPrice(),
                                cartVO.getLinkSellPrice(), priceOptionVO.getPoint(),
                                tGoodsVO.getType(), vo.getPmType(), priceOptionVO.getKind(), vo.getExtendDay(), cartVO.getCtgKey(), 0, 0,
                                0, "", PromotionPmType.FREE_PACKAGE.getPromotionPmStr()
                        );
                    //특별패키지일때
                    } else if (vo.getPmType() == PromotionPmType.SPECIAL_PACKAGE.getPromotionPmKey()) {
                        TPromotionVO promotionVO = productMapper.selectTPromotionInfoByGKey(vo.getGKey());
                        tOrderGoodsVO = new TOrderGoodsVO(
                                jKey, orderVO.getUserKey(), vo.getGKey(), StringUtils.convertLongToInt(vo.getCartKey()), vo.getPriceKey(), priceOptionVO.getPrice(),
                                priceOptionVO.getSellPrice(), priceOptionVO.getPoint(),
                                tGoodsVO.getType(), vo.getPmType(), priceOptionVO.getKind(), vo.getExtendDay(), 833, promotionVO.getExamYear(), promotionVO.getClassGroupCtgKey(),
                                0, "", tGoodsVO.getName()
                        );
                    //지안패스일때
                    } else if (vo.getPmType() == PromotionPmType.ZIAN_PASS.getPromotionPmKey()) {
                        TPromotionVO promotionVO = productMapper.selectTPromotionInfoByGKey(vo.getGKey());
                        int zianPassPageKey = productMapper.selectZianPassPageKeyFromGKey(vo.getGKey());
                        TCategoryGoodsVO categoryGoodsVO = productMapper.selectTCategoryGoods(zianPassPageKey);

                        tOrderGoodsVO = new TOrderGoodsVO(
                                jKey, orderVO.getUserKey(), vo.getGKey(), StringUtils.convertLongToInt(vo.getCartKey()), vo.getPriceKey(), priceOptionVO.getPrice(),
                                priceOptionVO.getSellPrice(), priceOptionVO.getPoint(),
                                tGoodsVO.getType(), vo.getPmType(), priceOptionVO.getKind(), vo.getExtendDay(), categoryGoodsVO.getCtgKey(), promotionVO.getExamYear(), promotionVO.getClassGroupCtgKey(),
                                0, "", tGoodsVO.getName()
                        );
                    //연간회원제일때
                    } else if (vo.getPmType() == PromotionPmType.YEAR_MEMBER.getPromotionPmKey()) {
                        TPromotionVO promotionVO = productMapper.selectTPromotionInfoByGKey(vo.getGKey());

                        tOrderGoodsVO = new TOrderGoodsVO(
                                jKey, orderVO.getUserKey(), vo.getGKey(), StringUtils.convertLongToInt(vo.getCartKey()), vo.getPriceKey(), priceOptionVO.getPrice(),
                                priceOptionVO.getSellPrice(), priceOptionVO.getPoint(),
                                tGoodsVO.getType(), vo.getPmType(), priceOptionVO.getKind(), vo.getExtendDay(), 0, promotionVO.getExamYear(), promotionVO.getClassGroupCtgKey(),
                                0, "", tGoodsVO.getName()
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
                } else if (orderVO.getPayStatus() == 2 && tGoodsVO.getType() == 5) {    //결제완료이고 자유패키지상품이면
                    /**
                     * TODO 자유패키지, 특별패키지 상품이 정상결제일때 필요한 동영상 강좌정보 저장
                     */
                    if (vo.getPmType() == PromotionPmType.FREE_PACKAGE.getPromotionPmKey() || vo.getPmType() == PromotionPmType.SPECIAL_PACKAGE.getPromotionPmKey()
                        || vo.getPmType() == PromotionPmType.ZIAN_PASS.getPromotionPmKey() || vo.getPmType() == PromotionPmType.YEAR_MEMBER.getPromotionPmKey()) {

                        TOrderPromotionVO promotionVO = new TOrderPromotionVO(jGKey, vo.getPmType());
                        orderMapper.insertTOrderPromotion(promotionVO);     //T_ORDER_PROMOTION 테이블 저장

                        List<TCartLinkGoodsVO> cartLinkGoodsList = new ArrayList<>();
                        if (vo.getPmType() == PromotionPmType.FREE_PACKAGE.getPromotionPmKey()) {   // 자유패키지일때
                            cartLinkGoodsList = orderMapper.selectCartLinkGoodsList(cartGoodsLinkCartKey);
                        } else if (vo.getPmType() == PromotionPmType.SPECIAL_PACKAGE.getPromotionPmKey()) {    //특별패키지
                            cartLinkGoodsList = productMapper.selectGoodsPriceOptionListBySpecialPackage(vo.getKind(), vo.getGKey());
                        } else if (vo.getPmType() == PromotionPmType.ZIAN_PASS.getPromotionPmKey() || vo.getPmType() == PromotionPmType.YEAR_MEMBER.getPromotionPmKey()) {    //지안패스, 연간회원제일때
                            //cartLinkGoodsList = productMapper.selectGoodsPriceOptionListBySpecialPackage(0, vo.getGKey());
                            cartLinkGoodsList = new ArrayList<>();
                        }

                        if (cartLinkGoodsList.size() > 0) {
                            for (TCartLinkGoodsVO linkGoodsVO : cartLinkGoodsList) {
                                TGoodsPriceOptionVO optionVO = productMapper.selectGoodsPriceOptionByPriceKey(linkGoodsVO.getPriceKey());
                                TOrderGoodsVO tOrderGoodsVO2 = null;

                                tOrderGoodsVO2 = new TOrderGoodsVO(
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
                                //T_ORDER_LEC 저장
                                paymentMapper.insertTOrderLec(tOrderLecVO);

                            }
                        } else {
                            //지안패스일때
                            if (vo.getPmType() == PromotionPmType.ZIAN_PASS.getPromotionPmKey()) {
                                List<Integer>gKeyList = productMapper.selectGoodsPriceOptionListByZianPass(vo.getGKey());

                                for (Integer gKey : gKeyList) {
                                    /**
                                     *kind 102번은 안들어간다
                                     */
                                    int priceOptionCount = productMapper.selectTGoodsPriceOptionCount(gKey);
                                    //T_GOODS_PRICE_OPTION 개수가 1개이상이고 3개 이하일때
                                    if (priceOptionCount > 1 && priceOptionCount < 4) {
                                        List<TGoodsPriceOptionVO> priceOptionList = productMapper.selectGoodsPriceOptionByGKey(gKey);
                                        for (TGoodsPriceOptionVO optionVO : priceOptionList) {
                                            // KIND는 100, 101의 상품가격만 저장
                                            if (optionVO.getKind() == 100 || optionVO.getKind() == 101) {
                                                TGoodsVO goodsInfo = productMapper.selectTGoodsInfo(optionVO.getGKey());
                                                TOrderGoodsVO tOrderGoodsVO3  = new TOrderGoodsVO(
                                                        jKey, orderVO.getUserKey(), optionVO.getGKey(), 0, optionVO.getPriceKey(), 0,
                                                        0, 0, 1, 0, optionVO.getKind(), -1, 0, 0, 0,
                                                        0, "", goodsInfo.getName(), promotionVO.getJPmKey()
                                                );
                                                paymentMapper.insertTOrderGoods(tOrderGoodsVO3);

                                                int limitDay = (vo.getKind() * 31);
                                                float multiple = 0.0f;
                                                tOrderLecVO = new TOrderLecVO(
                                                        PromotionPmType.ZIAN_PASS.getPromotionPmKey(), tOrderGoodsVO3.getJGKey(), 0, "", limitDay, multiple
                                                );
                                                //T_ORDER_LEC 저장
                                                paymentMapper.insertTOrderLec(tOrderLecVO);
                                            }
                                        }
                                    //T_GOODS_PRICE_OPTION 개수가 1개일떄 (KIND 102만 있을떄)
                                    } else if (priceOptionCount == 1) {
                                        List<Integer>kindArr = new ArrayList<>();
                                        kindArr.add(100);
                                        kindArr.add(101);

                                        for (Integer kind : kindArr) {
                                            //priceKey는 KIND 102의 priceKey로  kind 100, 101 일괄 저장
                                            TGoodsVO goodsInfo = productMapper.selectTGoodsInfo(gKey);
                                            TGoodsPriceOptionVO tGoodsPriceOptionVO = productMapper.selectGoodsPriceOptionByGKeySingle(gKey);
                                            TOrderGoodsVO tOrderGoodsVO3 = new TOrderGoodsVO(
                                                    jKey, orderVO.getUserKey(), gKey, 0, tGoodsPriceOptionVO.getPriceKey(), 0,
                                                    0, 0, 1, 0, kind, -1, 0, 0, 0,
                                                    0, "", goodsInfo.getName(), promotionVO.getJPmKey()
                                            );
                                            paymentMapper.insertTOrderGoods(tOrderGoodsVO3);

                                            int limitDay = (vo.getKind() * 31);
                                            float multiple = 0.0f;
                                            tOrderLecVO = new TOrderLecVO(
                                                    PromotionPmType.ZIAN_PASS.getPromotionPmKey(), tOrderGoodsVO3.getJGKey(), 0, "", limitDay, multiple
                                            );
                                            //T_ORDER_LEC 저장
                                            paymentMapper.insertTOrderLec(tOrderLecVO);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (orderVO.getPayStatus() == 2 && tGoodsVO.getType() == 4) {
                    String onOff = "";
                    TLinkKeyVO linkKeyVO = productMapper.selectExamOnOffKey(vo.getGKey());
                    if (linkKeyVO.getResType() == 2) onOff = "2";
                    else if (linkKeyVO.getResType() == 3) onOff = "1";

                    examService.injectUserExamInfo(linkKeyVO.getResKey(), orderVO.getUserKey(), onOff, jGKey);

                }
            }
        }
    }

    /**
     * 이니시스 결제 결과 저장하기
     * @param tPayInipayVO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO saveInipayResult(TPayInipayVO tPayInipayVO) {
        int resultCode = OK.value();

        int payKey = 0;
        if (tPayInipayVO == null) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            paymentMapper.insertTPayInipay(tPayInipayVO);
            payKey = tPayInipayVO.getPayKey();
        }
        return new ApiResultCodeDTO("PAY_KEY", payKey, resultCode);
    }

    /**
     * 마일리지 저장하기
     * @param injectType
     * @param userKey
     * @param point
     * @param jKey
     * @param jId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void injectUserPoint(String injectType, int userKey, int point, int jKey, String jId) {
        if (userKey == 0 && "".equals(injectType) && point == 0) return;

        int type = 0;
        String descType = "";
        //상품구매일때 마일리지 사용
        if ("M".equals(injectType)) {
            point = -point;
            descType = PointDescType.PRODUCT_BUY_USE.getPointDescTypeKey();
        //상품구매일때 마일리지 획득
        } else if ("P".equals(injectType)) {
            type = 1;
            descType = PointDescType.PRODUCT_BUY_GAIN.getPointDescTypeKey();
        //회원가입일때 마일리지 획득
        } else if ("U".equals(injectType)) {
            type = 1;
            descType = PointDescType.MEMBER_REG.getPointDescTypeKey();
        }
        TPointVO tPointVO = new TPointVO(userKey, type, point, jKey, jId, Integer.parseInt(descType));
        paymentMapper.insertTPoint(tPointVO);
    }
}
