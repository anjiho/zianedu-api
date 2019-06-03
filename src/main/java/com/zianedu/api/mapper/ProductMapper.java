package com.zianedu.api.mapper;

import com.zianedu.api.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {

    /** SELECT **/
    List<AcademySignUpVO> selectAcademySignUp(@Param("userKey") int userKey);

    List<OnlineSignUpVO> selectPromotionOnlineSignUp(@Param("userKey") int userKey, @Param("deviceType") String deviceType);

    Integer selectOnlineLectureProgressRate(@Param("jLecKey") int jLecKey);

    List<OnlineSignUpVO> selectVideoOnlineSignUp(@Param("userKey") int userKey, @Param("deviceType") String deviceType);

    OnlineLectureDetailVO selectOnlineLectureDetailInfo(@Param("jLecKey") int jLecKey);

    List<OnlineLectureSubjectListVO> selectOnlineLectureSubjectList(@Param("jLecKey") int jLecKey);

    List<TGoodsReviewVO> selectGoodsReviewList(@Param("jLecKey") int jLecKey, @Param("startNumber") int startNumber,
                                               @Param("listLimitNumber") int listLimitNumber);

    Integer selectGoodsReviewListCount(@Param("jLecKey") int jLecKey);

    VideoPausePopupVO selectVideoPauseRequestPopup(@Param("jLecKey") int jLecKey);

    Integer selectTOrderLecPauseCnt(@Param("jLecKey") int jLecKey);

    List<OnlineVideoPauseVO> selectVideoOnlinePauseList(@Param("userKey") int userKey);

    List<OnlineVideoEndVO> selectVideoOnlineEndList(@Param("userKey") int userKey);

    Integer selectVideoSubjectCount(@Param("gKey") int gKey);

    CalcPriceVO selectTopCalcPrice(@Param("gKey") int gKey);

    List<TGoodsPriceOptionVO> selectGoodsPriceOptionList(@Param("gKey") int gKey);

    List<LectureBookVO> selectTeacherBookListFromVideoLectureLink(@Param("gKey") int gKey);

    /** INSERT **/
    Integer insertTOrderLecStartStopLog(TOrderLecStartStopLogVO tOrderLecStartStopLogVO);

    /** UPDATE **/
    void updateTOrderLecPauseCnt(@Param("jLecKey") int jLecKey, @Param("pauseDay") int pauseDay);
}
