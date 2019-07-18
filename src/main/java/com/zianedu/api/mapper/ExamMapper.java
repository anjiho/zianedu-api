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

    /** INSERT **/

    /** UPDATE **/
}
