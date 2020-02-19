package com.zianedu.api.mapper;

import com.zianedu.api.dto.SubjectDTO;
import com.zianedu.api.dto.TypeDTO;
import com.zianedu.api.dto.ZianPassProductDTO;
import com.zianedu.api.vo.*;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.reflect.Type;
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

    List<TGoodsPriceOptionVO> selectGoodsPriceOptionList2(@Param("gKey") int gKey);

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

    List<LectureApplyAcademyListVO> selectAcademyLectureListFromCategoryMenuAtLectureApply(@Param("menuCtgKey") int menuCtgKey, @Param("stepCtgKeys") List<String> stepCtgKeys,
                                                                                           @Param("teacherKeys") List<String> teacherKeys);

    List<TeacherHomeLectureVO> selectVideoLectureListFromCategoryMenu(@Param("ctgKey") int ctgKey, @Param("stepCtgKey") int stepCtgKey,
                                                                    @Param("teacherKey") int teacherKey);

    List<TeacherHomeLectureVO> selectVideoLectureListFromCategoryMenuFromApplyLecture(@Param("ctgKey") int ctgKey, @Param("stepCtgKeys") List<String> stepCtgKeys,
                                                                                      @Param("teacherKey") int teacherKey);

    List<TeacherHomeLectureVO> selectVideoLectureListFromCategoryMenuFromApplyLecture2(@Param("ctgKey") int ctgKey, @Param("stepCtgKeys") List<String> stepCtgKeys,
                                                                                      @Param("teacherKey") int teacherKey);

    List<SpecialPackageVO> selectPromotionPackageList(@Param("menuCtgKey") int menuCtgKey, @Param("stepCtgKeys") List<String> stepCtgKeys,
                                                      @Param("teacherKeys") List<String> teacherKeys, @Param("subjectCtgKeys") List<String> subjectCtgKeys);

    SpecialPackageProductVO selectPromotionPackageDetailInfo(@Param("gKey") int gKey);

    List<SpecialPackageLectureVO> selectSpecialPackageIncludeProductList(@Param("gKey") int gKey);

    List<TeacherHomeLectureVO> selectPromotionPackageTeacherList(@Param("gKey") int gKey);

    int selectMockExamProductListCount(@Param("onOffKey") int onOffKey, @Param("ctgKey") int ctgKey,
                                                           @Param("searchType") String searchType, @Param("searchText") String searchText);

    List<MockExamProductVO> selectMockExamProductList(@Param("onOffKey") int onOffKey, @Param("ctgKey") int ctgKey, @Param("searchType") String searchType,
                                                      @Param("searchText") String searchText, @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    //List<MockExamProductVO> selectMockExamProductList(@Param("ctgKey") int ctgKey);

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

    Integer selectFreeLectureListCountFromCategoryMenu(@Param("ctgKey") int ctgKey, @Param("stepCtgKey") int stepCtgKey,
                                                       @Param("freeLectureType") String freeLectureType);

    List<FreeLectureVO> selectFreeLectureListFromCategoryMenu(@Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber,
                                                              @Param("ctgKey") int ctgKey, @Param("stepCtgKey") int stepCtgKey,
                                                              @Param("freeLectureType") String freeLectureType);

    FreeLectureVO selectFreeLectureListFromLecKey(@Param("lecKey") int lecKey);

    List<TypeDTO> selectFreeLectureStepList(@Param("ctgKey") int ctgKey, @Param("freeLectureType") String freeLectureType);

    List<TLecCurriVO> selectTLecCurriList(@Param("lecKey") int lecKey, @Param("device") String device);

    List<TypeDTO> selectZianPassProductAffiliationList(@Param("parentKey") int parentKey, @Param("pmType") int pmType);

    List<ZianPassProductDTO> selectZianPassProductList(@Param("parentKey") int parentKey, @Param("pmType") int pmType);

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

    TLecCurriVO selectVideoLectureRemainTimeByJLecKeyAndCurriKey(@Param("jLecKey") int jLecKey, @Param("curriKey") int curriKey);

    List<TypeDTO> selectSignUpAcademyTypeList(@Param("userKey") int userKey);

    List<SignUpLectureVO> selectSignUpAcademySubjectNameList(@Param("userKey") int userKey, @Param("stepCtgKey") int stepCtgKey);

    List<TypeDTO> selectSignUpVideoLecturePauseTypeList(@Param("userKey") int userKey);

    List<SignUpLectureVO> selectSignUpVideoLecturePauseSubjectList(@Param("userKey") int userKey, @Param("stepCtgKey") int stepCtgKey);

    List<TypeDTO> selectSignUpVideoLectureEndTypeList(@Param("userKey") int userKey, @Param("deviceType") String deviceType);

    List<SignUpLectureVO> selectSignUpVideoLectureEndSubjectList(@Param("userKey") int userKey, @Param("stepCtgKey") int stepCtgKey, @Param("deviceType") String deviceType);

    OnlineVideoEndVO selectVideoOnlineEndListByJLecKey(@Param("jLecKey") int jLecKey);
    //수강신청 과목 리스트
    List<LectureApplySubjectVO> selectLectureApplySubjectList(@Param("menuCtgKey") int menuCtgKey, @Param("productType") int productType);
    //수강신청 교수 리스트
    List<LectureApplyTeacherVO> selectLectureApplyTeacherList(@Param("menuCtgKey") int menuCtgKey, @Param("productType") int productType);

    List<LectureApplyTeacherTypeVO> selectLectureApplyTeacherTypeList(@Param("menuCtgKey") int menuCtgKey, @Param("subjectMenuKey") int subjectMenuKey,
                                                                      @Param("teacherKeys") List<String>teacherKeys, @Param("stepCtgKeys") List<String>stepCtgKeys);

    List<LectureApplyTeacherTypeVO> selectLectureApplyTeacherTypeList2(@Param("menuCtgKey") int menuCtgKey, @Param("subjectMenuKey") int subjectMenuKey,
                                                                      @Param("teacherKeys") List<String>teacherKeys, @Param("stepCtgKeys") List<String>stepCtgKeys);

    List<TCategoryVO> selectLectureApplySubjectListBySubjectKey(@Param("menuCtgKey") int menuCtgKey, @Param("goodsType") int goodsType);

    List<TCategoryVO> selectLectureApplySubjectListFromSearch(@Param("menuCtgKey") int menuCtgKey, @Param("goodsType") int goodsType, @Param("subjectMenuKeys") List<String>subjectMenuKeys);

    List<TCategoryVO> selectSpecialPackageSubjectListFromSearch(@Param("menuCtgKey") int menuCtgKey, @Param("subjectMenuKeys") List<String>subjectMenuKeys);

    List<LectureApplyTeacherVO> selectSpecialPackageTeacherList(@Param("menuCtgKey") int menuCtgKey);

    int selectPackagePriceKey(@Param("gKey") int gKey);

    VideoLectureDetailVO selectGoodsInfoByRetake(@Param("gKey") int gKey, @Param("priceKey") int priceKey);

    List<ZianPassEndListVO> selectZianPassEndList(@Param("userKey") int userKey);

    VideoLectureDetailVO selectGoodsInfoByJLecKey(@Param("jLecKey") int jLecKey);

    TLinkKeyVO selectExamOnOffKey(@Param("gKey") int gKey);

    TLinkKeyVO selectExamOnOffKeyByExamKey(@Param("examKey") int examKey);

    double selectVideoGoodsMultiple(@Param("jLecKey") int jLecKey);

    int selectVideoGoodsJGKey(@Param("jLecKey") int jLecKey);

    TOrderLecCurriVO selectTOrderLecCurriInfo(@Param("jLecKey") int jLecKey, @Param("curriKey") int curriKey);

    String selectTOrderLecStartDt(@Param("jLecKey") int jLecKey);

    /** INSERT **/
    Integer insertTOrderLecStartStopLog(TOrderLecStartStopLogVO tOrderLecStartStopLogVO);

    void insertTOrderLecCurri(TOrderLecCurriVO tOrderLecCurriVO);

    /** UPDATE **/
    void updateTOrderLecPauseCnt(@Param("jLecKey") int jLecKey, @Param("pauseDay") int pauseDay);

    void updateTOrderLecStartDt(@Param("jLecKey") int jLecKey);

    void updateTOrderLecCurri(@Param("jLecKey") int jLecKey, @Param("curriKey") int curriKey, @Param("time") int time);

    void updateTOrderLecCurriByMobile(@Param("jLecKey") int jLecKey, @Param("curriKey") int curriKey, @Param("time") int time);

    void updateTOrderLecStartDtByUserKey(@Param("userKey") int userKey);
}
