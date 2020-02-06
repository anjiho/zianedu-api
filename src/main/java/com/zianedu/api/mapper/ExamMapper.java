package com.zianedu.api.mapper;

import com.zianedu.api.dto.SelectboxDTO;
import com.zianedu.api.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamMapper {

    /** SELECT **/
    int selectGiChulProblemListCount(@Param("userKey") int userKey, @Param("groupCtgKey") int groupCtgKey,
                                @Param("classCtgKey") int classCtgKey, @Param("subjectCtgKey") int subjectCtgKey,
                                @Param("searchType") String searchType, @Param("searchText") String searchText);

    List<TExamUserVO> selectGiChulProblemList(@Param("userKey") int userKey, @Param("groupCtgKey") int groupCtgKey,
                                              @Param("classCtgKey") int classCtgKey, @Param("subjectCtgKey") int subjectCtgKey,
                                              @Param("searchType") String searchType, @Param("searchText") String searchText,
                                              @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    List<TExamUserVO> selectGiChulProblemListByComplete(@Param("userKey") int userKey, @Param("groupCtgKey") int groupCtgKey,
                                              @Param("classCtgKey") int classCtgKey, @Param("subjectCtgKey") int subjectCtgKey);


    List<TExamUserVO> selectDiagnosisEvaluationExamList(@Param("userKey") int userKey);

    List<TExamUserVO> selectDiagnosisEvaluationCompleteList(@Param("userKey") int userKey);

    int selectWeekBigExamListCount(@Param("userKey") int userKey, @Param("ctgKey") int ctgKey,
                                   @Param("searchType") String searchType, @Param("searchText") String searchText);

    List<TExamUserVO> selectWeekBigExamList(@Param("userKey") int userKey, @Param("ctgKey") int ctgKey,
                                            @Param("searchType") String searchType, @Param("searchText") String searchText,
                                            @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    List<TExamUserVO> selectWeekBigExamAchievementManagementList(@Param("userKey") int userKey);

    TExamUserVO selectExamResultHeaderInfo(@Param("examUserKey") int examUserKey);

    List<String> selectExamSubjectNameList(@Param("examUserKey") int examUserKey);

    List<ExamSubjectStaticsVO> selectExamSubjectStaticsList(@Param("examUserKey") int examUserKey);

    Integer selectExamSubjectGrade(@Param("examQuesBankSubjectKey") int examQuesBankSubjectKey, @Param("examKey") int examKey, @Param("userKey") int userKey);

    int selectSubjectStaticsSum(@Param("examKey") int examKey, @Param("examQuesBankSubjectKey") int examQuesBankSubjectKey);

    double selectSubjectTopPercentScore(@Param("examUserKey") int examUserKey, @Param("examKey") int examKey,
                                     @Param("examQuesBankSubjectKey") int examQuesBankSubjectKey, @Param("percentType") int percentType);

    List<ScoreRateGraphVO> selectScoreRateByStepCtgKey(@Param("examQuesBankSubjectKey") int examQuesBankSubjectKey, @Param("userKey") int userKey);

    List<ScoreRateGraphVO> selectScoreRateByPatternCtgKey(@Param("examQuesBankSubjectKey") int examQuesBankSubjectKey, @Param("userKey") int userKey);

    List<ScoreRateGraphVO> selectScoreRateByUnitCtgKey(@Param("examQuesBankSubjectKey") int examQuesBankSubjectKey, @Param("userKey") int userKey);

    List<ExamStaticsDetailSubjectVO> selectExamStaticsDetailInfoBySubject(@Param("examQuesBankSubjectKey") int examQuesBankSubjectKey, @Param("userKey") int userKey);

    List<ProblemNumberScoreVO> selectProblemNumberScoreList(@Param("examQuesBankSubjectKey") int examQuesBankSubjectKey, @Param("examQuesBankKey") int examQuesBankKey);

    List<ExamWrongAnswerVO> selectWrongAnswerList(@Param("examQuesBankSubjectKey") int examQuesBankSubjectKey, @Param("userKey") int userKey,
                                                  @Param("isScore") int isScore, @Param("isInterest") int isInterest);

    TExamMasterVO selectExamMasterInfo(@Param("examKey") int examKey);

    TExamMasterVO selectExamMasterInfoByExamUserKey(@Param("examUserKey") int examUserKey);

    List<TBankSubjectExamLinkVO> selectExamMasterSubjectList(@Param("examKey") int examKey);

    TExamUserVO selectTExamUserInfo(@Param("examKey") int examKey, @Param("userKey") int userKey);

    List<ExamListVO> selectExamList(@Param("examQuesBankSubjectKey") int examQuesBankSubjectKey);

    List<TExamSubjectUserVO> selectTExamSubjectUserList(@Param("examUserKey") int examUserKey, @Param("userKey") int userKey);

    TExamMasterVO selectTExamDateInfo(@Param("examKey") int examKey);

    List<TBankSubjectExamLinkVO> selectTBankSubjectExamLinkList(@Param("examKey") int examKey);

    List<SelectboxDTO> selectMockExamClassCtgSelectBoxList(@Param("onOffKey") int onOffKey);

    List<SelectboxDTO> selectMockExamBuyClassCtgSelectBoxList(@Param("userKey") int userKey, @Param("onOffKey") int onOffKey);

    List<SelectboxDTO> selectWeekMockExamClassCtgSelectBoxList();

    int selectMockExamListAtBuyCount(@Param("userKey") int userKey, @Param("onOffKey") int onOffKey, @Param("ctgKey") int ctgKey,
                                                    @Param("searchType") String searchType, @Param("searchText") String searchText);

    List<MockExamProductVO> selectMockExamListAtBuy(@Param("userKey") int userKey, @Param("onOffKey") int onOffKey, @Param("ctgKey") int ctgKey,
                                                    @Param("searchType") String searchType, @Param("searchText") String searchText,
                                                    @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    String selectTExamUserSerial();

    List<TExamUserVO> selectUserMockExamResultListAtBuy(@Param("userKey") int userKey, @Param("onOffKey") int onOffKey, @Param("ctgKey") int ctgKey,
                                                  @Param("searchType") String searchType, @Param("searchText") String searchText,
                                                  @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    int selectUserMockExamResultCountAtBuy(@Param("userKey") int userKey, @Param("onOffKey") int onOffKey, @Param("ctgKey") int ctgKey,
                                           @Param("searchType") String searchType, @Param("searchText") String searchText);

    List<TExamUserVO> selectUserFreeExamResult(@Param("userKey") int userKey, @Param("isGichul") int isGichul, @Param("classCtgKey") int classCtgKey,
                                               @Param("groupCtgKey") int groupCtgKey, @Param("subjectCtgKey") int subjectCtgKey,
                                               @Param("searchType") String searchType, @Param("searchText") String searchText,
                                               @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    int selectUserFreeExamResultCount(@Param("userKey") int userKey, @Param("isGichul") int isGichul, @Param("classCtgKey") int classCtgKey,
                                      @Param("groupCtgKey") int groupCtgKey, @Param("subjectCtgKey") int subjectCtgKey,
                                      @Param("searchType") String searchType, @Param("searchText") String searchText);

    List<ExamLogVO> selectUserExamApplyLogList(@Param("userKey") int userKey, @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    int selectUserExamApplyLogListCount(@Param("userKey") int userKey);


    /** INSERT **/
    void insertTExamUser(TExamUserVO tExamUserVO);

    void insertTExamSubjectUser(TExamSubjectUserVO tExamSubjectUserVO);

    /** UPDATE **/
    void updateTExamUser(TExamUserVO tExamUserVO);
}
