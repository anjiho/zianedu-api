package com.zianedu.api.controller;

import com.google.gson.JsonArray;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.repository.PaymentRepository;
import com.zianedu.api.service.PaymentService;
import com.zianedu.api.utils.GsonUtil;
import com.zianedu.api.utils.ZianApiUtils;
import com.zianedu.api.vo.OrderGoodsListVO;
import com.zianedu.api.vo.OrderVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/saveCreditCardPayment", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("신용카드 결제 결과 저장하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자키", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "price", value = "상품총 가격", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "pricePay", value = "결제할 가격", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "point", value = "적립될 총 포인트", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "discountPoint", value = "사용한 포인트", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "deliveryPrice", value = "배송비", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "payStatus", value = "결제상태(0 : 입금예정, 1 : 결제대기, 2 : 결제완료, 8 : 결제취소, 9 : 주문취소, 10 : 결제실패", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "cardCode", value = "이니시스 card_code ", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "bank", value = "자인에듀 입금계좌 은행명", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "bankAccount", value = "자인에듀 입금계좌 은행 계좌번호", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "depositUser", value = "결제자이름", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "deliveryName", value = "배송자이름", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "deliveryTelephone", value = "배송자 전화번호", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "deliveryTelephoneMobile", value = "배송자 핸드폰번호", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "deliveryZipcode", value = "우편번호", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "deliveryAddress", value = "구주소명", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "deliveryAddressRoad", value = "도로주소명", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "deliveryAddressAdd", value = "상세주소", dataType = "String", paramType = "query", required = false),
            //@ApiImplicitParam(name = "uniqueExtendDayList", value = "상품 유니크한 재수강날짜 리스트( 재수강일수, 기본 구매일때는 -1, 형식 >> '-1,-1,-1,..') ", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "payKey", value = "이니시스 결제 키값(T_PAY_INIPAY.pay_key)", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "isMobile", value = "모바일여부", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "orderGoodsList", value = "구매한 상품정보(json list string)", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultCodeDTO savePaymentResult(@ModelAttribute OrderVO orderVO) throws Exception {
        paymentRepository.savePaymentResult(orderVO);
        return null;
    }
}
