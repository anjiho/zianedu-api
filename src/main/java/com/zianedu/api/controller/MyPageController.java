package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiPagingResultDTO;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.MyPageService;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
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

    @RequestMapping(value = "/getVideoSignUp/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 수강중인강좌(동영상)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키 (86942)", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "deviceType", value = "기기종류('MOBILE':모바일, 'PC':데스크탑) ", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultObjectDTO getUserVideoOnlineSignUpList(@PathVariable("userKey") int userKey,
                                                             @RequestParam("deviceType") String deviceType) {
        return myPageService.getUserVideoOnlineSignUpList(userKey, deviceType);
    }

    @RequestMapping(value = "/getOnlineLectureDetail/{jLecKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 수강중인강좌 > 수강중인강좌 상세정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jLecKey", value = "강좌주문 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultObjectDTO getUserOnlineLectureDetailInfo(@PathVariable("jLecKey") int jLecKey) {
        return myPageService.getUserOnlineLectureDetailInfo(jLecKey);
    }

    @RequestMapping(value = "/getOnlineLectureSubjectList/{jLecKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 수강중인강좌 > 수강중인강좌 상세정보 > 강좌목차")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jLecKey", value = "강좌주문 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getUserOnlineLectureSubjectList(@PathVariable("jLecKey") int jLecKey) {
        return myPageService.getUserOnlineLectureSubjectList(jLecKey);
    }

    @RequestMapping(value = "/getGoodsReview/{jLecKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 수강중인강좌 > 수강중인강좌 상세정보 > 회원리뷰")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jLecKey", value = "강좌주문 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "sPage", value = "리스트 시작", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "리스트 개수", dataType = "int", paramType = "query", required = true)
    })
    public ApiPagingResultDTO getGoodsReviewList(@PathVariable("jLecKey") int jLecKey,
                                                 @Param("sPage") int sPage,
                                                 @Param("listLimit") int listLimit) {
        return myPageService.getGoodsReviewList(jLecKey, sPage, listLimit);
    }

    @RequestMapping(value = "/getVideoPauseRequestPopup/{jLecKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 수강중인강좌 > 동영상 수강중인강좌 > 일시정지 버튼 클릭 팝업 데이터")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jLecKey", value = "강좌주문 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultObjectDTO getVideoPauseRequestPopup(@PathVariable("jLecKey") int jLecKey) {
        return myPageService.getVideoPauseRequestPopup(jLecKey);
    }

    @RequestMapping(value = "/requestVideoStartStop", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("강좌 일시정지 요청, 일시정지 해제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jLecKey", value = "강좌주문 키", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pauseDay", value = "일시중지 요청 일수", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "requestType", value = "요청타입 : ('START' : 일시정지 해제, 'STOP' : 일시정지 )", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultCodeDTO requestVideoStartStop(@RequestParam("jLecKey") int jLecKey,
                                                  @RequestParam("pauseDay") int pauseDay,
                                                  @RequestParam("requestType") String requestType) {
        return myPageService.requestVideoStartStop(jLecKey, pauseDay, requestType);
    }

    @RequestMapping(value = "/getOnlineVideoPauseList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 일시정지강좌 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getOnlineVideoPauseList(@PathVariable("userKey") int userKey) {
        return myPageService.getOnlineVideoPauseList(userKey);
    }

    @RequestMapping(value = "/getOnlineVideoEndList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 수강완료강좌 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getOnlineVideoEndList(@PathVariable("userKey") int userKey) {
        return myPageService.getOnlineVideoEndList(userKey);
    }

}
