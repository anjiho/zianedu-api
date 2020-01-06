package com.zianedu.api.mapper;

import com.zianedu.api.dto.ConsultReserveListDTO;
import com.zianedu.api.vo.*;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardMapper {

    /** SELECT **/

    List<TBbsDataVO> selectTBbsDataList(@Param("bbsMasterKey") int bbsMasterKey, @Param("teacherKey") int teacherKey, @Param("listType") String listType,
                                         @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    List<ReferenceRoomVO> selectTBbsDataListBySearch(@Param("bbsMasterKey") int bbsMasterKey, @Param("teacherKey") int teacherKey,
                                                     @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber,
                                                     @Param("searchType") String searchType, @Param("searchText") String searchText,
                                                     @Param("isNotice") int isNotice);

    int selectTBbsDataListBySearchCount(@Param("bbsMasterKey") int bbsMasterKey, @Param("teacherKey") int teacherKey,
                                        @Param("searchType") String searchType, @Param("searchText") String searchText,
                                        @Param("isNotice") int isNotice);

    int selectLeaningTBbsDataListBySearchCount(@Param("bbsMasterKey") int bbsMasterKey, @Param("teacherKey") int teacherKey,
                                        @Param("searchType") String searchType, @Param("searchText") String searchText,
                                        @Param("isNotice") int isNotice);

    List<GoodsReviewVO> selectGoodsReviewList(@Param("teacherKey") int teacherKey, @Param("listType") String listType,
                                           @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);


    List<GoodsReviewVO> selectTeacherReviewList(@Param("teacherKey") int teacherKey, @Param("gKey") int gKey,
                                                @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    int selectTeacherReviewListCount(@Param("teacherKey") int teacherKey, @Param("gKey") int gKey);

    ReferenceRoomDetailVO selectTeacherReferenceRoomDetailInfo(@Param("bbsKey") int bbsKey);

    List<PrevNextVO> selectTeacherReferenceRoomPrevNext(@Param("bbsMasterKey") int bbsMasterKey, @Param("teacherKey") int teacherKey, @Param("bbsKey") int bbsKey, @Param("isNotice") int isNotice);

    List<NoticeListVO> selectNoticeList(@Param("bbsMasterKey") int bbsMasterKey, @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber,
                                        @Param("searchType") String searchType, @Param("searchText") String searchText);

    List<NoticeListVO> selectBannerNoticeList(@Param("bbsMasterKey") int bbsMasterKey, @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    Integer selectNoticeListCount(@Param("bbsMasterKey") int bbsMasterKey, @Param("searchType") String searchType, @Param("searchText") String searchText);

    List<CommunityListVO> selectCommunityList(@Param("bbsMasterKey") int bbsMasterKey, @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber,
                                              @Param("searchType") String searchType, @Param("searchText") String searchText);

    String selectTBbsDataFileName(@Param("bbsKey") int bbsKey);

    List<String> selectTBbsDataFileNameList(@Param("bbsKey") int bbsKey);

    BoardDetailVO selectBoardDetailInfo(@Param("bbsKey") int bbsKey);

    List<CommentListVO> selectBoardCommentList(@Param("bbsKey") int bbsKey);

    TBbsDataFileVO selectTBbsDataFile(@Param("bbsKey") int bbsKey);

    List<NoticeListVO> selectPassReviewList(@Param("listLimitNumber") int listLimitNumber);

    List<NoticeListVO> selectSignUpReviewList(@Param("listLimitNumber") int listLimitNumber);

    PrevNextVO selectNoticePrevNextInfo(@Param("bbsMasterKey") int bbsMasterKey, @Param("bbsKey") int bbsKey);

    List<PrevNextVO> selectBoardPrevNextInfoByReply(@Param("bbsMasterKey") int bbsMasterKey, @Param("teacherKey") int teacherKey, @Param("bbsKey") int bbsKey, @Param("isNotice") int isNotice);

    List<LectureRoomTableVO> selectLectureRoomTableList(@Param("lectureDate") String lectureDate);

    List<ReferenceRoomVO> selectOneByOneQuestionList(@Param("userKey") int userKey, @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber,
                                                     @Param("searchType") String searchType, @Param("searchText") String searchText);

    int selectOneByOneQuestionListCount(@Param("userKey") int userKey, @Param("searchType") String searchType, @Param("searchText") String searchText);

    ReferenceRoomVO selectOneByOneQuestionListByBbsParentKey(@Param("bbsParentKey") int bbsParentKey, @Param("searchType") String searchType, @Param("searchText") String searchText);

    ReferenceRoomVO selectOneByOneQuestionListByBbsKey(@Param("bbsKey") int bbsKey);

    int selectConsultReserveCount(@Param("reserveDate") String reserveDate, @Param("reserveTimeKey") int reserveTimeKey,
                                  @Param("reserveLocation") int reserveLocation);

    List<TConsultReserveVO> selectConsultReserveTimeList(@Param("reserveDate") String reserveDate, @Param("reserveLocation") int reserveLocation);

    List<TBbsDataVO> selectTBbsDataByCtgKey(@Param("ctgKey") int ctgKey);

    List<ConsultReserveListDTO> selectConsultReserveList(@Param("userKey") int userKey, @Param("reserveDate") String reserveDate,
                                                         @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    int selectConsultReserveListCount(@Param("userKey") int userKey, @Param("reserveDate") String reserveDate);

    List<TBbsDataVO> selectBoardListAtMyWrite(@Param("userKey") int userKey, @Param("boardType") String boardType,
                                              @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber,
                                              @Param("searchType") String searchType, @Param("searchText") String searchText);

    int selectBoardListAtMyWriteCount(@Param("userKey") int userKey, @Param("boardType") String boardType,
                                      @Param("searchType") String searchType, @Param("searchText") String searchText);

    Integer selectPasserVideoListCount(@Param("searchType") String searchType, @Param("searchText") String searchText);

    List<CommunityListVO> selectPasserVideoList(@Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber,
                                              @Param("searchType") String searchType, @Param("searchText") String searchText);

    /** INSERT **/
    void insertTGoodsReview(TGoodsReviewVO tGoodsReviewVO);

    void insertTBbsComment(TBbsCommentVO tBbsCommentVO);

    void insertTBbsData(TBbsDataVO tBbsDataVO);

    void insertTBbsDataFile(@Param("bbsKey") int bbsKey, @Param("fileName") String fileName);

    void insertTLectureRoomTable(LectureRoomTableVO lectureRoomTableVO);

    void insertTConsultReserve(TConsultReserveVO tConsultReserveVO);

    /** UPDATE **/
    void updateTBbsData(TBbsDataVO tBbsDataVO);

    void updateTBbsDataFile(@Param("bbsFileKey") int bbsFileKey, @Param("fileName") String fileName);

    void updateTBbsComment(@Param("bbsCommentKey") int bbsCommentKey, @Param("comment") String comment);

    void updateTBbsReadCount(@Param("bbsKey") int bbsKey);

    void updateTConsultReserveStatus(@Param("idxs") List<Integer>idxs, @Param("consultStatus") int consultStatus);

    /** DELETE **/
    void deleteTBbsData(@Param("bbsKey") int bbsKey);

    void deleteTBbsDataFile(@Param("bbsKey") int bbsKey);

    void deleteTBbsCommentFromBbsKey(@Param("bbsKey") int bbsKey);

    void deleteTBbsComment(@Param("bbsCommentKey") int bbsCommentKey);

    void deleteTLectureRoomTable(@Param("lectureDate") String lectureDate, @Param("academyNumber") int academyNumber);
}
