package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.BannerService;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @RequestMapping(value = "/getMainPageCtgKeyInfo/{ctgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("직렬별 메인페이지 관련 CTG_KEY 목록 불러오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctgKey", value = "카테고리 키 (지안에듀 : 215, 행정직학원 : 216, 행정직동영상 : 217, 기술직학원 : 218, 기술직동영상 : 219, 계리직학원 : 220, 계리직동영상 : 221, 온라인서점 : 687, 빅모의고사 : 6736", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getMainPageCtgKeyInfo(@PathVariable("ctgKey") int ctgKey) {
        return bannerService.getMainPageCtgKeyInfo(ctgKey);
    }

    @RequestMapping(value = "/getMainPageTopBanner", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("메인페이지 최상단 배너")
    public ApiResultObjectDTO getMainPageTopBanner() {
        return bannerService.getMainPageTopBanner(222);
    }

    @RequestMapping(value = "/getMainBigBanner/{listNumber}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("메인 대 배너")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "listNumber", value = "배너 개수", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getMainBigBanner(@PathVariable(value = "listNumber") int listNumber) {
        return bannerService.getBanner(223, listNumber);
    }

    @RequestMapping(value = "/getMainMiniBanner/{ctgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("대 배너 밑 미니 배너")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctgKey", value = "카테고리 키", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "listNumber", value = "배너 개수", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultListDTO getMainMiniBanner(@PathVariable("ctgKey") int ctgKey,
                                              @RequestParam("listNumber") int listNumber) {
        return bannerService.getBanner(ctgKey, listNumber);
    }

    @RequestMapping(value = "/getPopupList/{ctgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("팝업 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctgKey", value = "카테고리 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getPopup(@PathVariable("ctgKey") int ctgKey) {
        return bannerService.getPopup(ctgKey);
    }

    @RequestMapping(value = "/getTeacherBannerList/{ctgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("강사(지안교수진) 배너 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctgKey", value = "카테고리 키(228:지안에듀, 788:행정직학원, 794:행정직동영상, 823:기술직학원, 829:기술직동영상, 1080:계리직학원, 1086:계리직동영상)", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "subjectType", value = "과목타입(0:공통과목, 1:전공과목) ", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultListDTO getTeacherBannerList(@PathVariable("ctgKey") int ctgKey,
                                                 @RequestParam("subjectType") int subjectType) throws Exception {
        return bannerService.getTeacherBannerList(ctgKey, subjectType);
    }

    @RequestMapping(value = "/getPopulateAcademyLectureList", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("학원 인기강좌 리스트")
    public ApiResultListDTO getPopulateAcademyLectureList() {
        return bannerService.getPopulateAcademyLectureList(225);
    }

    @RequestMapping(value = "/getPopulateVideoLectureList", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("동영상 인기 강좌 리스트")
    public ApiResultListDTO getPopulateVideoLectureList() {
        return bannerService.getPopulateVideoLectureList(226);
    }

    @RequestMapping(value = "/getPackageLectureList", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("패키기 강좌 리스트")
    public ApiResultListDTO getPackageLectureList() {
        return bannerService.getPackageLectureList(227);
    }

    @RequestMapping(value = "/getNoticeList/{subject}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("배너 공시사항 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subject", value = "공지사항 종류 값( PUBLIC : 행정직, TECH : 기술직, POST : 계리직 )", dataType = "string", paramType = "path", required = true)
    })
    public ApiResultObjectDTO getBannerNoticeList(@PathVariable("subject") String subject) {
        return bannerService.getBannerNoticeList(subject);
    }

    @RequestMapping(value = "/getOnlineBookList", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("배너 온라인 서점 리스트")
    public ApiResultListDTO getBannerBookList() {
        return bannerService.getBannerBookList();
    }

    @RequestMapping(value = "/getSearchKeywordList/{className}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("인기 검색어 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "className", value = "( PUBLIC : 행정직, TECH : 기술직, POST : 계리직, BOOK : 온라인서점 )", dataType = "string", paramType = "path", required = true)
    })
    public ApiResultListDTO getSearchKeywordList(@PathVariable("className") String className) {
        return bannerService.getSearchKeywordList(className);
    }

    @RequestMapping(value = "/getBookStoreCenterBanner/{listNumber}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("온라인 서점 중앙 이벤트 베너")
    public ApiResultListDTO getBookStoreCenterBanner(@PathVariable(value = "listNumber") int listNumber) {
        return bannerService.getBanner(837, listNumber);
    }

    @RequestMapping(value = "/getBookStoreCenterRightBanner/{listNumber}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("온라인 서점 중앙 오른쪽 이벤트 베너")
    public ApiResultListDTO getBookStoreCenterRightBanner(@PathVariable(value = "listNumber") int listNumber) {
        return bannerService.getBanner(838, listNumber);
    }
}
