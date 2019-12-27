package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.service.CustomerCenterService;
import com.zianedu.api.utils.ZianApiUtils;
import com.zianedu.api.vo.TConsultReserveVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customerCenter")
public class CustomerCenterController {

    @Autowired
    private CustomerCenterService customerCenterService;

    @RequestMapping(value = "/reserveConsult", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("상담예약하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "reserveDate", value = "예약월(YYYY-MM-DD)", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "reserveTimeKey", value = "예약시간 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "reserveType", value = "예약종류 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "reserveLocation", value = "학원관(1관:1, 2관:2)", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "userName", value = "예약자명", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "mobileNumber", value = "핸드폰번호", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "emailAddress", value = "이메일주소", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "ctgKey1", value = "분류1", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "ctgKey2", value = "분류2", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "ctgKey3", value = "분류3", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "reserveContents", value = "상담요청내용", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultCodeDTO saveBoardFileList(@ModelAttribute TConsultReserveVO tConsultReserveVO) {
        return customerCenterService.reserveConsult(tConsultReserveVO);
    }
}
