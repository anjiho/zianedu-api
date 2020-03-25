package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiPagingResultDTO;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.*;
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

    @Autowired
    private BoardService boardService;

    @Autowired
    private ExamService examService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

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
    public ApiResultObjectDTO getUserVideoOnlineSignUpList(@PathVariable("jLecKey") int jLecKey) throws Exception {
        return myPageService.getUserVideoOnlineSignUpLectureList(jLecKey);
    }

    @RequestMapping(value = "/getPromotionUserVideoOnlineSignUpLectureList/{jLecKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 프로모션(패키지, 지안패스) > 수강중인강좌 상세정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jLecKey", value = "주문 강좌 키값", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultObjectDTO getPromotionUserVideoOnlineSignUpLectureList(@PathVariable("jLecKey") int jLecKey) throws Exception {
        return myPageService.getPromotionUserVideoOnlineSignUpLectureList(jLecKey);
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

    @RequestMapping(value = "/getPackageSignUpList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 패키지 상품 목록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자키 값(41677)", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getPackageSignUpList(@PathVariable("userKey") int userKey) {
        return myPageService.getSignUpPackageProductList(userKey);
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

    @RequestMapping(value = "/getSignUpPackageTypeList/{jKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 패키지 유형 목록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jKey", value = "주문 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "deviceType", value = "기기종류('MOBILE':모바일, 'PC':데스크탑) ", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultListDTO getSignUpPackageTypeList(@PathVariable("jKey") int jKey, @RequestParam("deviceType") String deviceType) {
        return myPageService.getSignUpPackageTypeList(jKey, deviceType);
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
            @ApiImplicitParam(name = "userKey", value = "사용자 키값(70001)", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "deviceType", value = "디바이스 종류", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultListDTO getSignUpVideoLectureEndTypeList(@PathVariable("userKey") int userKey,
                                                             @RequestParam("deviceType") String deviceType) {
        return myPageService.getSignUpVideoLectureEndTypeList(userKey, deviceType);
    }

    @RequestMapping(value = "/getSignUpVideoLectureEndSubjectList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 수강완료 과목 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키값(70001)", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "stepCtgKey", value = "유형 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "deviceType", value = "디바이스 종류", dataType = "string", paramType = "query", required = true),
    })
    public ApiResultListDTO getSignUpVideoLectureEndSubjectList(@PathVariable("userKey") int userKey,
                                                                @RequestParam("stepCtgKey") int stepCtgKey,
                                                                @RequestParam("deviceType") String deviceType) {
        return myPageService.getSignUpVideoLectureEndSubjectList(userKey, stepCtgKey, deviceType);
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

    @RequestMapping(value = "/getUserPointList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("마이페이지 > 마일리지 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "페이징 리스 개수", dataType = "int", paramType = "query", required = true)
    })
    public ApiPagingResultDTO getUserPointList(@PathVariable("userKey") int userKey,
                                               @RequestParam("sPage") int sPage,
                                               @RequestParam("listLimit") int listLimit) {
        return orderService.getUserPointList(userKey, sPage, listLimit);
    }

    @RequestMapping(value = "/getUserPointInfo/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("마이페이지 > 총 마일리지 정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultObjectDTO getUserPointInfo(@PathVariable("userKey") int userKey) {
        return orderService.getUserPointInfo(userKey);
    }

    @RequestMapping(value = "/getOneByOneQuestionList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("마이페이지 > 1:1상담내역 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "페이징 리스 개수", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "searchType", value = "검색 종류(title : 제목) ", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "searchText", value = "검색 값", dataType = "string", paramType = "query", required = false),
    })
    public ApiPagingResultDTO getOneByOneQuestionList(@PathVariable("userKey") int userKey,
                                               @RequestParam("sPage") int sPage,
                                               @RequestParam("listLimit") int listLimit,
                                               @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
                                               @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
        return myPageService.getOneByOneQuestionList(userKey, sPage, listLimit, searchType, searchText);
    }

    @RequestMapping(value = "/getOneByOneQuestionDetailInfo/{bbsKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("마이페이지 > 1:1상담 상세정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsKey", value = "게시판 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultObjectDTO getOneByOneQuestionDetailInfo(@PathVariable("bbsKey") int bbsKey) {
        return boardService.getBoardDetailInfo(10019, bbsKey);
    }

    @RequestMapping(value = "/getZianPassEndList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 > 지안패스 완료된 강좌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getZianPassEndList(@PathVariable("userKey") int userKey) {
        return myPageService.getZianPassEndList(userKey);
    }

    @RequestMapping(value = "/getMyWriteBoard/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("마이페이지 > 내 후기글")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "boardType", value = "게시판 종류(PASS:합격수기, LECTURE:수강후기, BOOK:도서후기, EXAM:응시후기) ", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "페이징 리스 개수", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "searchType", value = "검색 종류(title : 제목) ", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "searchText", value = "검색 값", dataType = "string", paramType = "query", required = false),
    })
    public ApiPagingResultDTO getMyWriteBoard(@PathVariable("userKey") int userKey,
                                              @RequestParam("boardType") String boardType,
                                              @RequestParam("sPage") int sPage,
                                              @RequestParam("listLimit") int listLimit,
                                              @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
                                              @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
        return myPageService.getBoardListAtMyWrite(userKey, boardType, sPage, listLimit, searchType, searchText);
    }

    @RequestMapping(value = "/getUserMockExamResultListAtBuy/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("성적관리 > 학원모의고사(온,오프라인)리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "onOffKey", value = "온.오프라인 키 값(온라인:2, 오프라인:3)", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "페이징 리스 개수", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "ctgKey", value = "직렬키 값", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "searchType", value = "검색 종류(name : 시험명) ", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "searchText", value = "검색 값", dataType = "string", paramType = "query", required = false)
    })
    public ApiPagingResultDTO getUserMockExamResultListAtBuy(@PathVariable("userKey") int userKey,
                                                             @RequestParam("onOffKey") int onOffKey,
                                                             @RequestParam("sPage") int sPage,
                                                             @RequestParam("listLimit") int listLimit,
                                                             @RequestParam(value = "ctgKey", required = false, defaultValue = "0") int ctgKey,
                                                             @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
                                                             @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) throws Exception {
        return examService.getUserMockExamResultListAtBuy(userKey, onOffKey, ctgKey, sPage, listLimit, searchType, searchText);
    }

    @RequestMapping(value = "/getUserFreeExamResultList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("성적관리 > 무료모의고사 리스트(주간모의고사, 기출문제)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "examType", value = "시험종류(GICHUL : 기출문제, WEEK : 주간모의고사)", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "페이징 리스 개수", dataType = "int", paramType = "query", required = true),

            @ApiImplicitParam(name = "groupCtgKey", value = "급수 카테고리 키", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "classCtgKey", value = "직렬 카테고리 키", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "subjectCtgKey", value = "과목 카테고리 키", dataType = "int", paramType = "query", required = false),

            @ApiImplicitParam(name = "searchType", value = "검색 종류(name : 시험명) ", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "searchText", value = "검색 값", dataType = "string", paramType = "query", required = false)
    })
    public ApiPagingResultDTO getUserFreeExamResultList(@PathVariable("userKey") int userKey,
                                                      @RequestParam("examType") String examType,
                                                      @RequestParam("sPage") int sPage,
                                                      @RequestParam("listLimit") int listLimit,
                                                        @RequestParam(value = "groupCtgKey", required = false, defaultValue = "0") int groupCtgKey,
                                                        @RequestParam(value = "classCtgKey", required = false, defaultValue = "0") int classCtgKey,
                                                        @RequestParam(value = "subjectCtgKey", required = false, defaultValue = "0") int subjectCtgKey,
                                                      @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
                                                      @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
        return examService.getUserFreeExamResultList(userKey, examType, classCtgKey, groupCtgKey, subjectCtgKey, sPage, listLimit, searchType, searchText);
    }

    @RequestMapping(value = "/getUserExamLogList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("성적관리 > 최근응시내역")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "페이징 리스 개수", dataType = "int", paramType = "query", required = true)
    })
    public ApiPagingResultDTO getUserExamLogList(@PathVariable("userKey") int userKey,
                                                        @RequestParam("sPage") int sPage,
                                                        @RequestParam("listLimit") int listLimit) {
        return examService.getUserExamLogList(userKey, sPage, listLimit);
    }

    @RequestMapping(value = "/confirmVideoPlay", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("구매한 동영상 플레이가 가능한지 여부 확인")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jLecKey", value = "주문 강좌 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "curriKey", value = "커리큘럽 키값", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultCodeDTO confirmVideoPlay(@RequestParam(value = "jLecKey") int jLecKey,
                                             @RequestParam(value = "curriKey") int curriKey) {
        return productService.confirmVideoPlay(jLecKey, curriKey);
    }

    @RequestMapping(value = "/confirmDuplicateDevice/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("동영상 플레이시 기기중복 확인")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "deviceType", value = "기기종류(0:PC, 1:MOBILE)", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "deviceId", value = "기기값", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "jLecKey", value = "주문 강좌 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "osVersion", value = "모바일 버전", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "appVersion", value = "모바일 앱 버전", dataType = "string", paramType = "query", required = false),
    })
    public ApiResultCodeDTO confirmDuplicateDevice(@PathVariable("userKey") int userKey,
                                                   @RequestParam("deviceType") int deviceType,
                                                   @RequestParam("deviceId") String deviceId,
                                                   @RequestParam("jLecKey") int jLecKey,
                                                   @RequestParam(value = "osVersion", required = false, defaultValue = "") String osVersion,
                                                   @RequestParam(value = "appVersion", required = false, defaultValue = "") String appVersion) {
        return userService.confirmDuplicateDevice(userKey, deviceType, deviceId, jLecKey, osVersion, appVersion);
    }

    @RequestMapping(value = "/injectVideoPlayTime", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("동영상 플레이시간 주입")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jLecKey", value = "주문 강좌 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "curriKey", value = "커리큘럽 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "deviceType", value = "기기종류(0:PC, 1:MOBILE)", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "mobileTime", value = "모바일 시간", dataType = "string", paramType = "query", required = false)
    })
    public ApiResultCodeDTO injectVideoPlayTime(@RequestParam(value = "jLecKey") int jLecKey,
                                                @RequestParam(value = "curriKey") int curriKey,
                                                @RequestParam(value = "deviceType", required = false, defaultValue = "0") String deviceType,
                                                @RequestParam(value = "mobileTime", required = false, defaultValue = "0") String mobileTime) {
        return productService.injectVideoPlayTime(jLecKey, curriKey, Integer.parseInt(deviceType), Integer.parseInt(mobileTime));
    }

}
