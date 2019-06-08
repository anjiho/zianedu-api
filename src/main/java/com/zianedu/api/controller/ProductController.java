package com.zianedu.api.controller;

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

}
