package com.zianedu.api.controller;

import com.google.gson.JsonArray;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.repository.PaymentRepository;
import com.zianedu.api.service.PaymentService;
import com.zianedu.api.utils.GsonUtil;
import com.zianedu.api.utils.Util;
import com.zianedu.api.utils.ZianApiUtils;
import com.zianedu.api.utils.ZianUtils;
import com.zianedu.api.vo.OrderGoodsListVO;
import com.zianedu.api.vo.OrderVO;
import com.zianedu.api.vo.TPayInipayVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/saveInipayInfo", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("이니페이 결과 저장하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid", value = "거래번호", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "resultcode", value = "결과코드", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "resultmsg", value = "결과 메시지", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "paymethod", value = "결제방법", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "moid", value = "상점주문번호. 결제 요청시 oid 필드에 설정된값(J_ID)", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "totprice", value = "거래금액", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "appldate", value = "승인일자(YYYYMMDD)", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "appltime", value = "승인시간(hh24miss)", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "applnum", value = "승인번호(결제수단에 따라 미전송)", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "cardQuota", value = "카드 할부기간", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "cardInterest", value = "할부여부('1'이면 무이자할부)", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "cardNum", value = "신용카드번호", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "cardCode", value = "카드사 코드", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "cardBankcode", value = "카드발급사", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "eventcode", value = "이벤트코드(카드 할부 및 행사 적용 코드)", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "cardApplprice", value = "카드승인금액", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "ocbPayprice", value = "OK CASHBAG 포인트지불금액", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "acctBankcode", value = "은행코드", dataType = "String", paramType = "query", required = false),

            @ApiImplicitParam(name = "vactNum", value = "입금할 계좌번호", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "vactDate", value = "입금할 날짜", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "vactTime", value = "입금할 시간", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "vactBankCode", value = "입금할 은행코드", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "vactName", value = "입금할 상점명", dataType = "String", paramType = "query", required = false),

            @ApiImplicitParam(name = "acctBankcode", value = "은행코드", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "acctBankcode", value = "은행코드", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "acctBankcode", value = "은행코드", dataType = "String", paramType = "query", required = false),

            @ApiImplicitParam(name = "resulterrorcode", value = "에러결과코드", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "isMobile", value = "모바일여부(0:PC, 1:모바일)", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultCodeDTO saveInipayResult(@ModelAttribute TPayInipayVO tPayInipayVO) {
        return paymentService.saveInipayResult(tPayInipayVO);
    }

    @RequestMapping(value = "/savePaymentInfo", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("결제 결과 저장하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jId", value = "주문 아이디(getJId API 에서 값을 가져온다)", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "userKey", value = "사용자키", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "price", value = "상품총 가격", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "pricePay", value = "결제할 가격", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "payType", value = "결제종류", dataType = "int", paramType = "query", required = true),
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
            @ApiImplicitParam(name = "depositDate", value = "무통장입금 예정일", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "orderGoodsList", value = "구매할 상품정보( [{ 'priceKey': 100924,   'cartKey': 388144,  'kind': 0,    'gKey': 101400,         'extendDay': -1,  'pmType':2 }, .... ])", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultCodeDTO savePaymentResult(@ModelAttribute OrderVO orderVO) throws Exception {
        return paymentRepository.savePaymentResult(orderVO);
    }

    @RequestMapping(value = "/getJId", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("주문키값 가져오기")
    public ApiResultCodeDTO getJId() {
        return new ApiResultCodeDTO("J_ID", ZianUtils.getJId(), OK.value());
    }
}
