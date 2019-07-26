package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.*;
import com.zianedu.api.mapper.BoardMapper;
import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.utils.PagingSupport;
import com.zianedu.api.utils.Util;
import com.zianedu.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class BoardService extends PagingSupport {

    @Autowired
    private BoardMapper boardMapper;

    /**
     * 리뷰 저장
     * @param gKey
     * @param userKey
     * @param title
     * @param contents
     * @param starPoint
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO saveProductReview(int gKey, int userKey, String title, String contents, int starPoint) {
        int resultCode = OK.value();

        int gReviewKey = 0;
        if (gKey == 0 && userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TGoodsReviewVO vo = new TGoodsReviewVO(gKey, userKey, title, contents, starPoint);
            boardMapper.insertTGoodsReview(vo);
            gReviewKey = vo.getGReviewKey();
        }
        return new ApiResultCodeDTO("gReviewKey", gReviewKey, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getNoticeList(int bbsMasterKey, int sPage, int listLimit, String searchType, String searchText) throws Exception {
        int resultCode = OK.value();

        List<NoticeListVO> noticeList = new ArrayList<>();
        int totalCount = 0;
        int startNumber = getPagingStartNumber(sPage, listLimit);

        if (bbsMasterKey == 0 && sPage == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            totalCount = boardMapper.selectNoticeListCount(bbsMasterKey, Util.isNullValue(searchType, ""), Util.isNullValue(searchText, ""));
            noticeList = boardMapper.selectNoticeList(bbsMasterKey, startNumber, listLimit, Util.isNullValue(searchType, ""), Util.isNullValue(searchText, ""));

            if (noticeList.size() > 0) {
                String standardDate = Util.plusDate(Util.returnNow(), -10);
                for (NoticeListVO vo : noticeList) {
                    int diffDayCnt = Util.getDiffDayCount(Util.convertDateFormat3(standardDate), Util.convertDateFormat3(vo.getIndate()));

                    if (diffDayCnt >= 0 && diffDayCnt <= 10) vo.setNew(true);
                    else vo.setNew(false);
                }
            }
        }
        return new ApiPagingResultDTO(totalCount, noticeList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getCommunityList(int bbsMasterKey, int sPage, int listLimit, String searchType, String searchText) throws Exception {
        int resultCode = OK.value();

        List<CommunityListVO> communityList = new ArrayList<>();
        int totalCount = 0;
        int startNumber = getPagingStartNumber(sPage, listLimit);

        if (bbsMasterKey == 0 && sPage == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            totalCount = boardMapper.selectNoticeListCount(bbsMasterKey, Util.isNullValue(searchType, ""), Util.isNullValue(searchText, ""));
            communityList = boardMapper.selectCommunityList(bbsMasterKey, startNumber, listLimit, Util.isNullValue(searchType, ""), Util.isNullValue(searchText, ""));

            if (communityList.size() > 0) {
                String standardDate = Util.plusDate(Util.returnNow(), -10);
                for (CommunityListVO vo : communityList) {
                    int diffDayCnt = Util.getDiffDayCount(Util.convertDateFormat3(standardDate), Util.convertDateFormat3(vo.getIndate()));

                    if (diffDayCnt >= 0 && diffDayCnt <= 10) vo.setNew(true);
                    else vo.setNew(false);
                    //합격자 영상, 지안식구 일때 이미지 썸네일 경로 주입
                    if (bbsMasterKey == 10970 || bbsMasterKey == 11031) {
                        String fileName = boardMapper.selectTBbsDataFileName(vo.getBbsKey());
                        vo.setFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), fileName));
                    }
                }
            }
        }
        return new ApiPagingResultDTO(totalCount, communityList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getBoardDetailInfo(int bbsKey) {
        int resultCode = OK.value();

        BoardDetailDTO boardDetailDTO = null;
        BoardDetailVO boardDetailVO = null;
        List<CommentListVO> commentList = new ArrayList<>();

        if (bbsKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            boardDetailVO = boardMapper.selectBoardDetailInfo(bbsKey);
            if (!"".equals(Util.isNullValue(boardDetailVO.getFileName(), ""))) {
                boardDetailVO.setFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), boardDetailVO.getFileName()));
            }
            //답글 리스트
            commentList = boardMapper.selectBoardCommentList(bbsKey);

            boardDetailDTO = new BoardDetailDTO(boardDetailVO, commentList);
        }
        return new ApiResultObjectDTO(boardDetailDTO, resultCode);
    }

    /**
     * 랜딩페이지 합격 수기, 수강후기 가져오기
     * @return
     */
    public ApiResultListDTO getReviewList(String reviewType, int listLimit) {
        int resultCode = OK.value();

        List<NoticeListVO> reviewList = new ArrayList<>();
        if ("".equals(reviewType) && listLimit == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            if ("PASS".equals(reviewType)) {
                reviewList = boardMapper.selectPassReviewList(listLimit);
            } else if ("SIGN".equals(reviewType)) {
                reviewList = boardMapper.selectSignUpReviewList(listLimit);
            }
        }
        return new ApiResultListDTO(reviewList, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO saveBoardComment(int bbsKey, int userKey, String comment) {
        int resultCode = OK.value();

        int bbsCommentKey = 0;
        if (bbsKey == 0 && userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TBbsCommentVO commentVO = new TBbsCommentVO(bbsKey, userKey, comment);
            boardMapper.insertTBbsComment(commentVO);
            bbsCommentKey = commentVO.getBbsCommentKey();
        }
        return new ApiResultCodeDTO("bbsCommentKey", bbsCommentKey, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO saveBoardInfo(int bbsMasterKey, int userKey, String title, String content, int isSecret, String fileName) {
        int resultCode = OK.value();

        int bbsKey = 0;
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TBbsDataVO bbsDataVO = new TBbsDataVO(bbsMasterKey, userKey, title, content, isSecret);
            boardMapper.insertTBbsData(bbsDataVO);
            bbsKey = bbsDataVO.getBbsKey();
            //첨부파일이 있을때 T_BBS_DATA_FILE 테이블 저장
            if (!"".equals(fileName)) {
                boardMapper.insertTBbsDataFile(bbsKey, fileName);
            }
        }
        return new ApiResultCodeDTO("bbsKey", bbsKey, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO updateBoardInfo(int bbsKey, String title, String content, int isSecret, String fileName) {
        int resultCode = OK.value();

        if (bbsKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TBbsDataVO bbsDataVO = new TBbsDataVO(bbsKey, title, content, isSecret);
            boardMapper.updateTBbsData(bbsDataVO);
            bbsKey = bbsDataVO.getBbsKey();
            //첨부파일이 있을때 T_BBS_DATA_FILE 테이블 저장
            if (!"".equals(fileName)) {
                TBbsDataFileVO fileVO = boardMapper.selectTBbsDataFile(bbsKey);
                if (fileVO == null) boardMapper.insertTBbsDataFile(bbsKey, fileName);
                else boardMapper.updateTBbsDataFile(fileVO.getBbsFileKey(), fileName);
            }
        }
        return new ApiResultCodeDTO("bbsKey", bbsKey, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO updateBoardCommentInfo(int bbsCommentKey, String comment) {
        int resultCode = OK.value();

        if (bbsCommentKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            boardMapper.updateTBbsComment(bbsCommentKey, comment);
        }
        return new ApiResultCodeDTO("bbsCommentKey", bbsCommentKey, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO deleteBoard(int bbsKey) {
        int resultCode = OK.value();

        if (bbsKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            boardMapper.deleteTBbsData(bbsKey);
            boardMapper.deleteTBbsDataFile(bbsKey);
            boardMapper.deleteTBbsCommentFromBbsKey(bbsKey);
        }
        return new ApiResultCodeDTO("bbsKey", bbsKey, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO deleteBoardComment(int bbsCommentKey) {
        int resultCode = OK.value();

        if (bbsCommentKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            boardMapper.deleteTBbsComment(bbsCommentKey);
        }
        return new ApiResultCodeDTO("bbsCommentKey", bbsCommentKey, resultCode);
    }

}
