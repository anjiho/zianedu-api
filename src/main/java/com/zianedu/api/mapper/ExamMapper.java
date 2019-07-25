package com.zianedu.api.mapper;

import com.zianedu.api.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamMapper {

    /** SELECT **/
    List<TExamUserVO> selectGiChulProblemList(@Param("userKey") int userKey, @Param("groupCtgKey") int groupCtgKey,
                                              @Param("classCtgKey") int classCtgKey, @Param("subjectCtgKey") int subjectCtgKey);

    List<TExamUserVO> selectGiChulProblemListByComplete(@Param("userKey") int userKey, @Param("groupCtgKey") int groupCtgKey,
                                              @Param("classCtgKey") int classCtgKey, @Param("subjectCtgKey") int subjectCtgKey);


    List<TExamUserVO> selectDiagnosisEvaluationExamList(@Param("userKey") int userKey);

    List<TExamUserVO> selectDiagnosisEvaluationCompleteList(@Param("userKey") int userKey);

    List<TExamUserVO> selectWeekBigExamList(@Param("userKey") int userKey);

    List<TExamUserVO> selectWeekBigExamAchievementManagementList(@Param("userKey") int userKey);

    TExamUserVO selectExamResultHeaderInfo(@Param("examUserKey") int examUserKey);

    List<String> selectExamSubjectNameList(@Param("examUserKey") int examUserKey);

    List<ExamSubjectStaticsVO> selectExamSubjectStaticsList(@Param("examUserKey") int examUserKey);

    int selectExamSubjectGrade(@Param("examQuesBankSubjectKey") int examQuesBankSubjectKey, @Param("examKey") int examKey, @Param("userKey") int userKey);

    int selectSubjectStaticsSum(@Param("examKey") int examKey, @Param("examQuesBankSubjectKey") int examQuesBankSubjectKey);

    int selectSubjectTopPercentScore(@Param("examUserKey") int examUserKey, @Param("examKey") int examKey,
                                     @Param("examQuesBankSubjectKey") int examQuesBankSubjectKey, @Param("percentType") int percentType);

    List<ScoreRateGraphVO> selectScoreRateByStepCtgKey(@Param("examQuesBankSubjectKey") int examQuesBankSubjectKey, @Param("userKey") int userKey);

    List<ScoreRateGraphVO> selectScoreRateByPatternCtgKey(@Param("examQuesBankSubjectKey") int examQuesBankSubjectKey, @Param("userKey") int userKey);

    List<ScoreRateGraphVO> selectScoreRateByUnitCtgKey(@Param("examQuesBankSubjectKey") int examQuesBankSubjectKey, @Param("userKey") int userKey);

    List<ExamStaticsDetailSubjectVO> selectExamStaticsDetailInfoBySubject(@Param("examQuesBankSubjectKey") int examQuesBankSubjectKey, @Param("userKey") int userKey);

    List<ProblemNumberScoreVO> selectProblemNumberScoreList(@Param("examQuesBankSubjectKey") int examQuesBankSubjectKey, @Param("examQuesBankKey") int examQuesBankKey);

    List<ExamWrongAnswerVO> selectWrongAnswerList(@Param("examQuesBankSubjectKey") int examQuesBankSubjectKey, @Param("userKey") int userKey,
                                                  @Param("isScore") int isScore, @Param("isInterest") int isInterest);

    TExamMasterVO selectExamMasterInfo(@Param("examKey") int examKey);

    List<TBankSubjectExamLinkVO> selectExamMasterSubjectList(@Param("examKey") int examKey);

    TExamUserVO selectTExamUserInfo(@Param("examKey") int examKey, @Param("userKey") int userKey);

    List<ExamListVO> selectExamList(@Param("examQuesBankSubjectKey") int examQuesBankSubjectKey);

    List<TExamSubjectUserVO> selectTExamSubjectUserList(@Param("examKey") int examKey, @Param("userKey") int userKey);

    /** INSERT **/
    void insertTExamUser(TExamUserVO tExamUserVO);

    void insertTExamSubjectUser(TExamSubjectUserVO tExamSubjectUserVO);

    /** UPDATE **/
    void updateTExamUser(TExamUserVO tExamUserVO);
}
