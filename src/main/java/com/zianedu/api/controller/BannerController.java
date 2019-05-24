package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.BannerService;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @RequestMapping(value = "/getMainPageTopBanner", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("메인페이지 최상단 배너")
    public ApiResultObjectDTO getMainPageTopBanner() {
        return bannerService.getMainPageTopBanner(222);
    }

    @RequestMapping(value = "/getMainBigBanner", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("메인 대 배너")
    public ApiResultListDTO getMainBigBanner() {
        return bannerService.getBanner(223);
    }

    @RequestMapping(value = "/getMainMiniBanner/{ctgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("대 배너 밑 미니 배너")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctgKey", value = "카테고리 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getMainMiniBanner(@PathVariable("ctgKey") int ctgKey) {
        return bannerService.getBanner(ctgKey);
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
            @ApiImplicitParam(name = "ctgKey", value = "카테고리 키", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getTeacherBannerList(@PathVariable("ctgKey") int ctgKey) {
        return bannerService.getTeacherBannerList(ctgKey);
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


}
