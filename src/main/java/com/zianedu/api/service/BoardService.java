package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.*;
import com.zianedu.api.mapper.BoardMapper;
import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.utils.PagingSupport;
import com.zianedu.api.utils.Util;
import com.zianedu.api.utils.ZianUtils;
import com.zianedu.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class BoardService extends PagingSupport {

    @Autowired
    private BoardMapper boardMapper;

    protected int resultCode = OK.value();

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

                    if (!"".equals(vo.getFileName())) {
                        vo.setFileName(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getFileName()));
                    }
                }
            }
        }
        return new ApiPagingResultDTO(totalCount, noticeList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getBannerNoticeList(int bbsMasterKey, int sPage, int listLimit) throws Exception {
        int resultCode = OK.value();

        List<NoticeListVO> noticeList = new ArrayList<>();
        int startNumber = getPagingStartNumber(sPage, listLimit);

        if (bbsMasterKey == 0 && sPage == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            noticeList = boardMapper.selectBannerNoticeList(bbsMasterKey, startNumber, listLimit);

            if (noticeList.size() > 0) {
                String standardDate = Util.plusDate(Util.returnNow(), -10);
                for (NoticeListVO vo : noticeList) {
                    int diffDayCnt = Util.getDiffDayCount(Util.convertDateFormat3(standardDate), Util.convertDateFormat3(vo.getIndate()));

                    if (diffDayCnt >= 0 && diffDayCnt <= 10) vo.setNew(true);
                    else vo.setNew(false);
                }
            }
        }
        return new ApiResultListDTO(noticeList, resultCode);
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

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultObjectDTO getBoardDetailInfo(int bbsMasterKey, int bbsKey) {
        int resultCode = OK.value();

        BoardDetailDTO boardDetailDTO = null;
        BoardDetailVO boardDetailVO = null;
        List<CommentListVO> commentList = new ArrayList<>();

        if (bbsKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            boardDetailVO = boardMapper.selectBoardDetailInfo(bbsKey);

            //ReferenceRoomDetailVO referenceRoomDetailVO = boardMapper.selectTeacherReferenceRoomDetailInfo(bbsKey);
            if (boardDetailVO != null) {
                //파일정보 주입 시작
                List<String> fileNameList = boardMapper.selectTBbsDataFileNameList(bbsKey);
                if (fileNameList.size() > 0) {
                    List<FileInfoDTO> fileInfoList = new ArrayList<>();
                    for (String file : fileNameList) {
                        FileInfoDTO fileInfoDTO = new FileInfoDTO();
                        String fileName = ZianUtils.getSplitFileName(file);
                        String fileUrl = FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), file);
                        fileInfoDTO.setFileName(fileName);
                        fileInfoDTO.setFileUrl(fileUrl);

                        fileInfoList.add(fileInfoDTO);
                    }
                    boardDetailVO.setFileInfo(fileInfoList);
                    //파일정보 주입 끝
                }
            }

//            if (!"".equals(Util.isNullValue(boardDetailVO.getFileName(), ""))) {
//                boardDetailVO.setFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), boardDetailVO.getFileName()));
//            }

            //답글 리스트
            commentList = boardMapper.selectBoardCommentList(bbsKey);
            //이전,다음글 정보
            PrevNextVO prevNextVO = boardMapper.selectNoticePrevNextInfo(bbsMasterKey, bbsKey);
            //ReadCount 증가
            boardMapper.updateTBbsReadCount(bbsKey);

            boardDetailDTO = new BoardDetailDTO(boardDetailVO, commentList, prevNextVO);
        }
        return new ApiResultObjectDTO(boardDetailDTO, resultCode);
    }

    /**
     * 랜딩페이지 합격 수기, 수강후기 가져오기
     * @return
     */
    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public ApiResultListDTO getLectureRoomTableList(String lectureDate) {
        List<LectureRoomTableVO> roomTableList = new ArrayList<>();

        if ("".equals(lectureDate)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            roomTableList = boardMapper.selectLectureRoomTableList(lectureDate);
            if (roomTableList.size() > 0) {
                for (LectureRoomTableVO vo : roomTableList) {
                    vo.setFileName(ConfigHolder.getFileDomainUrl() + "/100/bbs/" + vo.getFileName());
                }
            }
        }
        return new ApiResultListDTO(roomTableList, resultCode);
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
    public ApiResultCodeDTO saveBoardInfo(int bbsMasterKey, int userKey, String title, String content, int isSecret, int ctgKey, String fileName) {
        int resultCode = OK.value();

        int bbsKey = 0;
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TBbsDataVO bbsDataVO = new TBbsDataVO(bbsMasterKey, userKey, title, content, isSecret, ctgKey);
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
    public ApiResultCodeDTO saveBoardInfoAtNoUser(int bbsMasterKey, int userKey, String title, String content, int isSecret, String fileName) {
        int bbsKey = 0;

        TBbsDataVO bbsDataVO = new TBbsDataVO(bbsMasterKey, userKey, title, content, isSecret, 0);
        boardMapper.insertTBbsData(bbsDataVO);
        bbsKey = bbsDataVO.getBbsKey();
        //첨부파일이 있을때 T_BBS_DATA_FILE 테이블 저장
        if (!"".equals(fileName)) {
            boardMapper.insertTBbsDataFile(bbsKey, fileName);
        }
        return new ApiResultCodeDTO("bbsKey", bbsKey, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO saveLectureRoomTable(String lectureDate, int academyNumber, String fileName) {
        if ("".equals(lectureDate) && academyNumber == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            LectureRoomTableVO lectureRoomTableVO = new LectureRoomTableVO(lectureDate, academyNumber, fileName);
            boardMapper.deleteTLectureRoomTable(lectureDate, academyNumber);
            boardMapper.insertTLectureRoomTable(lectureRoomTableVO);
        }
        return new ApiResultCodeDTO("lectureDate", lectureDate, resultCode);
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

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO saveTeacherBoardInfo(int bbsMasterKey, int teacherKey, int userKey, String title, String content, int isNotice, int isSecret, String fileName) {
        int bbsKey = 0;
        if (bbsMasterKey == 0 && teacherKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TBbsDataVO bbsDataVO = new TBbsDataVO(bbsMasterKey, teacherKey, userKey, title, content, isSecret, isNotice);
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
    public ApiResultCodeDTO saveBoardFileList(int bbsKey, String[] fileNames) {
        if (bbsKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            if (fileNames.length > 0) {
                List<String>fileNameList = Arrays.asList(fileNames);
                for (String fileName : fileNameList) {
                    boardMapper.insertTBbsDataFile(bbsKey, fileName);
                }
            }
        }
        return new ApiResultCodeDTO("bbsKey", bbsKey, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO saveBoardReply(int bbsMasterKey, int bbsParentKey, int userKey, String title, String content, int isSecret) {
        int bbsKey = 0;
        if (bbsParentKey == 0 && userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            BoardDetailVO vo = boardMapper.selectBoardDetailInfo(bbsParentKey);
            TBbsDataVO bbsDataVO = new TBbsDataVO(bbsMasterKey, bbsParentKey, vo.getCtgKey(), userKey, title, content, isSecret, 0);
            boardMapper.insertTBbsData(bbsDataVO);
            bbsKey = bbsDataVO.getBbsKey();
        }
        return new ApiResultCodeDTO("bbsKey", bbsKey, resultCode);
    }

}
