package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.NiceService;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/nice")
@RestController
public class NiceController {

    @Autowired
    private NiceService niceService;

    @RequestMapping(value = "/getSEncData", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("핸드폰 본인인증에 필요한 암호화값 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sReturnUrl", value = "성공시 이동될 URL", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "sErrorUrl", value = "실패시 이동될 URL", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultObjectDTO getSEncData(@RequestParam(value = "sReturnUrl") String sReturnUrl,
                                          @RequestParam(value = "sErrorUrl") String sErrorUrl) {
        return niceService.getNiceSEncData(sReturnUrl, sErrorUrl);
    }


}
