package com.zianedu.api.controller;

import com.zianedu.api.dto.*;
import com.zianedu.api.service.BoardService;
import com.zianedu.api.utils.GsonUtil;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ApiOperation("게시판 댓글 저장")
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
            @ApiImplicitParam(name = "ctgKey", value = "카테고리 키값", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "fileName", value = "파일명", dataType = "String", paramType = "query", required = false)

    })
    public ApiResultCodeDTO saveBoard(@RequestParam("bbsMasterKey") int bbsMasterKey,
                                      @RequestParam("userKey") int userKey,
                                      @RequestParam("title") String title,
                                      @RequestParam("content") String content,
                                      @RequestParam("isSecret") int isSecret,
                                      @RequestParam("ctgKey") int ctgKey,
                                      @RequestParam(value = "fileName", required = false, defaultValue = "") String fileName) {
        return boardService.saveBoardInfo(bbsMasterKey, userKey, title, content, isSecret, ctgKey, fileName, "", 0, "", "");
    }

    @RequestMapping(value = "/saveBoardByAlliance", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("제휴 문의 게시글 저장")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsMasterKey", value = "게시판 마스터 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "게시판 제목", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "content", value = "게시판 내용", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultCodeDTO saveBoardByAlliance(@RequestParam("bbsMasterKey") int bbsMasterKey,
                                      @RequestParam("title") String title,
                                      @RequestParam("content") String content) {
        return boardService.saveBoardInfoAtNoUser(bbsMasterKey, 0, title, content, 0, "");
    }

    @RequestMapping(value = "/saveBoardReply", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("게시판 답글 저장")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsMasterKey", value = "게시판 마스터 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "bbsParentKey", value = "답글의 부모글 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "게시판 제목", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "content", value = "게시판 내용", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "isSecret", value = "공개/비공개 여부(0 : 공개, 1 : 비공개)", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultCodeDTO saveBoardReply(@RequestParam("bbsMasterKey") int bbsMasterKey,
                                      @RequestParam("bbsParentKey") int bbsParentKey,
                                      @RequestParam("userKey") int userKey,
                                      @RequestParam("title") String title,
                                      @RequestParam("content") String content,
                                      @RequestParam("isSecret") int isSecret) {
        return boardService.saveBoardReply(bbsMasterKey, bbsParentKey, userKey, title, content, isSecret);
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

    @RequestMapping(value = "/updateBoardByAlliance", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("제휴 문의 게시글 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsKey", value = "게시판 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "게시판 제목", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "content", value = "게시판 내용", dataType = "String", paramType = "query", required = true)
    })
    public ApiResultCodeDTO updateBoardByAlliance(@RequestParam("bbsKey") int bbsKey,
                                        @RequestParam("title") String title,
                                        @RequestParam("content") String content) {
        return boardService.updateBoardInfo(bbsKey, title, content, 0, "");
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

    @RequestMapping(value = "/getLectureRoomTableList/{lectureDate}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("강의실 배정표 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lectureDate", value = "조회할 날짜(YYYY-MM-DD)", dataType = "string", paramType = "path", required = true)
    })
    public ApiResultListDTO getLectureRoomTableList(@PathVariable("lectureDate") String lectureDate) {
        return boardService.getLectureRoomTableList(lectureDate);
    }

    @RequestMapping(value = "/saveLectureRoomTable", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("강의실 배정표 저장")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lectureDate", value = "입력 날짜(YYYY-MM-DD)", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "academyNumber", value = "학원관 번호(1관 : 1, 2관 : 2)", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "fileName", value = "배정표 파일명", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultCodeDTO saveLectureRoomTable(@RequestParam("lectureDate") String lectureDate,
                                                 @RequestParam("academyNumber") int academyNumber,
                                                 @RequestParam("fileName") String fileName) {
        return boardService.saveLectureRoomTable(lectureDate, academyNumber, fileName);
    }

    @RequestMapping(value = "/saveTeacherBoard", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("강사 게시판 글 저장")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsMasterKey", value = "게시판 마스터 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "teacherKey", value = "강사 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "게시판 제목", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "content", value = "게시판 내용", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "isNotice", value = "공지여부(0 : 일반, 1 : 공지)", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "isSecret", value = "비밀여부(0 : 공개, 1 : 비밀글)", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "fileName", value = "파일명", dataType = "String", paramType = "query", required = false)
    })
    public ApiResultCodeDTO saveTeacherBoard(@RequestParam("bbsMasterKey") int bbsMasterKey,
                                        @RequestParam("teacherKey") int teacherKey,
                                        @RequestParam("userKey") int userKey,
                                        @RequestParam("title") String title,
                                        @RequestParam("content") String content,
                                        @RequestParam("isNotice") int isNotice,
                                         @RequestParam("isSecret") int isSecret,
                                        @RequestParam(value = "fileName", required = false, defaultValue = "") String fileName) {
        return boardService.saveTeacherBoardInfo(bbsMasterKey, teacherKey, userKey, title, content, isNotice, isSecret, fileName);
    }

    @RequestMapping(value = "/saveBoardFileList", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("게시판 파일 다중 저장")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsKey", value = "게시판 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "fileName", value = "파일명 리스트 >> ['파일명','파일명',...]", dataType = "object", paramType = "query", required = true)
    })
    public ApiResultCodeDTO saveBoardFileList(@RequestParam("bbsKey") int bbsKey,
                                              @RequestParam("fileName") String fileName) {
        String[] fileNames = GsonUtil.convertToStringArrayFromString(fileName);
        return boardService.saveBoardFileList(bbsKey, fileNames);
    }

    @RequestMapping(value = "/getPasserVideoList", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("합격자영상 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "리스트 개수", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "searchType", value = "검색 종류(title : 제목, name : 이름, content : 내용) ", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "searchText", value = "검색 값", dataType = "string", paramType = "query", required = false)
    })
    public ApiPagingResultDTO getPasserVideoList(@RequestParam("sPage") int sPage,
                                               @RequestParam("listLimit") int listLimit,
                                               @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
                                               @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) throws Exception {
        return boardService.getPasserVideoList(sPage, listLimit, searchType, searchText);
    }

    @RequestMapping(value = "/getPasserVideoListFromReview/{bbsMasterKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("합격수기 > 합격자영상 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsMasterKey", value = "게시판 키값(11045 : 행정직, 10970 : 기술직/계리직)", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "리스트 개수", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "searchType", value = "검색 종류(title : 제목, name : 이름, content : 내용) ", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "searchText", value = "검색 값", dataType = "string", paramType = "query", required = false)
    })
    public ApiPagingResultDTO getPasserVideoListFromReview(@PathVariable("bbsMasterKey") int bbsMasterKey,
                                                 @RequestParam("sPage") int sPage,
                                                 @RequestParam("listLimit") int listLimit,
                                                 @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
                                                 @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) throws Exception {
        return boardService.getPasserVideoListFromReview(bbsMasterKey, sPage, listLimit, searchType, searchText);
    }

    @RequestMapping(value = "/getReviewBoardList/{bbsMasterKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("합격수 > 합격수기, 수강후기, 도서후기 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsMasterKey", value = "게시판 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "리스트 개수", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "searchType", value = "검색 종류(title : 제목, name : 이름, content : 내용) ", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "searchText", value = "검색 값", dataType = "string", paramType = "query", required = false)
    })
    public ApiPagingResultDTO getReviewBoardList(@PathVariable("bbsMasterKey") int bbsMasterKey,
                                                           @RequestParam("sPage") int sPage,
                                                           @RequestParam("listLimit") int listLimit,
                                                           @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
                                                           @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) throws Exception {
        return boardService.getReviewBoardList(bbsMasterKey, sPage, listLimit, searchType, searchText);
    }

    @RequestMapping(value = "/saveBoardReview", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("합격수기 글 저장")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsMasterKey", value = "게시판 마스터 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "게시판 제목", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "content", value = "게시판 내용", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "isSecret", value = "공개/비공개 여부(0 : 공개, 1 : 비공개)", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "ctgKey", value = "카테고리 키값", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "fileName", value = "파일명", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "youtubeHtml", value = "html코드", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "gKey", value = "수강후기 상품키값", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "successSubject", value = "합격과목", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "lectureSubject", value = "수강과목, 도서명", dataType = "String", paramType = "query", required = false)

    })
    public ApiResultCodeDTO saveBoardReview(@RequestParam("bbsMasterKey") int bbsMasterKey,
                                      @RequestParam("userKey") int userKey,
                                      @RequestParam("title") String title,
                                      @RequestParam("content") String content,
                                      @RequestParam("isSecret") int isSecret,
                                      @RequestParam(value = "ctgKey" ,required = false, defaultValue = "0") int ctgKey,
                                      @RequestParam(value = "fileName", required = false, defaultValue = "") String fileName,
                                      @RequestParam(value = "youtubeHtml", required = false, defaultValue = "") String youtubeHtml,
                                      @RequestParam(value = "gKey", required = false, defaultValue = "0") int gKey,
                                      @RequestParam(value = "successSubject", required = false, defaultValue = "") String successSubject,
                                      @RequestParam(value = "lectureSubject", required = false, defaultValue = "") String lectureSubject) {
        return boardService.saveBoardInfo(bbsMasterKey, userKey, title, content, isSecret, ctgKey, fileName, youtubeHtml, gKey, successSubject, lectureSubject);
    }

    @RequestMapping(value = "/getReviewBoardCount/{bbsMasterKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("합격수기 글 총 개수 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsMasterKey", value = "게시판 키값", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultCodeDTO getReviewBoardCount(@PathVariable("bbsMasterKey") int bbsMasterKey) {
        return boardService.getReviewBoardCount(bbsMasterKey);
    }

    @RequestMapping(value = "/updateBoardReview", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("게시판 글 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsKey", value = "게시판 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "게시판 제목", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "content", value = "게시판 내용", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "isSecret", value = "공개/비공개 여부(0 : 공개, 1 : 비공개)", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "fileName", value = "파일명", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "youtubeHtml", value = "html코드", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "gKey", value = "수강후기 상품키값", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "successSubject", value = "합격과목", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "lectureSubject", value = "수강과목, 도서명", dataType = "String", paramType = "query", required = false)
    })
    public ApiResultCodeDTO updateBoard(@RequestParam("bbsKey") int bbsKey,
                                        @RequestParam("title") String title,
                                        @RequestParam("content") String content,
                                        @RequestParam("isSecret") int isSecret,
                                        @RequestParam(value = "fileName", required = false, defaultValue = "") String fileName,
                                        @RequestParam(value = "youtubeHtml", required = false, defaultValue = "") String youtubeHtml,
                                        @RequestParam(value = "gKey", required = false, defaultValue = "0") int gKey,
                                        @RequestParam(value = "successSubject", required = false, defaultValue = "") String successSubject,
                                        @RequestParam(value = "lectureSubject", required = false, defaultValue = "") String lectureSubject) {
        return boardService.updateBoardReviewInfo(bbsKey, title, content, isSecret, fileName, youtubeHtml, gKey, successSubject, lectureSubject);
    }

    @RequestMapping(value = "/deleteBoardFile/{bbsFileKey}", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("게시판 첨부 파일 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bbsFileKey", value = "게시판 파일 키값", dataType = "int", paramType = "path", required = true)

    })
    public ApiResultCodeDTO deleteBoardFile(@PathVariable("bbsFileKey") int bbsFileKey) {
        return boardService.deleteBoardFile(bbsFileKey);
    }

    @RequestMapping(value = "/getFaQList/{faqTypeKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("자주하는 질문 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "faqTypeKey", value = "자주하는 질문 종류 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "리스트 개수", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "searchType", value = "검색 종류(title : 제목, name : 이름, content : 내용) ", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "searchText", value = "검색 값", dataType = "string", paramType = "query", required = false),

    })
    public ApiPagingResultDTO getFaQList(@PathVariable("faqTypeKey") int faqTypeKey,
                                           @RequestParam("sPage") int sPage,
                                           @RequestParam("listLimit") int listLimit,
                                           @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
                                           @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) throws Exception {
        return boardService.getFaQList(faqTypeKey, sPage, listLimit, searchType, searchText);
    }
}
