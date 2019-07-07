package com.zianedu.api.service;

import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultCodeDTO;
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
            TOrderGoodsVO tOrderGoodsVO = null;
            TOrderLecVO tOrderLecVO = null;
            /**
             * TODO T_ORDER_GOODS 테이블 저장하기
             */
            for (OrderGoodsListVO vo : orderGoodsList) {
                //상품 기본정보 조회
                TGoodsVO tGoodsVO = productMapper.selectTGoodsInfo(vo.getGKey());

                //동영상 상품일때
                if (tGoodsVO.getType() == 1) {
                    //상품의 강사이름 조회
                    List<String> teacherNameList = productMapper.selectTeacherNameListByVideoProduct(vo.getGKey());
                    //상품의 강좌 조회
                    TLecVO tLecVO = productMapper.selectTLecInfo(vo.getGKey());
                    TGoodsPriceOptionVO priceOptionVO = productMapper.selectGoodsPriceOptionByPriceKey(vo.getPriceKey());
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
                    //결제완료이면 T_ORDER_LEC 테이블에 정보 저장
                    if (orderVO.getPayStatus() == 2) {
                        int jGKey = tOrderGoodsVO.getJGKey();
                        tOrderLecVO = new TOrderLecVO(
                                jGKey, 0, "", tLecVO.getLimitDay(), tLecVO.getMultiple()
                        );
                    }
                //학원상품일때
                } else if (tGoodsVO.getType() == 2) {
                    /**
                     * TODO 학원상품일때
                     */
                } else if (tGoodsVO.getType() == 3) {
                    /**
                     * TODO 도서 상품일때
                     */
                } else if (tGoodsVO.getType() == 4) {
                    /**
                     * TODO 모의고사 상품일때
                     */
                } else if (tGoodsVO.getType() == 5) {
                    /**
                     * TODO 패키지 상품일때
                     */
                }
                //T_ORDER_GOODS 결제 상품 저장
                paymentMapper.insertTOrderGoods(tOrderGoodsVO);

                if (tOrderLecVO != null) {
                    /**
                     * TODO 동영상 상품이고 결제가 완료되었을때 T_ORDER_LEC 테이블에 정보 저장
                     */
                }
            }
        }
        return 0;
    }
}
