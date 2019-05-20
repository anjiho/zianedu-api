package com.zianedu.api.controller;

import com.zianedu.api.dto.*;
import com.zianedu.api.service.MyPageService;
import com.zianedu.api.service.OrderService;
import com.zianedu.api.utils.GsonUtil;
import com.zianedu.api.utils.ZianApiUtils;
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

    protected final Logger logger = LoggerFactory.getLogger(OrderController.class);

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

    @RequestMapping(value = "/deleteCartInfo", method = RequestMethod.DELETE, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("장바구니 리스트 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cartKey", value = "장바구니 키", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultCodeDTO deleteCartInfo(@RequestParam("cartKey") int cartKey) {
        return orderService.deleteCartInfo(cartKey);
    }

    @RequestMapping(value = "/getOrderSheetInfo/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("주문서 작성 > 장바구니에 담은 상품에서 주문서 작성으로 갈때")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "cartKeys", value = "장바구니 키", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultObjectDTO getOrderSheetInfoFromCart(@PathVariable("userKey") int userKey,
                                                @RequestParam("cartKeys") String cartKeys) {
        Integer[] cartKeyArray = GsonUtil.convertToIntegerArrayFromString(cartKeys);
        return orderService.getOrderSheetFromCart(userKey, cartKeyArray);
    }

}
