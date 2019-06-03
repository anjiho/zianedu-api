package com.zianedu.api.controller;

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
}
