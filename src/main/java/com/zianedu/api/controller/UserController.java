package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.UserService;
import com.zianedu.api.utils.ZianApiUtils;
import com.zianedu.api.vo.TUserVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("로그인")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 아이디", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "userPwd", value = "사용자 비밀번호", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultObjectDTO loginUser(@RequestParam("userId") String userId, @RequestParam("userPwd") String userPwd) {
        return userService.loginUser(userId, userPwd);
    }

    @RequestMapping(value = "/checkDuplicate", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("회원 아이디 중복체크")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 아이디", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultCodeDTO checkDuplicateUser(@RequestParam("userId") String userId) {
        return userService.checkDuplicateUser(userId);
    }

    @RequestMapping(value = "/reg", method = {RequestMethod.POST}, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("회원가입")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "이름", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "gender", value = "성별(0:남, 1:여)", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "userId", value = "아이디", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "pwd", value = "비밀번호", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "email", value = "이메일", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "telephoneMobile", value = "휴대전화", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "zipcode", value = "우편번호", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "addressRoad", value = "신주소 명", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "addressNumber", value = "구주소명", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "address", value = "상세주소", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "certCode", value = "개인인증코드", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "interestCtgKey0", value = "준비직렬", dataType = "int", paramType = "query", required = true),
            //@ApiImplicitParam(name = "birth", value = "생년월일", dataType = "String", paramType = "query", required = true),
            //@ApiImplicitParam(name = "lunar", value = "양/음력여부", dataType = "int", paramType = "query", required = true),
            //@ApiImplicitParam(name = "telephone", value = "일반전화", dataType = "String", paramType = "query", required = true),
            //@ApiImplicitParam(name = "recvSms", value = "SMS 수신여부", dataType = "int", paramType = "query", required = false),
            //@ApiImplicitParam(name = "recvEmail", value = "메일링 수신여부", dataType = "int", paramType = "query", required = false),
            //@ApiImplicitParam(name = "isMobileReg", value = "모바일여부", dataType = "int", paramType = "query", required = false),

    })
    public ApiResultCodeDTO regUser(@ModelAttribute TUserVO tUserVO) {
        return userService.regUser(tUserVO);
    }

    @RequestMapping(value = "/modify", method = {RequestMethod.POST}, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("회원정보 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "query", required = true),
            //@ApiImplicitParam(name = "birth", value = "생년월일", dataType = "String", paramType = "query", required = true),
            //@ApiImplicitParam(name = "email", value = "이메일", dataType = "String", paramType = "query", required = true),
            //@ApiImplicitParam(name = "telephone", value = "일반전화", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "telephoneMobile", value = "휴대전화", dataType = "String", paramType = "query", required = true),
            //@ApiImplicitParam(name = "recvSms", value = "SMS 수신여부", dataType = "int", paramType = "query", required = false),
            //@ApiImplicitParam(name = "recvEmail", value = "메일링 수신여부", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "zipcode", value = "우편번호", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "addressRoad", value = "신주소 명", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "addressNumber", value = "구주소명", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "address", value = "상세주소", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "interestCtgKey0", value = "준비직렬", dataType = "int", paramType = "query", required = true),
    })
    public ApiResultObjectDTO modifyUser(@ModelAttribute TUserVO tUserVO) throws Exception {
        return userService.modifyUserInfo(tUserVO);
    }

    @RequestMapping(value = "/modifyPwd", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("회원 비밀번호 변경")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "currentUserPwd", value = "현재 비말번호", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "changeUserPwd", value = "변경할 비밀번호", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultCodeDTO modifyUserPwd(@RequestParam("userKey") int userKey,
                                          @RequestParam("currentUserPwd") String currentUserPwd,
                                          @RequestParam("changeUserPwd") String changeUserPwd) {
        return userService.modifyUserPassword(userKey, currentUserPwd, changeUserPwd);
    }

    @RequestMapping(value = "/memberSecession", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("회원 탈퇴")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "회원 이름", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "userId", value = "회원 아이디", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "userPassword", value = "회원 패스워드", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "reason", value = "탈퇴 이유", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "memo", value = "메모", dataType = "String", paramType = "query", required =  false)
    })
    public ApiResultCodeDTO memberSecession(@RequestParam("userName") String userName,
                                            @RequestParam("userId") String userId,
                                            @RequestParam("userPassword") String userPassword,
                                            @RequestParam("reason") String reason,
                                            @RequestParam(value = "memo", required = false) String memo) {
        return userService.requestMemberSecession(userName, userId, userPassword, reason, memo);
    }

    @RequestMapping(value = "/requestChangeDevice/{userKey}", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("기기변경 요청")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키갑", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "deviceType", value = "기기종류 (PC, MOBILE)", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultCodeDTO requestChangeDevice(@PathVariable("userKey") int userKey,
                                                @RequestParam("deviceType") String deviceType) throws Exception {
        return userService.requestChangeDevice(userKey, deviceType);
    }

}


