package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiPagingResultDTO;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.BookStoreService;
import com.zianedu.api.service.CategoryService;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bookStore")
public class BookStoreController {

    @Autowired
    private BookStoreService bookStoreService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/getBannerList/{ctgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("온라인서점 도서 배너 목록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctgKey", value = "카테고리 키값(841 : MD추천, 842 : 화제의도서, 843 : 행정직베스트도서, 844 : 기술직베스트도서, 845 : 계리직베스트도서, 839 : 새로나온책, 846 : 공통과목(BEST), 847 : 행정직(BEST), 848 : 기술직(BEST), 849 : 계리직(BEST), 850 : 자격증/가산점(BEST), 851 : 모의고사(BEST)",
                                dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "listLimit", value = "리스트 개수", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultListDTO getTopBannerList(@PathVariable(value = "ctgKey") int ctgKey,
                                             @RequestParam(value = "listLimit") int listLimit) {
        return bookStoreService.getBookListFromOnlineStoreTopBanner(ctgKey, listLimit);
    }

    @RequestMapping(value = "/getBookList/{leftMenuCtgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("온라인서점 도서 목록(페이징)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "leftMenuCtgKey", value = "( /menu/getLeftMenu/{ctgKey} ) 리턴값 정의", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "리스트 개수", dataType = "int", paramType = "query", required = true)
    })
    public ApiPagingResultDTO getBookList(@PathVariable(value = "leftMenuCtgKey") int ctgKey,
                                          @RequestParam(value = "sPage") int sPage,
                                          @RequestParam(value = "listLimit") int listLimit) {
        return bookStoreService.getBookListFromLeftMenuCtgKey(ctgKey, sPage, listLimit);
    }

    @RequestMapping(value = "/getBestBookList", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("온라인서점 > 지안에듀 BEST 도서 리스트")
    public ApiResultListDTO getBestBookList() {
        return bookStoreService.getBestBookList();
    }

    @RequestMapping(value = "/getNewBookList", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("온라인서점 > 새로나온 책 리스트")
    public ApiResultListDTO getNewBookList() {
        return bookStoreService.getNewBookList();
    }

    @RequestMapping(value = "/getSalesBookList/{bookMenuType}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("온라인서점 도서 목록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookMenuType", value = "공통과목 : COMMON, 행정직군 : PUBLIC, 기술직군 : TECH, 계리직군 : POST, 자격증 : CERT", dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "searchText", value = "검색명", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "orderType", value = "출간일순 : date, 저자순 : name", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "리스트 개수", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "subjectKey", value = "과목 키값", dataType = "int", paramType = "query", required = false)
    })
    public ApiPagingResultDTO getSalesBookList(@PathVariable(value = "bookMenuType") String bookMenuType,
                                               @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
                                               @RequestParam(value = "orderType") String orderType,
                                               @RequestParam(value = "sPage") int sPage,
                                               @RequestParam(value = "listLimit") int listLimit,
                                               @RequestParam(value = "subjectKey", required = false, defaultValue = "0") int subjectKey) {
        return bookStoreService.getSalesBookList(bookMenuType, searchText, orderType, sPage, listLimit, subjectKey);
    }

    @RequestMapping(value = "/getBookDetailInfo/{gKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("도서 상세정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gKey", value = "상품 키값", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultObjectDTO getBookDetailInfo(@PathVariable(value = "gKey") int gKey) {
        return bookStoreService.getBookDetailInfo(gKey);
    }

    @RequestMapping(value = "/getBookStoreSelectBoxList/{bookStoreType}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("도서 과목 종류 셀렉트박스")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookStoreType", value = "도서 종류 키값(공통과목:COMMON, 기술직:TECH, 행정직:PUBLIC, 계리직:POST, 면접:CERT)", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultListDTO getBookStoreSelectBoxList(@PathVariable(value = "bookStoreType") String bookStoreType) {
        return categoryService.getBookStoreSelectBoxList(bookStoreType);
    }
}
