package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.datasource.ZianCoreCode;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.AchievementManagementDTO;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.dto.ScoreRateDTO;
import com.zianedu.api.mapper.ExamMapper;
import com.zianedu.api.utils.*;
import com.zianedu.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class ExamService {

    @Autowired
    private ExamMapper examMapper;

    @Transactional(readOnly = true)
    public ApiResultListDTO getGichulProblemList(int userKey, int groupCtgKey, int classCtgKey, int subjectCtgKey) {
        int resultCode = OK.value();

        List<TExamUserVO> gichulProblemList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            gichulProblemList = examMapper.selectGiChulProblemList(userKey, groupCtgKey, classCtgKey, subjectCtgKey);
            if (gichulProblemList.size() > 0) {
                for (TExamUserVO vo : gichulProblemList) {
                    vo.setPrintQuestionFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getPrintQuestionFile()));
                    vo.setPrintCommentaryFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getPrintCommentaryFile()));
                }
            }
        }
        return new ApiResultListDTO(gichulProblemList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getAchievementManagementList(int userKey, int groupCtgKey, int classCtgKey, int subjectCtgKey) {
        int resultCode = OK.value();

        List<TExamUserVO> gichulProblemList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            gichulProblemList = examMapper.selectGiChulProblemListByComplete(userKey, groupCtgKey, classCtgKey, subjectCtgKey);
            if (gichulProblemList.size() > 0) {
                for (TExamUserVO vo : gichulProblemList) {
                    vo.setPrintQuestionFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getPrintQuestionFile()));
                    vo.setPrintCommentaryFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getPrintCommentaryFile()));
                }
            }
        }
        return new ApiResultListDTO(gichulProblemList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getDiagnosisEvaluationExamList(int userKey) {
        int resultCode = OK.value();

        List<TExamUserVO> examList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            examList = examMapper.selectDiagnosisEvaluationExamList(userKey);
        }
        return new ApiResultListDTO(examList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getDiagnosisEvaluationCompleteList(int userKey) {
        int resultCode = OK.value();

        List<TExamUserVO> examList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            examList = examMapper.selectDiagnosisEvaluationCompleteList(userKey);
        }
        return new ApiResultListDTO(examList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getWeekBigExamList(int userKey) throws Exception {
        int resultCode = OK.value();

        List<TExamUserVO> examList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            examList = examMapper.selectWeekBigExamList(userKey);
            if (userKey != 5) {
                if (examList.size() > 0) {
                    for (TExamUserVO vo : examList) {
                        if (vo.getIsComplete() == 0) {
                            //시험 날짜가 오늘기준 이전이면 2로 변경('종료')
                            boolean isBetweenDate = DateUtils.isBetweenDateFromToday(vo.getAcceptStartDate(), vo.getAcceptEndDate());
                            if (!isBetweenDate) {
                                vo.setIsComplete(2);
                            }
                        }
                    }
                }
            }
        }
        return new ApiResultListDTO(examList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getWeekBigExamAchievementManagementList(int userKey) {
        int resultCode = OK.value();

        List<TExamUserVO> examList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            examList = examMapper.selectWeekBigExamAchievementManagementList(userKey);
        }
        return new ApiResultListDTO(examList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getAchievementManagementDetailInfo(int examUserKey) {
        int resultCode = OK.value();

        AchievementTopInfoVO achievementTopInfoVO = new AchievementTopInfoVO();
        TExamUserVO examHeaderInfo = new TExamUserVO();
        List<String> subjectNameList = new ArrayList<>();
        List<ExamSubjectStaticsVO> examSubjectStaticsList = new ArrayList<>();
        ExamSubjectTotalVO examSubjectTotalVO = new ExamSubjectTotalVO();
        List<ExamCompareTotalStaticsVO> examCompareTotalStaticsList = new ArrayList<>();
        List<SubjectStaticsVO> subjectStaticsList = new ArrayList<>();
        StaticsGraphVO subjectStaticsGraphVO = new StaticsGraphVO();
        StaticsGraphVO compareScoreStaticsGraphVO = new StaticsGraphVO();
        List<ScoreRateGraphVO> scoreRateByTypeInfo = new ArrayList<>();
        List<ScoreRateGraphVO> scoreRateByPatternInfo = new ArrayList<>();
        List<ScoreRateGraphVO> scoreRateByUnitInfo = new ArrayList<>();
        List<ScoreRateDTO>scoreRateInfo = new ArrayList<>();

        int totalStaticsScore = 0;
        int topTenSumScore = 0;
        int topThirtySumScore = 0;
        int totalMySumScore = 0;
        int totalSumScore = 0;

        List<String> subjectStaticsGraphCategoryNameList = new ArrayList<>();
        List<Integer> subjectStaticsGraphTopTenDataList = new ArrayList<>();
        List<Integer> subjectStaticsGraphTopThirtyDataList = new ArrayList<>();
        List<Double> subjectStaticsGraphMyDataList = new ArrayList<>();

        if (examUserKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            examHeaderInfo = examMapper.selectExamResultHeaderInfo(examUserKey);
            subjectNameList = examMapper.selectExamSubjectNameList(examUserKey);
            String subjectName = "";

            if (subjectNameList.size() > 0) {
                 subjectName = StringUtils.implodeList(",", subjectNameList);
            }
            achievementTopInfoVO.setSerial(examHeaderInfo.getSerial());
            achievementTopInfoVO.setSubjectName(subjectName);

            examSubjectStaticsList = examMapper.selectExamSubjectStaticsList(examUserKey);
            if (examSubjectStaticsList.size() > 0) {
                int staticsAnswerCnt = 0;
                int staticsAnswerScore = 0;
                int staticsUserGrade = 0;
                int staticsTotalAnswerCnt = 0;

                for (ExamSubjectStaticsVO vo : examSubjectStaticsList) {
                    ScoreRateDTO scoreRateDTO = new ScoreRateDTO();
                    ScoreRateGraphVO scoreRateByTypeVo = new ScoreRateGraphVO();
                    ScoreRateGraphVO scoreRateByPatternVo = new ScoreRateGraphVO();
                    ScoreRateGraphVO scoreRateByUnitVo = new ScoreRateGraphVO();

                    vo.setAnswerScore(vo.getAnswerCnt() * 5);   //원점수 계산
                    int userGrade = examMapper.selectExamSubjectGrade(vo.getExamQuesBankSubjectKey(), vo.getExamKey(), vo.getUserKey());
                    vo.setUserGrade(userGrade); //석차 주입
                    //상위누적%
                    vo.setTopAccumulatePercent(ZianUtils.getTopAccumulatePercent(vo.getTotalAnswerCnt(), vo.getUserGrade()));
                    if (ZianCoreCode.IS_PASS_SCORE > vo.getAnswerScore()) vo.setIsPass("과락");
                    else vo.setIsPass("합격");
                    //평균 계산
                    staticsAnswerCnt += vo.getAnswerCnt();
                    staticsAnswerScore += vo.getAnswerScore();
                    staticsUserGrade += vo.getUserGrade();
                    staticsTotalAnswerCnt += vo.getTotalAnswerCnt();
                    //전체평균과 본인성적 비교 그래프값
                    int subjectStaticsSum = examMapper.selectSubjectStaticsSum(vo.getExamKey(), vo.getExamQuesBankSubjectKey());
                    ExamCompareTotalStaticsVO examCompareTotalStaticsVO = new ExamCompareTotalStaticsVO(vo.getSubjectName(), vo.getAnswerScore(), subjectStaticsSum, vo.getTotalAnswerCnt());
                    examCompareTotalStaticsList.add(examCompareTotalStaticsVO);

                    totalStaticsScore += examCompareTotalStaticsVO.getTotalSubjectScore();

                    //과목별 평균
                    int tenPercentScore = examMapper.selectSubjectTopPercentScore(examUserKey, vo.getExamKey(), vo.getExamQuesBankSubjectKey(), 10);
                    int thirtyPercentScore = examMapper.selectSubjectTopPercentScore(examUserKey, vo.getExamKey(), vo.getExamQuesBankSubjectKey(), 30);
                    SubjectStaticsVO subjectStaticsVO = new SubjectStaticsVO(
                        vo.getSubjectName(), tenPercentScore, thirtyPercentScore, vo.getAnswerScore(), examCompareTotalStaticsVO.getTotalSubjectScore()
                    );
                    //과목별 평군의 '전체'합
                    topTenSumScore += tenPercentScore;
                    topThirtySumScore += thirtyPercentScore;
                    totalMySumScore += vo.getAnswerScore();
                    totalSumScore += examCompareTotalStaticsVO.getTotalSubjectScore();

                    subjectStaticsList.add(subjectStaticsVO);

                    //
                    subjectStaticsGraphCategoryNameList.add(vo.getSubjectName());
                    subjectStaticsGraphTopTenDataList.add(tenPercentScore);
                    subjectStaticsGraphTopThirtyDataList.add(thirtyPercentScore);
                    subjectStaticsGraphMyDataList.add(examCompareTotalStaticsVO.getTotalSubjectScore());

                    //유형별 정답률
                    List<ScoreRateGraphVO> scoreRateByTypeList = examMapper.selectScoreRateByStepCtgKey(vo.getExamQuesBankSubjectKey(), vo.getUserKey());
                    if (scoreRateByTypeList.size() > 0) {
                        List<String> scoreRateByTypeNameList = new ArrayList<>();
                        List<Integer> scoreRateByTypeProblemList = new ArrayList<>();
                        List<Integer> scoreRateByTypeScoreList = new ArrayList<>();
                        for (ScoreRateGraphVO scoreRateGraphVO : scoreRateByTypeList) {
                            scoreRateByTypeNameList.add(scoreRateGraphVO.getCtgName());
                            scoreRateByTypeProblemList.add(scoreRateGraphVO.getProblemCnt());
                            scoreRateByTypeScoreList.add(scoreRateGraphVO.getScoreCnt());
                        }

                        String[] scoreRateByTypeNames = StringUtils.arrayListToStringArray(scoreRateByTypeNameList);
                        Integer[] scoreRateByTypeProblems = StringUtils.arrayIntegerListToStringArray(scoreRateByTypeProblemList);
                        Integer[] scoreRateByTypeScores = StringUtils.arrayIntegerListToStringArray(scoreRateByTypeScoreList);
                        ScoreRateGraphVO scoreRateGraphVO = new ScoreRateGraphVO(vo.getSubjectName(), scoreRateByTypeNames, scoreRateByTypeProblems, scoreRateByTypeScores);
                        scoreRateByTypeInfo.add(scoreRateGraphVO);

                        scoreRateByTypeVo = new ScoreRateGraphVO(vo.getSubjectName(), scoreRateByTypeNames, scoreRateByTypeProblems, scoreRateByTypeScores);

                    }
                    //패턴별 정답률
                    List<ScoreRateGraphVO> scoreRateByPatternList = examMapper.selectScoreRateByPatternCtgKey(vo.getExamQuesBankSubjectKey(), vo.getUserKey());
                    if (scoreRateByPatternList.size() > 0) {
                        List<String> scoreRateByTypeNameList = new ArrayList<>();
                        List<Integer> scoreRateByTypeProblemList = new ArrayList<>();
                        List<Integer> scoreRateByTypeScoreList = new ArrayList<>();
                        for (ScoreRateGraphVO scoreRateGraphVO : scoreRateByPatternList) {
                            scoreRateByTypeNameList.add(scoreRateGraphVO.getCtgName());
                            scoreRateByTypeProblemList.add(scoreRateGraphVO.getProblemCnt());
                            scoreRateByTypeScoreList.add(scoreRateGraphVO.getScoreCnt());
                        }

                        String[] scoreRateByTypeNames = StringUtils.arrayListToStringArray(scoreRateByTypeNameList);
                        Integer[] scoreRateByTypeProblems = StringUtils.arrayIntegerListToStringArray(scoreRateByTypeProblemList);
                        Integer[] scoreRateByTypeScores = StringUtils.arrayIntegerListToStringArray(scoreRateByTypeScoreList);
                        ScoreRateGraphVO scoreRateGraphVO = new ScoreRateGraphVO(vo.getSubjectName(), scoreRateByTypeNames, scoreRateByTypeProblems, scoreRateByTypeScores);
                        scoreRateByPatternInfo.add(scoreRateGraphVO);

                        scoreRateByPatternVo = new ScoreRateGraphVO(vo.getSubjectName(), scoreRateByTypeNames, scoreRateByTypeProblems, scoreRateByTypeScores);
                    }
                    //대단원별 정답률
                    List<ScoreRateGraphVO> scoreRateByUnitList = examMapper.selectScoreRateByUnitCtgKey(vo.getExamQuesBankSubjectKey(), vo.getUserKey());
                    if (scoreRateByUnitList.size() > 0) {
                        List<String> scoreRateByTypeNameList = new ArrayList<>();
                        List<Integer> scoreRateByTypeProblemList = new ArrayList<>();
                        List<Integer> scoreRateByTypeScoreList = new ArrayList<>();
                        for (ScoreRateGraphVO scoreRateGraphVO : scoreRateByUnitList) {
                            scoreRateByTypeNameList.add(scoreRateGraphVO.getCtgName());
                            scoreRateByTypeProblemList.add(scoreRateGraphVO.getProblemCnt());
                            scoreRateByTypeScoreList.add(scoreRateGraphVO.getScoreCnt());
                        }

                        String[] scoreRateByTypeNames = StringUtils.arrayListToStringArray(scoreRateByTypeNameList);
                        Integer[] scoreRateByTypeProblems = StringUtils.arrayIntegerListToStringArray(scoreRateByTypeProblemList);
                        Integer[] scoreRateByTypeScores = StringUtils.arrayIntegerListToStringArray(scoreRateByTypeScoreList);
                        ScoreRateGraphVO scoreRateGraphVO = new ScoreRateGraphVO(vo.getSubjectName(), scoreRateByTypeNames, scoreRateByTypeProblems, scoreRateByTypeScores);
                        scoreRateByUnitInfo.add(scoreRateGraphVO);

                        scoreRateByUnitVo = new ScoreRateGraphVO(vo.getSubjectName(), scoreRateByTypeNames, scoreRateByTypeProblems, scoreRateByTypeScores);
                    }
                    //과목별 유형별, 패턴별, 대단원별 주입
                    scoreRateDTO = new ScoreRateDTO(scoreRateByTypeVo, scoreRateByPatternVo, scoreRateByUnitVo);
                    scoreRateInfo.add(scoreRateDTO);
                }
                //과목별 평군의 '전체' 주입
                SubjectStaticsVO subjectStaticsVO = new SubjectStaticsVO(
                        "총점", topTenSumScore, topThirtySumScore, totalMySumScore, totalSumScore
                );
                subjectStaticsList.add(subjectStaticsVO);
                //평균값 주입
                examSubjectTotalVO = new ExamSubjectTotalVO(
                        (staticsAnswerCnt / examSubjectStaticsList.size()),
                        (staticsAnswerScore / examSubjectStaticsList.size()),
                        (staticsUserGrade / examSubjectStaticsList.size()),
                        (staticsTotalAnswerCnt / examSubjectStaticsList.size())
                );
                //과목별 평균 그래프 (과목별 평균 옆 그래프)
                String[] subjectStaticsGraphCategoryNames = StringUtils.arrayListToStringArray(subjectStaticsGraphCategoryNameList);
                Integer[] subjectStaticsGraphTopTenData = StringUtils.arrayIntegerListToStringArray(subjectStaticsGraphTopTenDataList);
                Integer[] subjectStaticsGraphTopThirtyData = StringUtils.arrayIntegerListToStringArray(subjectStaticsGraphTopThirtyDataList);
                double[] subjectStaticsGraphMyData = StringUtils.arrayListToDoubleArray(subjectStaticsGraphMyDataList);
                subjectStaticsGraphVO = new StaticsGraphVO(
                        subjectStaticsGraphCategoryNames, subjectStaticsGraphTopTenData, subjectStaticsGraphTopThirtyData, subjectStaticsGraphMyData
                );
                //점수비교 그래프
                Integer[] scoreCompareGraphTopTenData = {topTenSumScore / examSubjectStaticsList.size()};
                Integer[] scoreCompareGraphStaticsData = {totalStaticsScore / examSubjectStaticsList.size()};
                double[] scoreCompareGraphMyData = {examSubjectTotalVO.getStaticsAnswerScore()};
                compareScoreStaticsGraphVO = new StaticsGraphVO(
                        scoreCompareGraphTopTenData, scoreCompareGraphStaticsData, scoreCompareGraphMyData
                );

            }
        }
        AchievementManagementDTO achievementManagementDTO = new AchievementManagementDTO(
                achievementTopInfoVO,
                examSubjectStaticsList,
                examSubjectTotalVO,
                examCompareTotalStaticsList,
                subjectStaticsList,
                subjectStaticsGraphVO,
                compareScoreStaticsGraphVO,
                scoreRateByTypeInfo,
                scoreRateByPatternInfo,
                scoreRateByUnitInfo,
                scoreRateInfo
        );
        //본인관 평균성적 비교 값 주입
        achievementManagementDTO.setUserStaticsScore(examSubjectTotalVO.getStaticsAnswerScore());
        achievementManagementDTO.setTotalStaticsScore(totalStaticsScore / examSubjectStaticsList.size());

        return new ApiResultObjectDTO(achievementManagementDTO, resultCode);
    }

}
