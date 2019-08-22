package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.service.EmailSendService;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/email")
@RestController
public class EmailSendController {

    @Autowired
    private EmailSendService emailSendService;

    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("이메일 발송")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recipient", value = "받는 사람의 메일주소", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "subject", value = "제목", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "body", value = "내용", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultCodeDTO sendEmail(@RequestParam(value = "recipient") String recipient,
                                      @RequestParam(value = "subject") String subject,
                                      @RequestParam(value = "body") String body) throws Exception {
        emailSendService.sendEmail(recipient, subject, body);
        return null;
    }
}
