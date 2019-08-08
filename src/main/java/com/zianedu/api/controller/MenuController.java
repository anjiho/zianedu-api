package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.CategoryService;
import com.zianedu.api.service.MenuService;
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
@RequestMapping(value = "/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/getLeftMenu/{ctgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("좌측 메뉴 리스트 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctgKey", value = "카테고리 키(/getLeftMenuCtgKey 호출하면 값 정의)", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getLeftMenu(@PathVariable("ctgKey") int ctgKey) {
        return menuService.getLeftMenuList(ctgKey);
    }

    @RequestMapping(value = "/getTechVodZianPassLeftMenu", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("기술직동영상 > 지안패스 > 좌측 가져오기")
    public ApiResultListDTO getTechVodZianPassLeftMenu() {
        return menuService.getTechVodZianPassLeftMenu(454);
    }

    @RequestMapping(value = "/getTeacherIntroduceLeftMenu/{ctgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("교수소개 좌측메뉴 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctgKey", value = "카테고리 키(/getLeftMenuCtgKey 호출하면 값 정의)", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getTeacherIntroduceLeftMenu(@PathVariable("ctgKey") int ctgKey) {
        return menuService.getTeacherIntroduceLeftMenu(ctgKey);
    }

    @RequestMapping(value = "/getZianPassMenu/{ctgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("지안패스 메뉴 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctgKey", value = "카테고리 키(239 : 행정직동영상 지안패스, 454 : 기술직동영상 지안패스, 587 : 계리직동영상 지안패스)", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getZianPassMenu(@PathVariable("ctgKey") int ctgKey) {
        return menuService.getZianPassMenu(ctgKey);
    }
}
