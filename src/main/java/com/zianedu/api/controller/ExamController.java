package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.ExamService;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
