package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiPagingResultDTO;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.TeacherService;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/getTeacherHomeInfo/{teacherKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("강사 홈정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherKey", value = "강사 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "listLimit", value = "리스트 개수 요청 값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "device", value = "디바이스 종류(데스크탑 : 1, 모바일 : 3) ", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "menuCtgKey", value = "과목의 CTG_KEY (parent_mnk의 값) ", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultObjectDTO getTeacherHome(@PathVariable("teacherKey") int teacherKey,
                                             @RequestParam("listLimit") int listLimit,
                                             @RequestParam("device") int device,
                                             @RequestParam("menuCtgKey") int menuCtgKey) {
        return teacherService.getTeacherHomeInfo(teacherKey, listLimit, device, menuCtgKey);
    }

    @RequestMapping(value = "/getTeacherVideoLecture/{teacherKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("강사소개 > 동영상 강좌 정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherKey", value = "강사 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "stepCtgKey", value = "유형 카테고리 키값(전체는 0) ", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultListDTO getTeacherVideoLecture(@PathVariable("teacherKey") int teacherKey,
                                                   @RequestParam("stepCtgKey") int stepCtgKey) {
        return teacherService.getTeacherLectureList(teacherKey, stepCtgKey);
    }

    @RequestMapping(value = "/getTeacherAcademyLecture/{teacherKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("강사소개 > 학원 강좌 정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherKey", value = "강사 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "stepCtgKey", value = "유형 카테고리 키값(전체는 0) ", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultListDTO getTeacherAcademyLecture(@PathVariable("teacherKey") int teacherKey,
                                                   @RequestParam("stepCtgKey") int stepCtgKey) {
        return teacherService.getTeacherAcademyList(teacherKey, stepCtgKey);
    }

    @RequestMapping(value = "/getTeacherReferenceRoom/{teacherKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("강사소개 > 학습 자료실")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherKey", value = "강사 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "페이징 리스 개수", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "searchType", value = "검색 타입(제목 : 'title', 작성자 : 'name', 내용 : 'contents'", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "searchText", value = "검색 내용", dataType = "String", paramType = "query", required = false),
    })
    public ApiPagingResultDTO getTeacherReferenceRoom(@PathVariable("teacherKey") int teacherKey,
                                                      @RequestParam("sPage") int sPage,
                                                      @RequestParam("listLimit") int listLimit,
                                                      @RequestParam(value = "searchType", required = false) String searchType,
                                                      @RequestParam(value = "searchText", required = false) String searchText) {
        return teacherService.getReferenceRoomList(teacherKey, sPage, listLimit, searchType, searchText);
    }

    @RequestMapping(value = "/getTeacherLearningQna/{teacherKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("강사소개 > 학습 Q/A")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherKey", value = "강사 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "페이징 리스 개수", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "searchType", value = "검색 타입(제목 : 'title', 작성자 : 'name', 내용 : 'contents'", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "searchText", value = "검색 내용", dataType = "String", paramType = "query", required = false),
    })
    public ApiPagingResultDTO getTeacherLearningQna(@PathVariable("teacherKey") int teacherKey,
                                                      @RequestParam("sPage") int sPage,
                                                      @RequestParam("listLimit") int listLimit,
                                                      @RequestParam(value = "searchType", required = false) String searchType,
                                                      @RequestParam(value = "searchText", required = false) String searchText) {
        return teacherService.getTeacherLearningQna(teacherKey, sPage, listLimit, searchType, searchText);
    }

    @RequestMapping(value = "/getTeacherLectureReview/{teacherKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("강사소개 > 수강후기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherKey", value = "강사 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "gKey", value = "상품키", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "페이징 리스 개수", dataType = "int", paramType = "query", required = true)
    })
    public ApiPagingResultDTO getLectureReviewList(@PathVariable("teacherKey") int teacherKey,
                                                   @RequestParam("gKey") int gKey,
                                                    @RequestParam("sPage") int sPage,
                                                    @RequestParam("listLimit") int listLimit) {
        return teacherService.getLectureReviewList(teacherKey, gKey, sPage, listLimit);
    }

    @RequestMapping(value = "/getTeacherVideoAcademyProductList/{teacherKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("강사소개 > 수강후기 > 강사의 상품목록 셀렉트박스")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherKey", value = "강사 키값", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getTeacherVideoAcademyProductList(@PathVariable("teacherKey") int teacherKey) {
        return teacherService.getTeacherVideoAcademyProductList(teacherKey);
    }

    public

}
