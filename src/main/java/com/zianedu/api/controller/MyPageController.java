package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiPagingResultDTO;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.MyPageService;
import com.zianedu.api.service.OrderService;
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

    @Autowired
    private OrderService orderService;

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

    @RequestMapping(value = "/getVideoSignUpLectureNameList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 수강중인강좌(동영상) 강좌명 리스트 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키 (86942)", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "deviceType", value = "기기종류('MOBILE':모바일, 'PC':데스크탑) ", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "subjectCtgKey", value = "과목 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "stepCtgKey", value = "유형 키값", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultListDTO getUserVideoOnlineSignUpList(@PathVariable("userKey") int userKey,
                                                           @RequestParam("deviceType") String deviceType,
                                                           @RequestParam("subjectCtgKey") int subjectCtgKey,
                                                           @RequestParam("stepCtgKey") int stepCtgKey) {
        return myPageService.getUserSignUpLectureNameList(userKey, deviceType, subjectCtgKey, stepCtgKey);
    }

    @RequestMapping(value = "/getVideoSignUpCount/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 수강중인 강좌수")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키 (86942)", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "deviceType", value = "기기종류('MOBILE':모바일, 'PC':데스크탑) ", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultCodeDTO getVideoSignUpCount(@PathVariable("userKey") int userKey,
                                                         @RequestParam("deviceType") String deviceType) {
        return myPageService.getUserSignUpLectureCount(userKey, deviceType, 0, 0);
    }

    @RequestMapping(value = "/getVideoSignUpDetailInfo/{jLecKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 수강중인강좌 상세정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jLecKey", value = "주문 강좌 키값", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultObjectDTO getUserVideoOnlineSignUpList(@PathVariable("jLecKey") int jLecKey) {
        return myPageService.getUserVideoOnlineSignUpLectureList(jLecKey);
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

    @RequestMapping(value = "/getOnlineVideoPauseListByJLecKey/{jLecKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 일시정지강좌 리스트(JLecKey기준)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jLecKey", value = "주문 강좌 키값", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getOnlineVideoPgetOnlineVideoPauseListByJLecKeyauseList(@PathVariable("jLecKey") int jLecKey) {
        return myPageService.getOnlineVideoPauseListByJLecKey(jLecKey);
    }

    @RequestMapping(value = "/getOnlineVideoEndList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 수강완료강좌 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getOnlineVideoEndList(@PathVariable("userKey") int userKey) {
        return myPageService.getOnlineVideoEndList(userKey);
    }

    @RequestMapping(value = "/getZianPassSignUpList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 지안패스 상품 목록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자키 값(41677)", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getZianPassSignUpList(@PathVariable("userKey") int userKey) {
        return myPageService.getZianPassProductList(userKey);
    }

    @RequestMapping(value = "/getSignUpZianPassTypeList/{jKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 지안패스 유형 목록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jKey", value = "주문 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "deviceType", value = "기기종류('MOBILE':모바일, 'PC':데스크탑) ", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultListDTO getSignUpZianPassTypeList(@PathVariable("jKey") int jKey, @RequestParam("deviceType") String deviceType) {
        return myPageService.getSignUpZianPassTypeList(jKey, deviceType);
    }

    @RequestMapping(value = "/getSignUpZianPassSubjectNameList/{jKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 자안패스 > 강좌명 리스트 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jKey", value = "주문 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "deviceType", value = "기기종류('MOBILE':모바일, 'PC':데스크탑) ", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "stepCtgKey", value = "유형 키값", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultListDTO getSignUpZianPassSubjectNameList(@PathVariable("jKey") int jKey,
                                                         @RequestParam("deviceType") String deviceType,
                                                         @RequestParam("stepCtgKey") int stepCtgKey) {
        return myPageService.getSinUpZianPassLectureNameList(jKey, deviceType, stepCtgKey);
    }

    @RequestMapping(value = "/getSignUpAcademyTypeList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 학원실강 유형 목록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getSignUpAcademyTypeList(@PathVariable("userKey") int userKey) {
        return myPageService.getSignUpAcademyTypeList(userKey);
    }

    @RequestMapping(value = "/getSignUpAcademySubjectNameList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 학원실강 > 강좌명 리스트 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "stepCtgKey", value = "유형 키값", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultListDTO getSignUpAcademySubjectNameList(@PathVariable("userKey") int userKey,
                                                             @RequestParam("stepCtgKey") int stepCtgKey) {
        return myPageService.getSignUpAcademyLectureNameList(userKey, stepCtgKey);
    }

    @RequestMapping(value = "/getSignUpVideoLecturePauseTypeList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 일시정지 동영상 유형 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키값(93928)", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getSignUpVideoLecturePauseTypeList(@PathVariable("userKey") int userKey) {
        return myPageService.getSignUpVideoLecturePauseTypeList(userKey);
    }

    @RequestMapping(value = "/getSignUpVideoLecturePauseSubjectList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 일시정지 > 강좌명 리스트 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "stepCtgKey", value = "유형 키값", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultListDTO getSignUpVideoLecturePauseSubjectList(@PathVariable("userKey") int userKey,
                                                            @RequestParam("stepCtgKey") int stepCtgKey) {
        return myPageService.getSignUpVideoLecturePauseSubjectList(userKey, stepCtgKey);
    }

    @RequestMapping(value = "/getSignUpVideoLectureEndTypeList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 수강완료 유형 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키값(70001)", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getSignUpVideoLectureEndTypeList(@PathVariable("userKey") int userKey) {
        return myPageService.getSignUpVideoLectureEndTypeList(userKey);
    }

    @RequestMapping(value = "/getSignUpVideoLectureEndSubjectList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 수강완료 과목 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키값(70001)", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "stepCtgKey", value = "유형 키값", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultListDTO getSignUpVideoLectureEndSubjectList(@PathVariable("userKey") int userKey,
                                                                  @RequestParam("stepCtgKey") int stepCtgKey) {
        return myPageService.getSignUpVideoLectureEndSubjectList(userKey, stepCtgKey);
    }

    @RequestMapping(value = "/getSignUpVideoLectureEndInfo/{jLecKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 수강완료 상세정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jLecKey", value = "주문 강좌 키값", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultObjectDTO getSignUpVideoLectureEndInfo(@PathVariable("jLecKey") int jLecKey) {
        return myPageService.getSignUpVideoLectureEndInfo(jLecKey);
    }

    @RequestMapping(value = "/getUserOrderList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("마이페이지 > 주문내역 조회 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "startDate", value = "검색 시작일", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "endDate", value = "검색 종료일", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "페이징 리스트 개수", dataType = "int", paramType = "query", required = true)
    })
    public ApiPagingResultDTO getUserOrderList(@PathVariable("userKey") int userKey,
                                               @RequestParam("startDate") String startDate,
                                               @RequestParam("endDate") String endDate,
                                               @RequestParam("sPage") int sPage,
                                               @RequestParam("listLimit") int listLimit) {
        return orderService.getUserOrderList(userKey, startDate, endDate, sPage, listLimit);
    }

    @RequestMapping(value = "/getUserOrderDetail/{jKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("마이페이지 > 주문내역 상세")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jKey", value = "주문 키값", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultObjectDTO getUserOrderDetail(@PathVariable("jKey") int jKey) {
        return orderService.getUserOrderDetailInfo(jKey);
    }


}
