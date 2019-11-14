package com.zianedu.api.mapper;

import com.zianedu.api.vo.*;
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

    List<GoodsReviewVO> selectGoodsReviewList(@Param("teacherKey") int teacherKey, @Param("listType") String listType,
                                           @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);


    List<GoodsReviewVO> selectTeacherReviewList(@Param("teacherKey") int teacherKey, @Param("gKey") int gKey,
                                                @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    int selectTeacherReviewListCount(@Param("teacherKey") int teacherKey, @Param("gKey") int gKey);

    ReferenceRoomDetailVO selectTeacherReferenceRoomDetailInfo(@Param("bbsKey") int bbsKey);

    List<PrevNextVO> selectTeacherReferenceRoomPrevNext(@Param("bbsMasterKey") int bbsMasterKey, @Param("teacherKey") int teacherKey, @Param("bbsKey") int bbsKey);

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

    List<LectureRoomTableVO> selectLectureRoomTableList(@Param("lectureDate") String lectureDate);


    /** INSERT **/
    void insertTGoodsReview(TGoodsReviewVO tGoodsReviewVO);

    void insertTBbsComment(TBbsCommentVO tBbsCommentVO);

    void insertTBbsData(TBbsDataVO tBbsDataVO);

    void insertTBbsDataFile(@Param("bbsKey") int bbsKey, @Param("fileName") String fileName);

    void insertTLectureRoomTable(LectureRoomTableVO lectureRoomTableVO);

    /** UPDATE **/
    void updateTBbsData(TBbsDataVO tBbsDataVO);

    void updateTBbsDataFile(@Param("bbsFileKey") int bbsFileKey, @Param("fileName") String fileName);

    void updateTBbsComment(@Param("bbsCommentKey") int bbsCommentKey, @Param("comment") String comment);

    void updateTBbsReadCount(@Param("bbsKey") int bbsKey);

    /** DELETE **/
    void deleteTBbsData(@Param("bbsKey") int bbsKey);

    void deleteTBbsDataFile(@Param("bbsKey") int bbsKey);

    void deleteTBbsCommentFromBbsKey(@Param("bbsKey") int bbsKey);

    void deleteTBbsComment(@Param("bbsCommentKey") int bbsCommentKey);

    void deleteTLectureRoomTable(@Param("lectureDate") String lectureDate, @Param("academyNumber") int academyNumber);
}
