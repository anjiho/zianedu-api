package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiPagingResultDTO;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.ProductService;
import com.zianedu.api.utils.GsonUtil;
import com.zianedu.api.utils.StringUtils;
import com.zianedu.api.utils.Util;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/getVideoProductDetail/{gKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("동영상 강좌 상품 상세정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gKey", value = "상품 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "device", value = "디바이스 종류(데스크탑 : 1, 모바일 : 3)", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultObjectDTO getVideoProductDetailInfo(@PathVariable("gKey") int gKey,
                                                        @RequestParam("device") int device) {
        return productService.getVideoProductDetailInfo(gKey, device);
    }

    @RequestMapping(value = "/getAcademyProductDetail/{gKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("학원수강 상품 상세정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gKey", value = "상품 키값", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultObjectDTO getAcademyProductDetailInfo(@PathVariable("gKey") int gKey) {
        return productService.getAcademyProductDetailInfo(gKey);
    }

    @RequestMapping(value = "/getAcademyLectureListFromCategoryMenu/{ctgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("학원별 학원수강신청 상품 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctgKey", value = "과목 카테고리 값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "stepCtgKey", value = "유형 카테고리 값", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "teacherKey", value = "강사 키", dataType = "int", paramType = "query", required = false)
    })
    public ApiResultListDTO getAcademyLectureList(@PathVariable("ctgKey") int ctgKey,
                                                  @RequestParam(value = "stepCtgKey", required = false, defaultValue = "0") int stepCtgKey,
                                                  @RequestParam(value = "teacherKey", required = false, defaultValue = "0") int teacherKey) {
        return productService.getAcademyLectureListFromCategoryMenu(ctgKey, stepCtgKey, teacherKey);
    }

    @RequestMapping(value = "/getVideoLectureListFromCategoryMenu/{ctgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("학원별 동영상강의 상품 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctgKey", value = "과목 카테고리 값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "stepCtgKey", value = "유형 카테고리 값", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "teacherKey", value = "강사 키", dataType = "int", paramType = "query", required = false)
    })
    public ApiResultListDTO getVideoLectureList(@PathVariable("ctgKey") int ctgKey,
                                                  @RequestParam(value = "stepCtgKey", required = false, defaultValue = "0") int stepCtgKey,
                                                  @RequestParam(value = "teacherKey", required = false, defaultValue = "0") int teacherKey) {
        return productService.getVideoLectureListFromCategoryMenu(ctgKey, stepCtgKey, teacherKey);
    }

    @RequestMapping(value = "/getSpecialPackageList/{menuCtgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("특별 패키지 상품 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuCtgKey", value = "메뉴 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "subjectMenuKeys", value = "과목 메뉴 키값", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "teacherKeys", value = "강사 키값", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "stepCtgKeys", value = "유형 키값", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "device", value = "디바이스 종류(PC, MOBILE)", dataType = "String", paramType = "query", required = true),
    })
    public ApiResultListDTO getSpecialPackageList(@PathVariable(value = "menuCtgKey") int menuCtgKey,
                                                  @RequestParam(value = "subjectMenuKeys", defaultValue = "", required = false) String subjectMenuKeys,
                                                  @RequestParam(value = "teacherKeys", defaultValue = "", required = false) String teacherKeys,
                                                  @RequestParam(value = "stepCtgKeys", defaultValue = "", required = false) String stepCtgKeys,
                                                  @RequestParam(value = "device", defaultValue = "", required = false) String device) {
        String[] subjectMenuKeyStrs = new String[0];
        String[] teacherKeyStrs = new String[0];
        String[] stepCtgKeyStrs = new String[0];

        if (!"".equals(Util.isNullValue(subjectMenuKeys, ""))) subjectMenuKeyStrs = GsonUtil.convertToStringArrayFromString(subjectMenuKeys);
        if (!"".equals(Util.isNullValue(teacherKeys, ""))) teacherKeyStrs = GsonUtil.convertToStringArrayFromString(teacherKeys);
        if (!"".equals(Util.isNullValue(stepCtgKeys, ""))) stepCtgKeyStrs = GsonUtil.convertToStringArrayFromString(stepCtgKeys);
        return productService.getSpecialPackageList(menuCtgKey, subjectMenuKeyStrs, teacherKeyStrs, stepCtgKeyStrs, device);
    }

    @RequestMapping(value = "/getSpecialPackageDetailInfo/{gKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("특별 패키지 상품 상세정보")
    public ApiResultObjectDTO getSpecialPackageDetailInfo(@PathVariable("gKey") int gKey) {
        return productService.getSpecialPackageDetailInfo(gKey);
    }

    @RequestMapping(value = "/getMockExamListFromCategoryMenu/{ctgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("모의고사 상품 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctgKey", value = "메뉴 카테고리 값", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getMockExamList(@PathVariable("ctgKey") int ctgKey) {
        return productService.getMockExamProductList(ctgKey);
    }

    @RequestMapping(value = "/getFreeVideoLectureStepList/{ctgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("학원별 무료 동영상강의 유형 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctgKey", value = "메뉴 카테고리 값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "freeLectureType", value = "무료강좌 종류(THEORY : 이론, 기출풀이 : PREV) ", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultListDTO getFreeVideoLectureList(@PathVariable("ctgKey") int ctgKey,
                                                    @RequestParam("freeLectureType") String freeLectureType) {
        return productService.getFreeVideoLectureStepList(ctgKey, freeLectureType);
    }

    @RequestMapping(value = "/getFreeVideoLectureListFromCategoryMenu/{ctgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("학원별 무료 동영상강의 상품 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctgKey", value = "메뉴 카테고리 값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "페이징 리스 개수", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "stepCtgKey", value = "유형 카테고리 값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "freeLectureType", value = "무료강좌 종류(THEORY : 이론, 기출풀이 : PREV) ", dataType = "string", paramType = "query", required = true)
    })
    public ApiPagingResultDTO getFreeVideoLectureList(@PathVariable("ctgKey") int ctgKey,
                                                      @RequestParam("sPage") int sPage,
                                                      @RequestParam("listLimit") int listLimit,
                                                      @RequestParam("stepCtgKey") int stepCtgKey,
                                                      @RequestParam("freeLectureType") String freeLectureType) {
        return productService.getFreeVideoLectureListFromCategoryMenu(sPage, listLimit, ctgKey, stepCtgKey, freeLectureType);
    }

    @RequestMapping(value = "/getFreeVideoLectureDetailInfo/{lecKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("학원별 무료 동영상강의 상세정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lecKey", value = "강좌 키 값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "device", value = "디바이스 종류(PC, MOBILE)", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultObjectDTO getFreeVideoLecturDetailInfo(@PathVariable("lecKey") int lecKey,
                                                    @RequestParam("device") String device) {
        return productService.getFreeVideoLectureDetailInfo(lecKey, device);
    }

    @RequestMapping(value = "/getZianPassProductList/{parentKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("지안패스 상품 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentKey", value = "카테고리 부모키 값", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getZianPassProductList(@PathVariable("parentKey") int parentKey) {
        return productService.getZianPassProductList(parentKey);
    }

    @RequestMapping(value = "/getLectureList/{gKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("강의 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gKey", value = "상품키 값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "device", value = "디바이스 종류(PC, MOBILE)", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultObjectDTO getVideoLectureList(@PathVariable("gKey") int gKey, @RequestParam("device") String device) {
        return productService.getVideoLectureList(gKey, device);
    }

    @RequestMapping(value = "/getLectureListByJLecKey/{jLecKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("내 강의실 동영상 강의 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jLecKey", value = "주문강좌 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "device", value = "디바이스 종류(PC, MOBILE)", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultObjectDTO getLectureListByJLecKey(@PathVariable("jLecKey") int jLecKey, @RequestParam("device") String device) {
        return productService.getVideoLectureListByJLecKey(jLecKey, device);
    }

    @RequestMapping(value = "/getLectureApplySubjectList/{menuCtgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("수강신청 > 과목 리스트 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuCtgKey", value = "메뉴 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "goodsType", value = "상품 종류(VIDEO, ACADEMY)", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultListDTO getLectureApplySubjectList(@PathVariable("menuCtgKey") int menuCtgKey,
                                                       @RequestParam("goodsType") String goodsType) {
        return productService.getLectureApplySubjectList(menuCtgKey, goodsType);
    }

    @RequestMapping(value = "/getLectureApplyTeacherList/{menuCtgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("수강신청 > 교수 리스트 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuCtgKey", value = "메뉴 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "goodsType", value = "상품 종류(VIDEO, ACADEMY)", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultListDTO getLectureApplyTeacherList(@PathVariable("menuCtgKey") int menuCtgKey, @RequestParam("goodsType") String goodsType) {
        return productService.getLectureApplyTeacherList(menuCtgKey, goodsType);
    }

    @RequestMapping(value = "/getLectureApplyTeacherTypeList/{menuCtgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("수강신청 > 강의 리스트(동영상, 학원)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuCtgKey", value = "메뉴 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "subjectMenuKeys", value = "과목 메뉴 키값", dataType = "object", paramType = "query", required = false),
            @ApiImplicitParam(name = "teacherKeys", value = "강사 키값", dataType = "object", paramType = "query", required = false),
            @ApiImplicitParam(name = "stepCtgKeys", value = "유형 키값", dataType = "object", paramType = "query", required = false),
            @ApiImplicitParam(name = "goodsType", value = "상품 종류(VIDEO, ACADEMY)", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultObjectDTO getLectureApplyTeacherTypeList(@PathVariable("menuCtgKey") int menuCtgKey,
                                                             @RequestParam(value = "subjectMenuKeys", defaultValue = "", required = false) String subjectMenuKeys,
                                                             @RequestParam(value = "teacherKeys", defaultValue = "", required = false) String teacherKeys,
                                                             @RequestParam(value = "stepCtgKeys", defaultValue = "", required = false) String stepCtgKeys,
                                                           @RequestParam("goodsType") String goodsType) {
        String[] subjectMenuKeyStrs = new String[0];
        String[] teacherKeyStrs = new String[0];
        String[] stepCtgKeyStrs = new String[0];

        if (!"".equals(Util.isNullValue(subjectMenuKeys, ""))) subjectMenuKeyStrs = GsonUtil.convertToStringArrayFromString(subjectMenuKeys);
        if (!"".equals(Util.isNullValue(teacherKeys, ""))) teacherKeyStrs = GsonUtil.convertToStringArrayFromString(teacherKeys);
        if (!"".equals(Util.isNullValue(stepCtgKeys, ""))) stepCtgKeyStrs = GsonUtil.convertToStringArrayFromString(stepCtgKeys);

        return productService.getLectureApplyTeacherTypeList(menuCtgKey, subjectMenuKeyStrs, teacherKeyStrs, stepCtgKeyStrs, goodsType);
    }

    @RequestMapping(value = "/getSpecialPackageSubjectList/{menuCtgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("수강신청 > 패키지 과목 리스트 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuCtgKey", value = "메뉴 키값", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getSpecialPackageSubjectList(@PathVariable(value = "menuCtgKey") int menuCtgKey) {
        return productService.getSpecialPackageSubjectList(menuCtgKey);
    }

    @RequestMapping(value = "/getSpecialPackageTeacherList/{menuCtgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("수강신청 > 패키지 교수 리스트 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuCtgKey", value = "메뉴 키값", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getSpecialPackageTeacherList(@PathVariable(value = "menuCtgKey") int menuCtgKey) {
        return productService.getSpecialPackageTeacherList(menuCtgKey);
    }

}
