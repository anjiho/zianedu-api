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
                                                     @Param("searchType") String searchType, @Param("searchText") String searchText);

    int selectTBbsDataListBySearchCount(@Param("bbsMasterKey") int bbsMasterKey, @Param("teacherKey") int teacherKey,
                                        @Param("searchType") String searchType, @Param("searchText") String searchText);

    List<GoodsReviewVO> selectGoodsReviewList(@Param("teacherKey") int teacherKey, @Param("listType") String listType,
                                           @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);


    List<GoodsReviewVO> selectTeacherReviewList(@Param("teacherKey") int teacherKey, @Param("gKey") int gKey,
                                                @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    int selectTeacherReviewListCount(@Param("teacherKey") int teacherKey, @Param("gKey") int gKey);

    ReferenceRoomDetailVO selectTeacherReferenceRoomDetailInfo(@Param("bbsKey") int bbsKey);

    List<PrevNextVO> selectTeacherReferenceRoomPrevNext(@Param("bbsMasterKey") int bbsMasterKey, @Param("teacherKey") int teacherKey, @Param("bbsKey") int bbsKey);

    /** INSERT **/
    void insertTGoodsReview(TGoodsReviewVO tGoodsReviewVO);
}
