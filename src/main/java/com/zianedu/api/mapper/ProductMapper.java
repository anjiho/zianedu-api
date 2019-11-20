package com.zianedu.api.mapper;

import com.zianedu.api.dto.SubjectDTO;
import com.zianedu.api.dto.TypeDTO;
import com.zianedu.api.dto.ZianPassProductDTO;
import com.zianedu.api.vo.*;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ProductMapper {

    /** SELECT **/
    List<AcademySignUpVO> selectAcademySignUp(@Param("userKey") int userKey);

    List<OnlineSignUpVO> selectPromotionOnlineSignUp(@Param("userKey") int userKey, @Param("deviceType") String deviceType);

    Integer selectOnlineLectureProgressRate(@Param("jLecKey") int jLecKey);

    List<OnlineSignUpVO> selectVideoOnlineSignUp(@Param("userKey") int userKey, @Param("deviceType") String deviceType,
                                                 @Param("subjectCtgKey") int subjectCtgKey, @Param("stepCtgKey") int stepCtgKey);

    OnlineSubjectListVO selectLectureDetailInfoByJLecKey(@Param("jLecKey") int jLecKey);

    OnlineLectureDetailVO selectOnlineLectureDetailInfo(@Param("jLecKey") int jLecKey);

    List<OnlineLectureSubjectListVO> selectOnlineLectureSubjectList(@Param("jLecKey") int jLecKey);

    List<TGoodsReviewVO> selectGoodsReviewList(@Param("jLecKey") int jLecKey, @Param("startNumber") int startNumber,
                                               @Param("listLimitNumber") int listLimitNumber);

    Integer selectGoodsReviewListCount(@Param("jLecKey") int jLecKey);

    VideoPausePopupVO selectVideoPauseRequestPopup(@Param("jLecKey") int jLecKey);

    Integer selectTOrderLecPauseCnt(@Param("jLecKey") int jLecKey);

    List<OnlineVideoPauseVO> selectVideoOnlinePauseList(@Param("userKey") int userKey);

    List<OnlineVideoPauseVO> selectVideoOnlinePauseListByJLecKey(@Param("jLecKey") int jLecKey);

    List<OnlineVideoEndVO> selectVideoOnlineEndList(@Param("userKey") int userKey);

    Integer selectVideoSubjectCount(@Param("gKey") int gKey);

    CalcPriceVO selectTopCalcPrice(@Param("gKey") int gKey);

    List<TGoodsPriceOptionVO> selectGoodsPriceOptionList(@Param("gKey") int gKey);

    List<LectureBookVO> selectTeacherBookListFromVideoLectureLink(@Param("gKey") int gKey);

    List<LectureBookVO> selectTeacherAcademyLectureBookList(@Param("gKey") int gKey);

    VideoLectureDetailVO selectOnlineVideoLectureDetailInfo(@Param("gKey") int gKey);

    List<TGoodsPriceOptionVO> selectGoodsPriceOption(@Param("gKey") int gKey);

    List<TLecCurriVO> selectLectureListFromVideoProduct(@Param("gKey") int gKey, @Param("device") int device);

    int selectLectureListFromVideoProductCount(@Param("gKey") int gKey);

    AcademyLectureDetailVO selectAcademyLectureDetailInfo(@Param("gKey") int gKey);

    List<TeacherInfoVO> selectAcademyLectureTeacherList(@Param("gKey") int gKey);

    List<AcademyLectureListVO> selectAcademyLectureListFromCategoryMenu(@Param("ctgKey") int ctgKey, @Param("stepCtgKey") int stepCtgKey,
                                                                        @Param("teacherKey") int teacherKey);

    List<TeacherHomeLectureVO> selectVideoLectureListFromCategoryMenu(@Param("ctgKey") int ctgKey, @Param("stepCtgKey") int stepCtgKey,
                                                                    @Param("teacherKey") int teacherKey);

    List<SpecialPackageVO> selectPromotionPackageList();

    SpecialPackageProductVO selectPromotionPackageDetailInfo(@Param("gKey") int gKey);

    List<TeacherHomeLectureVO> selectPromotionPackageTeacherList(@Param("gKey") int gKey);

    List<MockExamProductVO> selectMockExamProductList(@Param("ctgKey") int ctgKey);

    TGoodsPriceOptionVO selectGoodsPriceOptionByPriceKey(@Param("priceKey") int priceKey);

    List<TGoodsPriceOptionVO> selectGoodsPriceOptionByGKey(@Param("gKey") int gKey);

    TGoodsPriceOptionVO selectGoodsPriceOptionByGKeySingle(@Param("gKey") int gKey);

    Integer selectGoodsType(@Param("gKey") int gKey);

    List<String> selectTeacherNameListByVideoProduct(@Param("gKey") int gKey);

    TLecVO selectTLecInfo(@Param("gKey") int gKey);

    TGoodsVO selectTGoodsInfo(@Param("gKey") int gKey);

    TPromotionVO selectTPromotionInfoByGKey(@Param("gKey") int gKey);

    List<TCartLinkGoodsVO> selectGoodsPriceOptionListBySpecialPackage(@Param("kind") int kind, @Param("promotionGKey") int promotionGKey);

    Integer selectZianPassPageKeyFromGKey(@Param("gKey") int gKey);

    List<Integer> selectGoodsPriceOptionListByZianPass(@Param("gKey") int gKey);

    TCategoryGoodsVO selectTCategoryGoods(@Param("gKey") int gKey);

    Integer selectTGoodsPriceOptionCount(@Param("gKey") int gKey);

    List<TeacherHomeLectureVO> selectFreeLectureListFromCategoryMenu(@Param("ctgKey") int ctgKey);

    List<TLecCurriVO> selectTLecCurriList(@Param("lecKey") int lecKey, @Param("device") String device);

    List<ZianPassProductDTO> selectZianPassProductList(@Param("parentKey") int parentKey);

    List<ZianPassProductDTO> selectZianPassSubjectSingleProductList(@Param("parentKey") int parentKey);

    List<ZianPassSubMenuVO> selectZianPassSubMenuList(@Param("parentKey") int parentKey);

    List<ZianPassSubMenuVO> selectZianPassSingleSubjectMenuList(@Param("parentKey") int parentKey);

    List<SubjectDTO> selectVideoOnlineSignUpSubjectList(@Param("userKey") int userKey, @Param("deviceType") String deviceType);

    List<TypeDTO> selectVideoOnlineSignUpTypeList(@Param("userKey") int userKey, @Param("deviceType") String deviceType);

    List<SignUpLectureVO> selectSignUpLectureList(@Param("userKey") int userKey, @Param("deviceType") String deviceType,
                                                  @Param("subjectCtgKey") int subjectCtgKey, @Param("stepCtgKey") int stepCtgKey);

    List<ZianPassSignUpVO> selectSignUpZianPassList(@Param("userKey") int userKey);

    List<TypeDTO> selectSignUpZianPassTypeList(@Param("jKey") int jKey, @Param("deviceType") String deviceType);

    List<SignUpLectureVO> selectZianPassSubjectNameList(@Param("jKey") int jKey, @Param("stepCtgKey") int stepCtgKey,
                                         @Param("deviceType") String deviceType);

    List<TLecCurriVO> selectVideoLectureListByJLecKey(@Param("jLecKey") int jLecKey, @Param("deviceType") String deviceType);

    List<TypeDTO> selectSignUpAcademyTypeList(@Param("userKey") int userKey);

    List<SignUpLectureVO> selectSignUpAcademySubjectNameList(@Param("userKey") int userKey, @Param("stepCtgKey") int stepCtgKey);

    List<TypeDTO> selectSignUpVideoLecturePauseTypeList(@Param("userKey") int userKey);

    List<SignUpLectureVO> selectSignUpVideoLecturePauseSubjectList(@Param("userKey") int userKey, @Param("stepCtgKey") int stepCtgKey);

    /** INSERT **/
    Integer insertTOrderLecStartStopLog(TOrderLecStartStopLogVO tOrderLecStartStopLogVO);

    /** UPDATE **/
    void updateTOrderLecPauseCnt(@Param("jLecKey") int jLecKey, @Param("pauseDay") int pauseDay);
}
