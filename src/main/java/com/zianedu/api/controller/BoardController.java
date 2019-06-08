package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.service.BoardService;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @RequestMapping(value = "/saveProductReview", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("상품 리뷰 저장하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gKey", value = "상품 키", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "userKey", value = "사용자 키", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "제목", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "contents", value = "내용", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "starPoint", value = "평가점수", dataType = "int", paramType = "query", required = true),
    })
    public ApiResultCodeDTO saveProductReview(@RequestParam("gKey") int gKey,
                                              @RequestParam("userKey") int userKey,
                                              @RequestParam("title") String title,
                                              @RequestParam("contents") String contents,
                                              @RequestParam("starPoint") int starPoint) {
        return boardService.saveProductReview(gKey, userKey, title, contents, starPoint);
    }
}
