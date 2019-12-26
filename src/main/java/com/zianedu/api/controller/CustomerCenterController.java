package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.service.CustomerCenterService;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/customerCenter")
public class CustomerCenterController {

    @Autowired
    private CustomerCenterService customerCenterService;

    @RequestMapping(value = "/reserveConsult", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("상담예약하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reserveDate", value = "예약월(YYYY-MM-DD)", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "reserveTimeKey", value = "예약시간 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "reserveType", value = "예약종류 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "reserveLocation", value = "학원관(1관:1, 2관:2)", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "userName", value = "예약자명", dataType = "string", paramType = "query", required = true),

    })
    public ApiResultCodeDTO saveBoardFileList(@RequestParam("bbsKey") int bbsKey,
                                              @RequestParam("fileName") String fileName) {
        customerCenterService.reserveConsult()
    }
}
