package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.service.CategoryService;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/getLeftMenuCtgKey", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("좌측 메뉴 카테고리 키 목록 가져오기")
    public ApiResultListDTO getLeftMenuCtgKey() {
        return categoryService.getLeftMenuCtgKey();
    }

    @RequestMapping(value = "/getUserRegSerialList", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("회원가입 준비직렬 셀렉트박스 리스트")
    public ApiResultListDTO getUserRegSerialList() {
        return categoryService.getTCategoryListByParentKey(133);
    }
}
