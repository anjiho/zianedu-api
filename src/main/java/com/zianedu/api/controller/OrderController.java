package com.zianedu.api.controller;

import com.zianedu.api.dto.*;
import com.zianedu.api.service.MyPageService;
import com.zianedu.api.service.OrderService;
import com.zianedu.api.utils.GsonUtil;
import com.zianedu.api.utils.ZianApiUtils;
import com.zianedu.api.vo.SaveCartVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/getUserCartInfo/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("장바구니 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultObjectDTO getUserCartInfo(@PathVariable("userKey") int userKey) {
        return orderService.getUserCartList(userKey);
    }

    @RequestMapping(value = "/deleteCartInfo", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("장바구니 리스트 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cartKeys", value = "장바구니 키", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultCodeDTO deleteCartInfo(@RequestParam("cartKeys") String cartKeys) {
        Integer[] cartKeyArray = GsonUtil.convertToIntegerArrayFromString(cartKeys);
        return orderService.deleteCartInfo(cartKeyArray);
    }

    @RequestMapping(value = "/getOrderSheetInfoFromCart/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("주문서 작성 > 장바구니에 담은 상품에서 주문서 작성으로 갈때")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "cartKeys", value = "장바구니 키 >> [1234,1234,...]", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultObjectDTO getOrderSheetInfoFromCart(@PathVariable("userKey") int userKey,
                                                        @RequestParam("cartKeys") String cartKeys) {
        Integer[] cartKeyArray = GsonUtil.convertToIntegerArrayFromString(cartKeys);
        return orderService.getOrderSheetFromCart(userKey, cartKeyArray);
    }

    @RequestMapping(value = "/getOrderSheetInfoFromImmediately/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("주문서 작성 > 일반 상품 > '바로신청' 버튼으로 주문서 작성으로 갈때")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "gKeys", value = "상품 키 >> [1234,1234,...]", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultObjectDTO getOrderSheetFromImmediatelyBuy(@PathVariable("userKey") int userKey,
                                                        @RequestParam("gKeys") String gKeys) {
        Integer[] gKeyArray = GsonUtil.convertToIntegerArrayFromString(gKeys);
        return orderService.getOrderSheetFromImmediatelyBuy(userKey, gKeyArray);
    }

    @RequestMapping(value = "/getOrderSheetInfoFromImmediatelyAtFree/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("주문서 작성 > 자유 패키지 > '바로신청' 버튼으로 주문서 작성으로 갈때")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "gKeys", value = "상품 키 >> [1234,1234,...]", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultObjectDTO getOrderSheetFromImmediatelyBuyAtFree(@PathVariable("userKey") int userKey,
                                                              @RequestParam("gKeys") String gKeys) {
        Integer[] gKeyArray = GsonUtil.convertToIntegerArrayFromString(gKeys);
        return orderService.getOrderSheetFromImmediatelyBuyAtFree(userKey, gKeyArray);
    }

    @RequestMapping(value = "/getUserOrderDeliveryInfo/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("주문배송조회 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "startDate", value = "검색 시작일", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "endDate", value = "검색 종료일", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultListDTO getUserOrderDeliveryList(@PathVariable("userKey") int userKey,
                                                     @RequestParam("startDate") String startDate,
                                                     @RequestParam("endDate") String endDate) {
        return orderService.getUserOrderDeliveryList(userKey, startDate, endDate);
    }

    @RequestMapping(value = "/getUserOrderDeliveryInfo/{userKey}/{jKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("주문배송 상세정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "jKey", value = "주문 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultObjectDTO getUserOrderDeliveryDetailInfo(@PathVariable("userKey") int userKey,
                                                            @PathVariable("jKey") int jKey) {
        return orderService.getUserOrderDeliveryDetailInfo(userKey, jKey);
    }

    @RequestMapping(value = "/getUserPointInfo/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 나의 마일리지")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
    })
    public ApiResultObjectDTO getUserPointInfo(@PathVariable("userKey") int userKey) {
        return orderService.getUserPointList(userKey);
    }

    @RequestMapping(value = "/saveCart", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("장바구니 담기(자유패키지 외 상품)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "saveCartInfo", value = "저장할 카트 정보", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultCodeDTO saveCart(@RequestParam("saveCartInfo") String saveCartInfo) {
        return orderService.saveCart(saveCartInfo);
    }

    @RequestMapping(value = "/saveCartFreePackage", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("장바구니 담기(자유패키지)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "gKeys", value = "상품 키 >> [1234,1234,...]", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultCodeDTO saveCartFreePackage(@RequestParam("userKey") int userKey,
                                                  @RequestParam("gKeys") String gKeys) {
        Integer[] gKeyArray = GsonUtil.convertToIntegerArrayFromString(gKeys);
        return orderService.saveCartFreePackage(userKey, gKeyArray);
    }
}
