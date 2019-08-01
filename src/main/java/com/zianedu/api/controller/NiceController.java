package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.NiceService;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/nice")
@RestController
public class NiceController {

    @Autowired
    private NiceService niceService;

    @RequestMapping(value = "/getSEncData", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("핸드폰 본인인증에 필요한 암호화값 가져오기")
    public ApiResultObjectDTO getSEncData() {
        return niceService.getNiceSEncData();
    }

    @RequestMapping(value = "/checkPlusSuccess")
    public ApiResultCodeDTO niceCheckPlusSuccess(HttpServletRequest request) {
        niceService.resultUserIdentify(request);
        return null;
    }

    @RequestMapping(value = "/checkPlusFail")
    public ApiResultCodeDTO niceCheckPlusFail() {
        return null;
    }
}
