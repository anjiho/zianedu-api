package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiPagingResultDTO;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.BoardService;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/getNoticeList/{bbsMasterKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("공지사항 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsMasterKey", value = "게시판 마스터 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "리스트 개수", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "searchType", value = "검색 종류(title : 제목, name : 이름, content : 내용) ", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "searchText", value = "검색 값", dataType = "string", paramType = "query", required = false),

    })
    public ApiPagingResultDTO getNoticeList(@PathVariable("bbsMasterKey") int bbsMasterKey,
                                             @RequestParam("sPage") int sPage,
                                             @RequestParam("listLimit") int listLimit,
                                             @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
                                             @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) throws Exception {
        return boardService.getNoticeList(bbsMasterKey, sPage, listLimit, searchType, searchText);
    }

    @RequestMapping(value = "/getCommunityList/{bbsMasterKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("커뮤니티 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsMasterKey", value = "게시판 마스터 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "리스트 개수", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "searchType", value = "검색 종류(title : 제목, name : 이름, content : 내용) ", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "searchText", value = "검색 값", dataType = "string", paramType = "query", required = false),

    })
    public ApiPagingResultDTO getCommunityList(@PathVariable("bbsMasterKey") int bbsMasterKey,
                                            @RequestParam("sPage") int sPage,
                                            @RequestParam("listLimit") int listLimit,
                                            @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
                                            @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) throws Exception {
        return boardService.getCommunityList(bbsMasterKey, sPage, listLimit, searchType, searchText);
    }

    @RequestMapping(value = "/getBoardDetailInfo/{bbsMasterKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("게시판 상세정보(공지, 커뮤니티)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsMasterKey", value = "게시판 마스터 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "bbsKey", value = "게시판 키값", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultObjectDTO getBoardDetailInfo(@PathVariable("bbsMasterKey") int bbsMasterKey,
                                                 @RequestParam("bbsKey") int bbsKey) {
        return boardService.getBoardDetailInfo(bbsMasterKey, bbsKey);
    }

    @RequestMapping(value = "/saveBoardComment", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("게시판 답글 저장")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsKey", value = "게시판 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "comment", value = "답글 내용", dataType = "String", paramType = "query", required = true)

    })
    public ApiResultCodeDTO saveBoardComment(@RequestParam("bbsKey") int bbsKey,
                                             @RequestParam("userKey") int userKey,
                                             @RequestParam("comment") String comment) {
        return boardService.saveBoardComment(bbsKey, userKey, comment);
    }

    @RequestMapping(value = "/saveBoard", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("게시판 글 저장")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsMasterKey", value = "게시판 마스터 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "게시판 제목", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "content", value = "게시판 내용", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "isSecret", value = "공개/비공개 여부(0 : 공개, 1 : 비공개)", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "fileName", value = "파일명", dataType = "String", paramType = "query", required = false)

    })
    public ApiResultCodeDTO saveBoard(@RequestParam("bbsMasterKey") int bbsMasterKey,
                                      @RequestParam("userKey") int userKey,
                                      @RequestParam("title") String title,
                                      @RequestParam("content") String content,
                                      @RequestParam("isSecret") int isSecret,
                                      @RequestParam(value = "fileName", required = false, defaultValue = "") String fileName) {
        return boardService.saveBoardInfo(bbsMasterKey, userKey, title, content, isSecret, fileName);
    }

    @RequestMapping(value = "/updateBoard", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("게시판 글 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsKey", value = "게시판 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "게시판 제목", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "content", value = "게시판 내용", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "isSecret", value = "공개/비공개 여부(0 : 공개, 1 : 비공개)", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "fileName", value = "파일명", dataType = "String", paramType = "query", required = false)

    })
    public ApiResultCodeDTO updateBoard(@RequestParam("bbsKey") int bbsKey,
                                        @RequestParam("title") String title,
                                        @RequestParam("content") String content,
                                        @RequestParam("isSecret") int isSecret,
                                        @RequestParam(value = "fileName", required = false, defaultValue = "") String fileName) {
        return boardService.updateBoardInfo(bbsKey, title, content, isSecret, fileName);
    }

    @RequestMapping(value = "/updateBoardComment", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("게시판 답글 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsCommentKey", value = "답글 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "comment", value = "답글 내용", dataType = "String", paramType = "query", required = true)

    })
    public ApiResultCodeDTO updateBoardComment(@RequestParam("bbsCommentKey") int bbsCommentKey,
                                        @RequestParam("comment") String comment) {
        return boardService.updateBoardCommentInfo(bbsCommentKey, comment);
    }

    @RequestMapping(value = "/deleteBoard", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("게시판 글 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsKey", value = "게시판 키값", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultCodeDTO deleteBoard(@RequestParam("bbsKey") int bbsKey) {
        return boardService.deleteBoard(bbsKey);
    }

    @RequestMapping(value = "/deleteBoardComment", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("게시판 답글 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsCommentKey", value = "게시판 답글 키값", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultCodeDTO deleteBoardComment(@RequestParam("bbsCommentKey") int bbsCommentKey) {
        return boardService.deleteBoardComment(bbsCommentKey);
    }

    @RequestMapping(value = "/getReviewList/{reviewType}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("합격수기, 수강후기 리스트 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reviewType", value = "후기 종류(PASS : 합격수기, SIGN : 수강후기)", dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "listLimit", value = "리스트 개수", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultListDTO getReviewList(@PathVariable("reviewType") String reviewType,
                                          @RequestParam("listLimit") int listLimit) {
        return boardService.getReviewList(reviewType, listLimit);
    }

    @RequestMapping(value = "/getBannerNoticeList/{bbsMasterKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("배너 공지사항 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsMasterKey", value = "게시판 마스터 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "리스트 개수", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultListDTO getNoticeList(@PathVariable("bbsMasterKey") int bbsMasterKey,
                                            @RequestParam("sPage") int sPage,
                                            @RequestParam("listLimit") int listLimit) throws Exception {
        return boardService.getBannerNoticeList(bbsMasterKey, sPage, listLimit);
    }
}
