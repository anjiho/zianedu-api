package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.service.MyPageService;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/myPage")
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    @RequestMapping(value = "/getAcademySignUp/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 학원수강내역")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getUserAcademySignUpList(@PathVariable("userKey") int userKey) {
        return myPageService.getUserAcademySignUpList(userKey);
    }

    @RequestMapping(value = "/getPromotionSignUp/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 수강중인강좌(프로모션)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "deviceType", value = "기기종류('MOBILE':모바일, 'PC':데스크탑) ", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultListDTO getUserPromotionOnlineSignUpList(@PathVariable("userKey") int userKey,
                                                             @RequestParam("deviceType") String deviceType) {
        return myPageService.getUserPromotionOnlineSignUpList(userKey, deviceType);
    }


}
