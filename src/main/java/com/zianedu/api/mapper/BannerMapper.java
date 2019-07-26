package com.zianedu.api.mapper;

import com.zianedu.api.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerMapper {

    /** SELECT **/
    List<TCategoryVO> selectMainPageCtgKeyList(@Param("ctgKey") int ctgKey);

    BannerVO selectBannerInfoBySingle(@Param("ctgKey") int ctgKey);

    List<BannerVO> selectBannerList(@Param("ctgKey") int ctgKey, @Param("listNumber") int listNumber);

    List<PopupVO> selectPopupList(@Param("ctgKey") int ctgKey);

    List<TeacherBannerVO> selectTeacherBannerList(@Param("ctgKey") int ctgKey, @Param("subjectType") int subjectType);

    List<PopulateLectureVO> selectPopulateAcademyLectureList(@Param("ctgKey") int ctgKey);

    TeacherBannerVO selectTeacherNameAndSubjectName(@Param("teacherKey") int teacherKey);

    List<PopulateLectureVO> selectPopulateVideoLectureList(@Param("ctgKey") int ctgKey);

    String selectCtgNameAtLectureBannerList(@Param("gKey") int gKey);

    List<PopulateLectureVO> selectPackageLectureList(@Param("ctgKey") int ctgKey);

    List<NoticeVO> selectBannerNoticeList(@Param("bbsMasterKey") int bbsMasterKey);

    List<BannerBookVO> selectBannerBookList();

    List<TSearchKeywordVO> selectSearchKeywordList(@Param("className") String className);

    /** INSERT **/

    /** UPDATE **/
}
