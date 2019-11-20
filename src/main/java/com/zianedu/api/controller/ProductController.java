package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.ProductService;
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

    @RequestMapping(value = "/getSpecialPackageList", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("특별 패키지 상품 리스트")
    public ApiResultListDTO getSpecialPackageList() {
        return productService.getSpecialPackageList();
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

    @RequestMapping(value = "/getFreeVideoLectureListFromCategoryMenu/{ctgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("학원별 무료 동영상강의 상품 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctgKey", value = "메뉴 카테고리 값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "device", value = "디바이스 종류(PC, MOBILE)", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultListDTO getFreeVideoLectureList(@PathVariable("ctgKey") int ctgKey, @RequestParam("device") String device) {
        return productService.getFreeVideoLectureListFromCategoryMenu(ctgKey, device);
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

}
