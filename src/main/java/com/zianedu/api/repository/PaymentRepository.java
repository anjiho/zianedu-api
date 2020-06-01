package com.zianedu.api.repository;

import com.google.gson.JsonArray;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.service.PaymentService;
import com.zianedu.api.service.OrderService;
import com.zianedu.api.utils.GsonUtil;
import com.zianedu.api.utils.StringUtils;
import com.zianedu.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Repository
public class PaymentRepository {

    @Autowired
    private PaymentService paymentService;
    private OrderService orderService;

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO savePaymentResult(OrderVO orderVO) throws Exception {
        int resultCode = OK.value();

        int jKey = 0;
        /*int jIdChk = 0;
        if(orderVO.getJId() != null || !"".equals(orderVO.getJId())) {
            String jId = orderVO.getJId();
            jIdChk = orderService.selectJIdChk(jId);
        }*/

            if (orderVO == null) {
                resultCode = ZianErrCode.BAD_REQUEST.code();
            } else {
                JsonArray orderGoodsListJson = GsonUtil.convertStringToJsonArray(orderVO.getOrderGoodsList());
                List<OrderGoodsListVO> orderGoodsList = GsonUtil.getObjectFromJsonArray(orderGoodsListJson, OrderGoodsListVO.class);

                //T_ORDER 테이블 정보 저장하기
                jKey = paymentService.saveOrder(orderVO, orderGoodsList);
                if (jKey > 0) {
                    /**
                     * TODO T_ORDER_GOODS 테이블 저장하기
                     */
                    paymentService.saveOrderGoodsList(jKey, orderVO, orderGoodsList);
                }
            }


        return new ApiResultCodeDTO("J_KEY", jKey, resultCode);
    }

}
