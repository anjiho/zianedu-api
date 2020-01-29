package com.zianedu.api.controller;

import com.google.gson.JsonArray;
import com.zianedu.api.dto.*;
import com.zianedu.api.service.ExamService;
import com.zianedu.api.utils.GsonUtil;
import com.zianedu.api.utils.ZianApiUtils;
import com.zianedu.api.vo.SaveCartVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @RequestMapping(value = "/getGichulProblemList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("기출문제 > 기출문제 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "groupCtgKey", value = "급수 카테고리 키", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "classCtgKey", value = "직렬 카테고리 키", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "subjectCtgKey", value = "과목 카테고리 키", dataType = "int", paramType = "query", required = false),
    })
    public ApiResultListDTO getGichulProblemList(@PathVariable(value = "userKey") int userKey,
                                                 @RequestParam(value = "groupCtgKey", required = false, defaultValue = "0") int groupCtgKey,
                                                 @RequestParam(value = "classCtgKey", required = false, defaultValue = "0") int classCtgKey,
                                                 @RequestParam(value = "subjectCtgKey", required = false, defaultValue = "0") int subjectCtgKey) {
        return examService.getGichulProblemList(userKey, groupCtgKey, classCtgKey, subjectCtgKey);
    }

    @RequestMapping(value = "/getGichulAchievementManagementList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("기출문제 > 성적관리 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "groupCtgKey", value = "급수 카테고리 키", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "classCtgKey", value = "직렬 카테고리 키", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "subjectCtgKey", value = "과목 카테고리 키", dataType = "int", paramType = "query", required = false),
    })
    public ApiResultListDTO getAchievementManagementList(@PathVariable(value = "userKey") int userKey,
                                                 @RequestParam(value = "groupCtgKey", required = false, defaultValue = "0") int groupCtgKey,
                                                 @RequestParam(value = "classCtgKey", required = false, defaultValue = "0") int classCtgKey,
                                                 @RequestParam(value = "subjectCtgKey", required = false, defaultValue = "0") int subjectCtgKey) {
        return examService.getAchievementManagementList(userKey, groupCtgKey, classCtgKey, subjectCtgKey);
    }

    @RequestMapping(value = "/getDiagnosisEvaluationExamList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("진단평가 > 진단평가 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getDiagnosisEvaluationExamList(@PathVariable(value = "userKey") int userKey) {
        return examService.getDiagnosisEvaluationExamList(userKey);
    }

    @RequestMapping(value = "/getDiagnosisAchievementManagementList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("진단평가 > 성적관리 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getDiagnosisEvaluationCompleteList(@PathVariable(value = "userKey") int userKey) {
        return examService.getDiagnosisEvaluationCompleteList(userKey);
    }

    @RequestMapping(value = "/getWeekBigExamList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("주간모의고사 > 빅모의고사(온라인) 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getWeekBigExamList(@PathVariable(value = "userKey") int userKey) throws Exception {
        return examService.getWeekBigExamList(userKey);
    }

    @RequestMapping(value = "/getWeekBigExamAchievementManagementList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("주간모의고사 > 성적관리 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getWeekBigExamAchievementManagementList(@PathVariable(value = "userKey") int userKey) {
        return examService.getWeekBigExamAchievementManagementList(userKey);
    }

    @RequestMapping(value = "/getAchievementManagementDetailInfo/{examUserKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("성적전체 분석정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examUserKey", value = "시험 사용자 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultObjectDTO getAchievementManagementDetailInfo(@PathVariable(value = "examUserKey") int userKey) {
        return examService.getAchievementManagementDetailInfo(userKey);
    }

    @RequestMapping(value = "/getAchievementManagementDetailInfoBySubject/{examUserKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("과목별 성적 상세 정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examUserKey", value = "시험 사용자 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultObjectDTO getAchievementManagementDetailInfoBySubject(@PathVariable(value = "examUserKey") int userKey) {
        return examService.getAchievementManagementDetailInfoBySubject(userKey);
    }

    @RequestMapping(value = "/getWrongNote/{examUserKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("오답노트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examUserKey", value = "시험 사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "isScore", value = "정답여부(-1:전체, 0:오답, 1:정답)", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "isInterest", value = "관심문제여부(-1:전체, 0:비관심, 1:관심)", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultObjectDTO getWrongNote(@PathVariable(value = "examUserKey") int examUserKey,
                                           @RequestParam(value = "isScore") int isScore,
                                           @RequestParam(value = "isInterest") int isInterest) {
        return examService.getExamWrongNoteList(examUserKey, isScore, isInterest);
    }

    @RequestMapping(value = "/getExamMasterGateInfo/{examKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("시험 시작 입구 페이지 정보 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examKey", value = "시험 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultObjectDTO getExamMasterGateInfo(@PathVariable(value = "examKey") int examKey,
                                                    @RequestParam(value = "userKey") int userKey) {
        return examService.getExamMasterGateInfo(examKey, userKey);
    }

    @RequestMapping(value = "/getUserExamList/{examKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("시험 시작 버튼 후 시험정보 리스트 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examKey", value = "시험 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultObjectDTO getUserExamList(@PathVariable(value = "examKey") int examKey,
                                              @RequestParam(value = "userKey") int userKey) {
        return examService.getUserExamList(examKey, userKey);
    }

    @RequestMapping(value = "/saveExamResult", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("시험 결과 저장")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "playTime", value = "시험을 본 시간정보", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultCodeDTO saveExamResult(@RequestParam int playTime,
                                           @RequestParam String examResultInfo) {
        JsonArray saveExamResultInfoJson = GsonUtil.convertStringToJsonArray(examResultInfo);
        List<ExamResultDTO>examResultList = GsonUtil.getObjectFromJsonArray(saveExamResultInfoJson, ExamResultDTO.class);
        return examService.injectUserExamResult(playTime, examResultList);
    }

    @RequestMapping(value = "/getMockExamClassCtgSelectBoxList/{onOffKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("모의고사 리스트 직렬선택 셀렉트 박스")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "onOffKey", value = "온.오프라인 키 값(온라인:2, 오프라인:3)", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getMockExamClassCtgSelectBoxList(@PathVariable(value = "onOffKey") int onOffKey) {
        return examService.getMockExamClassCtgSelectBoxList(onOffKey, false, 0);
    }

    @RequestMapping(value = "/getMockExamListAtBuy/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("학원모의고사 구매한 시험 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "onOffKey", value = "온.오프라인 키 값(온라인:2, 오프라인:3)", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "페이징 리스 개수", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "ctgKey", value = "직렬키 값", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "searchType", value = "검색 종류(name : 시험명) ", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "searchText", value = "검색 값", dataType = "string", paramType = "query", required = false)
    })
    public ApiPagingResultDTO getMockExamListAtBuy(@PathVariable("userKey") int userKey,
                                                @RequestParam("onOffKey") int onOffKey,
                                              @RequestParam("sPage") int sPage,
                                              @RequestParam("listLimit") int listLimit,
                                              @RequestParam(value = "ctgKey", required = false, defaultValue = "0") int ctgKey,
                                              @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
                                              @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) throws Exception {
        return examService.getMockExamListAtBuy(userKey, onOffKey, ctgKey, sPage, listLimit, searchType, searchText);
    }

    @RequestMapping(value = "/getMockExamBuyClassCtgSelectBoxList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("학원모의고사 구매한 리스트 직렬선택 셀렉트 박스")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "onOffKey", value = "온.오프라인 키 값(온라인:2, 오프라인:3)", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultListDTO getMockExamBuyClassCtgSelectBoxList(@PathVariable(value = "userKey") int userKey,
                                                                @RequestParam(value = "onOffKey") int onOffKey) {
        return examService.getMockExamClassCtgSelectBoxList(onOffKey, true, userKey);
    }
}
