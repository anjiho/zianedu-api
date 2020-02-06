package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.datasource.ZianCoreCode;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.*;
import com.zianedu.api.mapper.ExamMapper;
import com.zianedu.api.mapper.MenuMapper;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.utils.*;
import com.zianedu.api.vo.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.Subject;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.springframework.http.HttpStatus.OK;

@Service
public class ExamService extends PagingSupport {

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getGichulProblemList(int userKey, int groupCtgKey, int classCtgKey, int subjectCtgKey,
                                                 int sPage, int listLimit, String searchType, String searchText) {
        int resultCode = OK.value();

        int totalCnt = 0;
        List<TExamUserVO> gichulProblemList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            int startNumber = getPagingStartNumber(sPage, listLimit);
            totalCnt = examMapper.selectGiChulProblemListCount(userKey, groupCtgKey, classCtgKey, subjectCtgKey, searchType, searchText);
            gichulProblemList = examMapper.selectGiChulProblemList(userKey, groupCtgKey, classCtgKey, subjectCtgKey, searchType, searchText, startNumber, listLimit);
            if (gichulProblemList.size() > 0) {
                for (TExamUserVO vo : gichulProblemList) {
                    vo.setPrintQuestionFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getPrintQuestionFile()));
                    vo.setPrintCommentaryFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getPrintCommentaryFile()));
                }
            }
        }
        return new ApiPagingResultDTO(totalCnt, gichulProblemList, resultCode);
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
    public ApiPagingResultDTO getWeekBigExamList(int userKey, int sPage, int listLimit, int ctgKey, String searchType, String searchText) throws Exception {
        int resultCode = OK.value();

        int totalCnt = 0;
        List<TExamUserVO> examList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            int startNumber = getPagingStartNumber(sPage, listLimit);
            totalCnt = examMapper.selectWeekBigExamListCount(userKey, ctgKey, searchType, searchText);
            examList = examMapper.selectWeekBigExamList(userKey, ctgKey, searchType, searchText, startNumber, listLimit);
            for (TExamUserVO vo : examList) {
                vo.setPrintQuestionFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getPrintQuestionFile()));
                vo.setPrintCommentaryFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getPrintCommentaryFile()));
            }
            //if (userKey != 5) {
//                if (examList.size() > 0) {
//                    for (TExamUserVO vo : examList) {
//                        if (vo.getIscomplete() == 0) {
//                            //시험 날짜가 오늘기준 이전이면 2로 변경('종료')
//                            boolean isBetweenDate = DateUtils.isBetweenDateFromToday(vo.getAcceptStartDate(), vo.getAcceptEndDate());
//                            if (!isBetweenDate) {
//                                vo.setIscomplete(2);
//                            }
//                        }
//                    }
//                }
            //}
        }
        return new ApiPagingResultDTO(totalCnt, examList, resultCode);
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
        List<ExamSubjectStaticsVO> examSubjectStaticsList = new ArrayList<>();
        ExamSubjectTotalVO examSubjectTotalVO = new ExamSubjectTotalVO();
        List<ExamCompareTotalStaticsVO> examCompareTotalStaticsList = new ArrayList<>();
        List<SubjectStaticsVO> subjectStaticsList = new ArrayList<>();
        StaticsGraphVO subjectStaticsGraphVO = new StaticsGraphVO();
        StaticsGraphVO compareScoreStaticsGraphVO = new StaticsGraphVO();
        List<ScoreRateGraphVO> scoreRateByTypeInfo = new ArrayList<>();
        List<ScoreRateGraphVO> scoreRateByTypeInfo2 = new ArrayList<>();
        List<ScoreRateGraphVO> scoreRateByPatternInfo = new ArrayList<>();
        List<ScoreRateGraphVO> scoreRateByPatternInfo2 = new ArrayList<>();
        List<ScoreRateGraphVO> scoreRateByUnitInfo = new ArrayList<>();
        List<ScoreRateGraphVO> scoreRateByUnitInfo2 = new ArrayList<>();
        List<ScoreRateDTO>scoreRateInfo = new ArrayList<>();
        RadialGraphVO radialGraphVO = new RadialGraphVO();
        CompareScoreSubjectGraphVO compareScoreSubjectGraphVO = new CompareScoreSubjectGraphVO();
        CompareScoreSubjectGraphVO compareScoreSubjectGraphVO2 = new CompareScoreSubjectGraphVO();
        CompareScoreSubjectGraphVO compareScoreSubjectGraphVO3 = new CompareScoreSubjectGraphVO();
        List<SubjectAnalysisGraphDTO> subjectAnalysisGraphInfo = new ArrayList<>();

        int totalStaticsScore = 0;
        int topTenSumScore = 0;
        int topThirtySumScore = 0;
        int totalMySumScore = 0;
        int totalSumScore = 0;

        List<String> subjectStaticsGraphCategoryNameList = new ArrayList<>();
        List<Double> subjectStaticsGraphTopTenDataList = new ArrayList<>();
        List<Double> subjectStaticsGraphTopThirtyDataList = new ArrayList<>();
        List<Double> subjectStaticsGraphMyDataList = new ArrayList<>();

        if (examUserKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TExamUserVO examHeaderInfo = examMapper.selectExamResultHeaderInfo(examUserKey);
            List<String> subjectNameList = examMapper.selectExamSubjectNameList(examUserKey);
            String subjectName = "";

            if (subjectNameList.size() > 0) {
                 subjectName = StringUtils.implodeList(",", subjectNameList);
            }
            achievementTopInfoVO.setSerial(("0" + examHeaderInfo.getSerial()));
            achievementTopInfoVO.setSubjectName(subjectName);

            examSubjectStaticsList = examMapper.selectExamSubjectStaticsList(examUserKey);
            if (examSubjectStaticsList.size() > 0) {
                int staticsAnswerCnt = 0;
                int staticsAnswerScore = 0;
                int staticsUserGrade = 0;
                int staticsTotalAnswerCnt = 0;

                List<String> radialSubjectNameList = new ArrayList<>();
                List<Integer> radialSubjectScoreList = new ArrayList<>();
                List<Integer> topTenScoreList = new ArrayList<>();
                List<LineGraphDataVO> lineGraphDataList = new ArrayList<>();
                List<LineGraphDataVO> lineGraphDataList2 = new ArrayList<>();
                List<LineGraphDataVO> lineGraphDataList3 = new ArrayList<>();

                for (ExamSubjectStaticsVO vo : examSubjectStaticsList) {
                    int i=0;
                    ScoreRateDTO scoreRateDTO = new ScoreRateDTO();
                    ScoreRateGraphVO scoreRateByTypeVo = new ScoreRateGraphVO();
                    ScoreRateGraphVO scoreRateByTypeVo2 = new ScoreRateGraphVO();
                    ScoreRateGraphVO scoreRateByPatternVo = new ScoreRateGraphVO();
                    ScoreRateGraphVO scoreRateByUnitVo = new ScoreRateGraphVO();
                    LineGraphDataVO lineGraphDataVO = new LineGraphDataVO();
                    SubjectAnalysisGraphDTO subjectAnalysisGraphDTO = new SubjectAnalysisGraphDTO();

                    vo.setAnswerScore(vo.getAnswerCnt() * 5);   //원점수 계산
                    Integer userGrade = examMapper.selectExamSubjectGrade(vo.getExamQuesBankSubjectKey(), vo.getExamKey(), vo.getUserKey());
                    vo.setUserGrade(Util.isIntegerNullValue(userGrade)); //석차 주입
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

                    //방사형 그래프 데이터작업
                    radialSubjectNameList.add(vo.getSubjectName());
                    radialSubjectScoreList.add(vo.getAnswerScore());

                    //과목별 평균
                    double tenPercentScore = examMapper.selectSubjectTopPercentScore(examUserKey, vo.getExamKey(), vo.getExamQuesBankSubjectKey(), 10);
                    double thirtyPercentScore = examMapper.selectSubjectTopPercentScore(examUserKey, vo.getExamKey(), vo.getExamQuesBankSubjectKey(), 30);
                    SubjectStaticsVO subjectStaticsVO = new SubjectStaticsVO(
                        vo.getSubjectName(), tenPercentScore, thirtyPercentScore, vo.getAnswerScore(), examCompareTotalStaticsVO.getTotalSubjectScore()
                    );
                    //과목별 평군의 '전체'합
                    topTenSumScore += tenPercentScore;
                    topThirtySumScore += thirtyPercentScore;
                    totalMySumScore += vo.getAnswerScore();
                    totalSumScore += examCompareTotalStaticsVO.getTotalSubjectScore();

                    subjectStaticsList.add(subjectStaticsVO);


                    subjectStaticsGraphCategoryNameList.add(vo.getSubjectName());
                    subjectStaticsGraphTopTenDataList.add(tenPercentScore);
                    subjectStaticsGraphTopThirtyDataList.add(thirtyPercentScore);
                    subjectStaticsGraphMyDataList.add(examCompareTotalStaticsVO.getTotalSubjectScore());

                    //유형별 정답률
                    List<ScoreRateGraphVO> scoreRateByTypeList = examMapper.selectScoreRateByStepCtgKey(vo.getExamQuesBankSubjectKey(), vo.getUserKey());
                    if (scoreRateByTypeList.size() > 0) {
                        List<String> scoreRateByTypeNameList = new ArrayList<>();
                        List<Integer> scoreRateByTypeProblemList = new ArrayList<>();
                        List<Integer> scoreRateByTypeProblemList2 = new ArrayList<>();
                        List<Integer> scoreRateByTypeScoreList = new ArrayList<>();
                        for (ScoreRateGraphVO scoreRateGraphVO : scoreRateByTypeList) {
                            scoreRateByTypeNameList.add(scoreRateGraphVO.getCtgName());
                            scoreRateByTypeProblemList.add(scoreRateGraphVO.getProblemCnt());
                            scoreRateByTypeProblemList2.add((scoreRateGraphVO.getProblemCnt() * 2));
                            scoreRateByTypeScoreList.add(scoreRateGraphVO.getScoreCnt());
                        }

                        String[] scoreRateByTypeNames = StringUtils.arrayListToStringArray(scoreRateByTypeNameList);
                        Integer[] scoreRateByTypeProblems = StringUtils.arrayIntegerListToStringArray(scoreRateByTypeProblemList);
                        Integer[] scoreRateByTypeProblems2 = StringUtils.arrayIntegerListToStringArray(scoreRateByTypeProblemList2);
                        Integer[] scoreRateByTypeScores = StringUtils.arrayIntegerListToStringArray(scoreRateByTypeScoreList);
                        ScoreRateGraphVO scoreRateGraphVO = new ScoreRateGraphVO(vo.getSubjectName(), scoreRateByTypeNames, scoreRateByTypeProblems, scoreRateByTypeScores);
                        ScoreRateGraphVO scoreRateGraphVO2 = new ScoreRateGraphVO(vo.getSubjectName(), scoreRateByTypeNames, scoreRateByTypeProblems2, scoreRateByTypeScores);

                        scoreRateByTypeInfo.add(scoreRateGraphVO);
                        scoreRateByTypeInfo2.add(scoreRateGraphVO2);    //누적

                        scoreRateByTypeVo = new ScoreRateGraphVO(vo.getSubjectName(), scoreRateByTypeNames, scoreRateByTypeProblems, scoreRateByTypeScores);
                    }
                    //패턴별 정답률
                    List<ScoreRateGraphVO> scoreRateByPatternList = examMapper.selectScoreRateByPatternCtgKey(vo.getExamQuesBankSubjectKey(), vo.getUserKey());
                    if (scoreRateByPatternList.size() > 0) {
                        List<String> scoreRateByTypeNameList = new ArrayList<>();
                        List<Integer> scoreRateByTypeProblemList = new ArrayList<>();
                        List<Integer> scoreRateByTypeProblemList2 = new ArrayList<>();
                        List<Integer> scoreRateByTypeScoreList = new ArrayList<>();
                        for (ScoreRateGraphVO scoreRateGraphVO : scoreRateByPatternList) {
                            scoreRateByTypeNameList.add(scoreRateGraphVO.getCtgName());
                            scoreRateByTypeProblemList.add(scoreRateGraphVO.getProblemCnt());
                            scoreRateByTypeProblemList2.add((scoreRateGraphVO.getProblemCnt() * 2));
                            scoreRateByTypeScoreList.add(scoreRateGraphVO.getScoreCnt());
                        }

                        String[] scoreRateByTypeNames = StringUtils.arrayListToStringArray(scoreRateByTypeNameList);
                        Integer[] scoreRateByTypeProblems = StringUtils.arrayIntegerListToStringArray(scoreRateByTypeProblemList);
                        Integer[] scoreRateByTypeProblems2 = StringUtils.arrayIntegerListToStringArray(scoreRateByTypeProblemList2);
                        Integer[] scoreRateByTypeScores = StringUtils.arrayIntegerListToStringArray(scoreRateByTypeScoreList);
                        ScoreRateGraphVO scoreRateGraphVO = new ScoreRateGraphVO(vo.getSubjectName(), scoreRateByTypeNames, scoreRateByTypeProblems, scoreRateByTypeScores);
                        ScoreRateGraphVO scoreRateGraphVO2 = new ScoreRateGraphVO(vo.getSubjectName(), scoreRateByTypeNames, scoreRateByTypeProblems2, scoreRateByTypeScores);

                        scoreRateByPatternInfo.add(scoreRateGraphVO);
                        scoreRateByPatternInfo2.add(scoreRateGraphVO2);     //누적

                        scoreRateByPatternVo = new ScoreRateGraphVO(vo.getSubjectName(), scoreRateByTypeNames, scoreRateByTypeProblems, scoreRateByTypeScores);
                    }
                    //대단원별 정답률
                    List<ScoreRateGraphVO> scoreRateByUnitList = examMapper.selectScoreRateByUnitCtgKey(vo.getExamQuesBankSubjectKey(), vo.getUserKey());
                    if (scoreRateByUnitList.size() > 0) {
                        List<String> scoreRateByTypeNameList = new ArrayList<>();
                        List<Integer> scoreRateByTypeProblemList = new ArrayList<>();
                        List<Integer> scoreRateByTypeProblemList2 = new ArrayList<>();
                        List<Integer> scoreRateByTypeScoreList = new ArrayList<>();
                        for (ScoreRateGraphVO scoreRateGraphVO : scoreRateByUnitList) {
                            scoreRateByTypeNameList.add(scoreRateGraphVO.getCtgName());
                            scoreRateByTypeProblemList.add(scoreRateGraphVO.getProblemCnt());
                            scoreRateByTypeProblemList2.add((scoreRateGraphVO.getProblemCnt() * 2));
                            scoreRateByTypeScoreList.add(scoreRateGraphVO.getScoreCnt());
                        }

                        String[] scoreRateByTypeNames = StringUtils.arrayListToStringArray(scoreRateByTypeNameList);
                        Integer[] scoreRateByTypeProblems = StringUtils.arrayIntegerListToStringArray(scoreRateByTypeProblemList);
                        Integer[] scoreRateByTypeProblems2 = StringUtils.arrayIntegerListToStringArray(scoreRateByTypeProblemList2);
                        Integer[] scoreRateByTypeScores = StringUtils.arrayIntegerListToStringArray(scoreRateByTypeScoreList);
                        ScoreRateGraphVO scoreRateGraphVO = new ScoreRateGraphVO(vo.getSubjectName(), scoreRateByTypeNames, scoreRateByTypeProblems, scoreRateByTypeScores);
                        ScoreRateGraphVO scoreRateGraphVO2 = new ScoreRateGraphVO(vo.getSubjectName(), scoreRateByTypeNames, scoreRateByTypeProblems2, scoreRateByTypeScores);

                        scoreRateByUnitInfo.add(scoreRateGraphVO);
                        scoreRateByUnitInfo2.add(scoreRateGraphVO2);

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
                double[] subjectStaticsGraphTopTenData = StringUtils.arrayListToDoubleArray(subjectStaticsGraphTopTenDataList);
                double[] subjectStaticsGraphTopThirtyData = StringUtils.arrayListToDoubleArray(subjectStaticsGraphTopThirtyDataList);
                double[] subjectStaticsGraphMyData = StringUtils.arrayListToDoubleArray(subjectStaticsGraphMyDataList);
                subjectStaticsGraphVO = new StaticsGraphVO(
                        subjectStaticsGraphCategoryNames, subjectStaticsGraphTopTenData, subjectStaticsGraphTopThirtyData, subjectStaticsGraphMyData
                );
                //점수비교 그래프
                double[] scoreCompareGraphTopTenData = {topTenSumScore / examSubjectStaticsList.size()};
                double[] scoreCompareGraphStaticsData = {totalStaticsScore / examSubjectStaticsList.size()};
                double[] scoreCompareGraphMyData = {examSubjectTotalVO.getStaticsAnswerScore()};
                compareScoreStaticsGraphVO = new StaticsGraphVO(
                        scoreCompareGraphTopTenData, scoreCompareGraphStaticsData, scoreCompareGraphMyData
                );
                //방사형 그래프
                String[] radialSubjectNames = StringUtils.arrayListToStringArray(radialSubjectNameList);
                Integer[] radialSubjectScores = StringUtils.arrayIntegerListToStringArray(radialSubjectScoreList);
                radialGraphVO = new RadialGraphVO(radialSubjectNames, radialSubjectScores);
                //과목별 평균 옆 라인 그래프
                for (int i=0; i<3; i++) {
                    double data[] = null;
                    if (i == 0) data = subjectStaticsGraphVO.getCategoryTopTenData();
                    else if (i == 1) data = subjectStaticsGraphVO.getCategoryTopThirtyData();
                    else if (i == 2) data = subjectStaticsGraphVO.getCategoryMyData();
                    LineGraphDataVO lineGraphDataVO = new LineGraphDataVO(ZianApiUtils.TOP_DATA_NAMES[i], data);
                    lineGraphDataList.add(lineGraphDataVO);
                }
                compareScoreSubjectGraphVO = new CompareScoreSubjectGraphVO(radialSubjectNames, lineGraphDataList);
                //점수비교 라인 그래프
                for (int j=0; j<3; j++) {
                    double data2[] = null;
                    if (j == 0) data2 =  compareScoreStaticsGraphVO.getCategoryTopTenData();
                    else if (j == 1) data2 = compareScoreStaticsGraphVO.getCategoryStaticsData();
                    else if (j == 2) data2 = compareScoreStaticsGraphVO.getCategoryMyData();
                    LineGraphDataVO lineGraphDataVO2 = new LineGraphDataVO(ZianApiUtils.TOP_DATA_NAMES2[j], data2);
                    lineGraphDataList2.add(lineGraphDataVO2);
                }
                compareScoreSubjectGraphVO2 = new CompareScoreSubjectGraphVO(null, lineGraphDataList2);
                //회차별 점수비교 그래프
                for (int k=0; k<3; k++) {
                    double data3[] = null;
                    List<Double> dataList = new ArrayList<>();
                    if (k == 0 ) {
                        data3 = compareScoreStaticsGraphVO.getCategoryTopTenData();
                        for (int k2=0; k2<2; k2++) {
                            if (k2 == 0) dataList.add(0d);
                            else dataList.add(data3[0]);
                        }
                    } else if (k == 1) {
                        data3 = compareScoreStaticsGraphVO.getCategoryStaticsData();
                        for (int k2=0; k2<2; k2++) {
                            if (k2 == 0) dataList.add(0d);
                            else dataList.add(data3[0]);
                        }
                    } else if (k == 2) {
                        data3 = compareScoreStaticsGraphVO.getCategoryMyData();
                        for (int k2=0; k2<2; k2++) {
                            if (k2 == 0) dataList.add(0d);
                            else dataList.add(data3[0]);
                        }
                    }
                    LineGraphDataVO lineGraphDataVO3 = new LineGraphDataVO(ZianApiUtils.TOP_DATA_NAMES2[k], StringUtils.arrayListToDoubleArray(dataList));
                    lineGraphDataList3.add(lineGraphDataVO3);
                }
                compareScoreSubjectGraphVO3 = new CompareScoreSubjectGraphVO(null, lineGraphDataList3);


                for(ScoreRateGraphVO scoreRateGraphVO : scoreRateByTypeInfo) {
                    int i=0;
                    SubjectAnalysisGraphDTO subjectAnalysisGraphDTO = new SubjectAnalysisGraphDTO();
                    subjectAnalysisGraphDTO.setSubjectName(scoreRateGraphVO.getSubjectName());

                    subjectAnalysisGraphDTO.setScoreRateByTypeInfo(scoreRateGraphVO);
                    subjectAnalysisGraphDTO.setScoreRateByTypeInfo2(scoreRateByTypeInfo2.get(i));

                    subjectAnalysisGraphDTO.setScoreRateByPatternInfo(scoreRateByPatternInfo.get(i));
                    subjectAnalysisGraphDTO.setScoreRateByPatternInfo2(scoreRateByPatternInfo2.get(i));

                    subjectAnalysisGraphDTO.setScoreRateByUnitInfo(scoreRateByUnitInfo.get(i));
                    subjectAnalysisGraphDTO.setScoreRateByUnitInfo2(scoreRateByUnitInfo2.get(i));

                    subjectAnalysisGraphInfo.add(subjectAnalysisGraphDTO);
                    i++;
                }
            }
        }
        AchievementManagementDTO achievementManagementDTO = new AchievementManagementDTO(
                achievementTopInfoVO,
                examSubjectStaticsList,
                examSubjectTotalVO,
                examCompareTotalStaticsList,
                subjectStaticsList,
                subjectStaticsGraphVO,
                compareScoreSubjectGraphVO2,
                compareScoreSubjectGraphVO3,
                scoreRateByTypeInfo,
                scoreRateByPatternInfo,
                scoreRateByUnitInfo,
                scoreRateInfo,
                radialGraphVO,
                compareScoreSubjectGraphVO,
                subjectAnalysisGraphInfo
        );
        //본인관 평균성적 비교 값 주입
        achievementManagementDTO.setUserStaticsScore(examSubjectTotalVO.getStaticsAnswerScore());
        achievementManagementDTO.setTotalStaticsScore(totalStaticsScore / examSubjectStaticsList.size());

        return new ApiResultObjectDTO(achievementManagementDTO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getAchievementManagementDetailInfoBySubject(int examUserKey) {
        int resultCode = OK.value();

        List<AchievementSubjectDTO> resultList = new ArrayList<>();
        TExamUserVO examHeaderInfo = new TExamUserVO();
        List<String> subjectNameList = new ArrayList<>();
        List<ExamSubjectStaticsVO> examSubjectStaticsList = new ArrayList<>();
        List<ExamStaticsDetailSubjectVO> examStaticsDetailSubjectList = new ArrayList<>();

        if (examUserKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            examHeaderInfo = examMapper.selectExamResultHeaderInfo(examUserKey);
            subjectNameList = examMapper.selectExamSubjectNameList(examUserKey);
            String subjectName = "";

            if (subjectNameList.size() > 0) {
                subjectName = StringUtils.implodeList(",", subjectNameList);
            }

            examSubjectStaticsList = examMapper.selectExamSubjectStaticsList(examUserKey);
            if (examSubjectStaticsList.size() > 0) {
                //과목별 리스트
                for (ExamSubjectStaticsVO vo : examSubjectStaticsList) {
                    AchievementSubjectDTO achievementSubjectDTO = new AchievementSubjectDTO();
                    achievementSubjectDTO.setExamHeaderInfo(examHeaderInfo);
                    achievementSubjectDTO.setSubjectNameList(subjectName);
                    achievementSubjectDTO.setSubjectName(vo.getSubjectName());


                    examStaticsDetailSubjectList = examMapper.selectExamStaticsDetailInfoBySubject(vo.getExamQuesBankSubjectKey(), vo.getUserKey());
                    //시험문제 개수만큼
                    for (ExamStaticsDetailSubjectVO subjectVO : examStaticsDetailSubjectList) {
                        String unitName = categoryService.getMakeUnitName(subjectVO.getUnitCtgKey());
                        subjectVO.setUnitName(unitName);
                        subjectVO.setScorePercent(ZianUtils.getTopAccumulatePercent(subjectVO.getTotalCnt(), subjectVO.getTotalScoreCnt()));
                        //보기별 정답률
                        List<ProblemNumberScoreVO> problemNumberScoreList = examMapper.selectProblemNumberScoreList(vo.getExamQuesBankSubjectKey(), subjectVO.getExamQuesBankKey());
                        for (ProblemNumberScoreVO numberScoreVO : problemNumberScoreList) {
                            numberScoreVO.setScorePercent(ZianUtils.getTopAccumulatePercent(subjectVO.getTotalCnt(), numberScoreVO.getScoreCnt()));
                        }
                        subjectVO.setProblemScoreList(problemNumberScoreList);
                    }
                    achievementSubjectDTO.setResultList(examStaticsDetailSubjectList);
                    resultList.add(achievementSubjectDTO);
                }
            }
        }
        return new ApiResultObjectDTO(resultList, resultCode);
    }

    /**
     * TODO 배열안에 아닌 값을 빼기
     * @param examUserKey
     * @param isScore
     * @param isInterest
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultObjectDTO getExamWrongNoteList(int examUserKey, int isScore, int isInterest) {
        int resultCode = OK.value();

        List<WrongNoteDTO> resultList = new ArrayList<>();

        if (examUserKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TExamUserVO examHeaderInfo = examMapper.selectExamResultHeaderInfo(examUserKey);
            List<String> subjectNameList = examMapper.selectExamSubjectNameList(examUserKey);
            String subjectName = "";

            if (subjectNameList.size() > 0) {
                subjectName = StringUtils.implodeList(",", subjectNameList);
            }

            List<ExamSubjectStaticsVO> examSubjectStaticsList = examMapper.selectExamSubjectStaticsList(examUserKey);
            if (examSubjectStaticsList.size() > 0) {
                //과목별 리스트
                for (ExamSubjectStaticsVO vo : examSubjectStaticsList) {
                    WrongNoteDTO wrongNoteDTO = new WrongNoteDTO();
                    wrongNoteDTO.setExamHeaderInfo(examHeaderInfo);
                    wrongNoteDTO.setSubjectNameList(subjectName);
                    wrongNoteDTO.setSubjectName(vo.getSubjectName());

                    List<ExamWrongAnswerVO> examWrongAnswerList = examMapper.selectWrongAnswerList(vo.getExamQuesBankSubjectKey(), vo.getUserKey(), isScore, isInterest);
                    //시험문제 개수만큼
                    for (ExamWrongAnswerVO wrongAnswerVO : examWrongAnswerList) {
                        String unitName = categoryService.getMakeUnitName(wrongAnswerVO.getUnitCtgKey());
                        wrongAnswerVO.setUnitName(unitName);
                        wrongAnswerVO.setScorePercent(ZianUtils.getTopAccumulatePercent(wrongAnswerVO.getTotalCnt(), wrongAnswerVO.getTotalScoreCnt()));
                        wrongAnswerVO.setQuestionImage(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), wrongAnswerVO.getQuestionImage()));
                        wrongAnswerVO.setCommentaryImage(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), wrongAnswerVO.getCommentaryImage()));
                        wrongAnswerVO.setTheoryLearningUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), wrongAnswerVO.getTheoryLearningUrl()));
                    }
                    wrongNoteDTO.setResultList(examWrongAnswerList);
                    resultList.add(wrongNoteDTO);
                }
            }
        }
        return new ApiResultObjectDTO(resultList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getExamMasterGateInfo(int examKey, int userKey) {
        int resultCode = OK.value();

        Integer examUserKey = null;
        TExamMasterVO tExamMasterVO = new TExamMasterVO();
        String subjectName = "";
        if (examKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
//            String onOff = "";
//            TLinkKeyVO linkKeyVO = productMapper.selectExamOnOffKeyByExamKey(examKey);
//            if ("2".equals(linkKeyVO.getReqType())) onOff = "2";
//            else if ("3".equals(linkKeyVO.getReqType())) onOff = "1";

            examUserKey = this.injectUserExamInfo(examKey, userKey, "2", 0);
            if (examUserKey != null) {
                tExamMasterVO = examMapper.selectExamMasterInfo(examKey);
                List<String> subjectNameList = examMapper.selectExamSubjectNameList(examUserKey);
                if (subjectNameList.size() > 0) {
                    subjectName = StringUtils.implodeList(",", subjectNameList);
                }
            }
        }
        ExamGateDTO examGateDTO = new ExamGateDTO(examUserKey, tExamMasterVO, subjectName);
        return new ApiResultObjectDTO(examGateDTO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getExamMasterGateInfoByExamUserKey(int examUserKey) {
        int resultCode = OK.value();

        TExamMasterVO tExamMasterVO = new TExamMasterVO();
        String subjectName = "";
        if (examUserKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            if (examUserKey > 0) {
                tExamMasterVO = examMapper.selectExamMasterInfoByExamUserKey(examUserKey);
                List<String> subjectNameList = examMapper.selectExamSubjectNameList(examUserKey);
                if (subjectNameList.size() > 0) {
                    subjectName = StringUtils.implodeList(",", subjectNameList);
                }
            }
        }
        ExamGateDTO examGateDTO = new ExamGateDTO(examUserKey, tExamMasterVO, subjectName);
        return new ApiResultObjectDTO(examGateDTO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getUserExamList(int examUserKey, int userKey) {
        int resultCode = OK.value();

        List<String> subjectNameList = new ArrayList<>();
        List<ExamListDTO> examDTOList = new ArrayList<>();
        if (examUserKey == 0 && userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            List<TExamSubjectUserVO> examSubjectUserList = examMapper.selectTExamSubjectUserList(examUserKey, userKey);
            if (examSubjectUserList.size() > 0) {
                //시험시작 상태로 업데이트
                this.updateExamResultStatus(examUserKey, 0, 0, 1);

                for (TExamSubjectUserVO subjectUserVO : examSubjectUserList) {
                    subjectNameList.add(subjectUserVO.getSubjectName());
                    List<ExamListVO> examList = examMapper.selectExamList(subjectUserVO.getExamQuesBankSubjectKey());
                    for (ExamListVO examListVO : examList) {
                        examListVO.setExamUserKey(subjectUserVO.getExamUserKey());
                        examListVO.setExamSbjUserKey(subjectUserVO.getExamSbjUserKey());
                        examListVO.setQuestionImage(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), examListVO.getQuestionImage()));

                    }
                    ExamListDTO examListDTO = new ExamListDTO(subjectUserVO.getSubjectName(), examList);
                    examDTOList.add(examListDTO);
                }
                TExamUserVO examHeaderInfo = examMapper.selectExamResultHeaderInfo(examUserKey);
                examHeaderInfo.setSubjectNameList(StringUtils.implodeList(",", subjectNameList));
                examDTOList.get(0).setExamHeaderInfo(examHeaderInfo);
            }
        }
        return new ApiResultObjectDTO(examDTOList, resultCode);
    }

    /**
     * 모의고사 시험을 보기위한 정보 저장
     * @param examKey
     * @param userKey
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer injectUserExamInfo(int examKey, int userKey, String onOff, int jGKey) {
        if (examKey == 0 && userKey == 0) return null;
        Integer examUserKey = null;
        TExamUserVO tExamUserVO = examMapper.selectTExamUserInfo(examKey, userKey);
        //'응시하기'를 한번도 안했을때 시험정보 저장하기
        if (tExamUserVO == null) {
            String serial = onOff + examMapper.selectTExamUserSerial();
            TExamUserVO examUserVO = new TExamUserVO(examKey, userKey, serial, onOff, jGKey);
            examMapper.insertTExamUser(examUserVO);
            examUserKey = examUserVO.getExamUserKey();

            if (examUserKey > 0) {
                List<TBankSubjectExamLinkVO> examMasterSubjectList = examMapper.selectExamMasterSubjectList(examKey);
                if (examMasterSubjectList.size() > 0) {
                    for (TBankSubjectExamLinkVO vo : examMasterSubjectList) {
                        TExamSubjectUserVO tExamSubjectUserVO = new TExamSubjectUserVO(
                                examUserKey, examKey, vo.getBankSubjectExamLinkKey(), vo.getExamQuestionBankSubjectKey(), userKey
                        );
                        examMapper.insertTExamSubjectUser(tExamSubjectUserVO);
                    }
                }
            }
        } else {
            examUserKey = tExamUserVO.getExamUserKey();
        }
        return examUserKey;
    }

    /**
     * 사용자 시험 결과 정보 저장
     * @param examResultDTOList
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO injectUserExamResult(int playTime, List<ExamResultDTO>examResultDTOList) {
        int resultCode = OK.value();

        int examUserKey = 0;
        String sql = "";
        if (examResultDTOList.size() == 0) {
            resultCode = ZianErrCode.CUSTOM_DATA_SIZE_ZERO.code();
        } else {
             sql = "INSERT INTO T_EXAM_QUESTION_USER (EXAM_QUESTION_USER_KEY, EXAM_USER_KEY, USER_KEY, EXAM_SBJ_USER_KEY, EXAM_QUESTION_KEY, EXAM_QUESTION_BANK_KEY, USER_ANSWER, UPDATEDT, SCORE)" +
                    "VALUES (T_EXAM_QUESTION_USER_SEQ.nextval, ?, ?, ?, 0, ?, ?, sysdate, ?)";
            jdbcTemplate.batchUpdate(
                    sql,
                    examResultDTOList,
                    5,
                    new ParameterizedPreparedStatementSetter<ExamResultDTO>() {
                        @Override
                        public void setValues(PreparedStatement ps, ExamResultDTO dto) throws SQLException {
                            ps.setInt(1, dto.getExamUserKey());
                            ps.setInt(2, dto.getUserKey());
                            ps.setInt(3, dto.getExamSbjUserKey());
                            ps.setInt(4, dto.getExamQuestionBankKey());
                            ps.setInt(5, dto.getUserAnswer());
                            ps.setInt(6, dto.getScore());
                        }
                    });
            examUserKey = examResultDTOList.get(0).getExamUserKey();
            this.updateExamResultStatus(examUserKey, 1, playTime, 0);
        }
        return new ApiResultCodeDTO("EXAM_USER_KEY", examUserKey, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateExamResultStatus(int examUserKey, int isComplete, int playTime, int isStart) {
        TExamUserVO tExamUserVO = new TExamUserVO(examUserKey, isComplete, playTime, isStart);
        examMapper.updateTExamUser(tExamUserVO);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getMockExamClassCtgSelectBoxList(int onOffKey, boolean isBuy, int userKey) {
        int resultCode = OK.value();

        List<SelectboxDTO> selectBoxList = new CopyOnWriteArrayList<>();
        if (onOffKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            List<SelectboxDTO> list = new ArrayList<>();
            if (isBuy) {
                list = examMapper.selectMockExamBuyClassCtgSelectBoxList(userKey, onOffKey);
            } else {
                list = examMapper.selectMockExamClassCtgSelectBoxList(onOffKey);
            }

            if (list.size() > 0) {
                SelectboxDTO selectboxDTO = new SelectboxDTO();
                selectboxDTO.setKey("");
                selectboxDTO.setValue("직렬선택");
                selectBoxList.add(selectboxDTO);
                for (SelectboxDTO dto : list) {
                    selectboxDTO = new SelectboxDTO(dto.getKey(), dto.getValue());
                    selectBoxList.add(selectboxDTO);
                }
            }
        }
        return new ApiResultListDTO(selectBoxList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getWeekMockExamClassCtgSelectBoxList() {
        int resultCode = OK.value();

        List<SelectboxDTO> selectBoxList = new CopyOnWriteArrayList<>();
        List<SelectboxDTO> list = examMapper.selectWeekMockExamClassCtgSelectBoxList();

        SelectboxDTO selectboxDTO = new SelectboxDTO();
        selectboxDTO.setKey("");
        selectboxDTO.setValue("직렬선택");
        selectBoxList.add(selectboxDTO);

        if (list.size() > 0) {
            for (SelectboxDTO dto : list) {
                selectboxDTO = new SelectboxDTO(dto.getKey(), dto.getValue());
                selectBoxList.add(selectboxDTO);
            }
        }
        return new ApiResultListDTO(selectBoxList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getMockExamListAtBuy(int userKey, int onOffKey, int ctgKey, int sPage, int listLimit, String searchType, String searchText) throws Exception {
        int resultCode = OK.value();

        int totalCnt = 0;
        List<MockExamProductVO> mockExamProductList = new ArrayList<>();
        int startNumber = getPagingStartNumber(sPage, listLimit);

        if (onOffKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            totalCnt = examMapper.selectMockExamListAtBuyCount(userKey, onOffKey, ctgKey, searchType, searchText);
            mockExamProductList = examMapper.selectMockExamListAtBuy(userKey, onOffKey, ctgKey, searchType, searchText, startNumber, listLimit);
            if (mockExamProductList.size() > 0) {
                for (MockExamProductVO productVO : mockExamProductList) {
                    TExamMasterVO examVO = examMapper.selectTExamDateInfo(productVO.getExamKey());
                    if (examVO != null) {
                        productVO.setAcceptStartDate(examVO.getAcceptStartDate());
                        productVO.setAcceptEndDate(examVO.getAcceptEndDate());
                        productVO.setStareDate(examVO.getOnlineStartDate());
                        productVO.setEndDate(examVO.getOnlineEndDate());
                        productVO.setClassName(examVO.getClassName());
                        productVO.setPrintCommentaryFile(examVO.getPrintCommentaryFile());
                        productVO.setPrintQuestionFile(examVO.getPrintQuestionFile());
                        //응시가능 여부 주입
                        if (DateUtils.isBetweenDateFromToday(examVO.getOnlineStartDate(), examVO.getOnlineEndDate())) {
                            if (productVO.getIsComplete() == 0) {   //응시가능
                                productVO.setAcceptType(1);
                            } else if (productVO.getIsComplete() == 1) {    //응시완료
                                productVO.setAcceptType(2);
                            }
                        } else {
                            productVO.setAcceptType(0); //응시마감
                        }
                        //문제지, 해설지 주입
                        TExamMasterVO examMasterVO = examMapper.selectExamMasterInfo(productVO.getExamKey());
                        if (examMasterVO != null) {
                            productVO.setPrintQuestionFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), examMasterVO.getPrintQuestionFile()));
                            productVO.setPrintCommentaryFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), examMasterVO.getPrintCommentaryFile()));
                        }
                    }
                }
            }
        }
        return new ApiPagingResultDTO(totalCnt, mockExamProductList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getGichulSelectBoxList(String selectBoxType) {
        int resultCode = OK.value();

        List<SelectboxDTO> selectBoxList = new ArrayList<>();
        if ("".equals(selectBoxType)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            List<TCategoryVO> list = new ArrayList<>();
            SelectboxDTO selectboxDTO = new SelectboxDTO();

            selectboxDTO.setKey("");
            if ("SERIAL".equals(selectBoxType)) {
                list = menuMapper.selectTCategoryByParentKey(133);
                selectboxDTO.setValue("직렬선택");
            } else if ("RATING".equals(selectBoxType)) {
                list = menuMapper.selectTCategoryByParentKey(4309);
                selectboxDTO.setValue("급수선택");
            } else if ("SUBJECT".equals(selectBoxType)) {
                list = menuMapper.selectTCategoryByParentKey(70);
                selectboxDTO.setValue("과목선택");
            }
            selectBoxList.add(selectboxDTO);

            if (list.size() > 0) {
                for (TCategoryVO vo : list) {
                    selectboxDTO = new SelectboxDTO(vo.getCtgKey(), vo.getName());
                    selectBoxList.add(selectboxDTO);
                }
            }
        }
        return new ApiResultListDTO(selectBoxList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getUserMockExamResultListAtBuy(int userKey, int onOffKey, int ctgKey, int sPage, int listLimit, String searchType, String searchText) {
        int resultCode = OK.value();

        int totalCnt = 0;
        List<TExamUserVO> resultList = new ArrayList<>();
        int startNumber = getPagingStartNumber(sPage, listLimit);

        if (userKey == 0 && onOffKey < 2) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            totalCnt = examMapper.selectUserMockExamResultCountAtBuy(userKey, onOffKey, ctgKey, searchType, searchText);
            resultList = examMapper.selectUserMockExamResultListAtBuy(userKey, onOffKey, ctgKey, searchType, searchText, startNumber, listLimit);
            if (resultList.size() > 0) {
                for (TExamUserVO vo : resultList) {
                    //문제지, 해설지 주입
                    TExamMasterVO examMasterVO = examMapper.selectExamMasterInfo(vo.getExamKey());
                    if (examMasterVO != null) {
                        vo.setPrintQuestionFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), examMasterVO.getPrintQuestionFile()));
                        vo.setPrintCommentaryFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), examMasterVO.getPrintCommentaryFile()));
                    }
                }
            }
        }
        return new ApiPagingResultDTO(totalCnt, resultList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getUserFreeExamResultList(int userKey, String examType, int classCtgKey, int groupCtgKey, int subjectCtgKey,
                                                        int sPage, int listLimit, String searchType, String searchText) {
        int resultCode = OK.value();

        int totalCnt = 0;
        List<TExamUserVO> resultList = new ArrayList<>();
        int startNumber = getPagingStartNumber(sPage, listLimit);

        if (userKey == 0 && "".equals(examType)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            if ("GICHUL".equals(examType)) {
                totalCnt = examMapper.selectUserFreeExamResultCount(userKey, 1, classCtgKey, groupCtgKey, subjectCtgKey, searchType, searchText);
                resultList = examMapper.selectUserFreeExamResult(userKey, 1, classCtgKey, groupCtgKey, subjectCtgKey, searchType, searchText, startNumber, listLimit);
            } else if ("WEEK".equals(examType)) {
                totalCnt = examMapper.selectUserFreeExamResultCount(userKey, 0, classCtgKey, groupCtgKey, subjectCtgKey, searchType, searchText);
                resultList = examMapper.selectUserFreeExamResult(userKey, 0, classCtgKey, groupCtgKey, subjectCtgKey, searchType, searchText, startNumber, listLimit);
            }
            if (resultList.size() > 0) {
                for (TExamUserVO vo : resultList) {
                    //문제지, 해설지 주입
                    TExamMasterVO examMasterVO = examMapper.selectExamMasterInfo(vo.getExamKey());
                    if (examMasterVO != null) {
                        vo.setPrintQuestionFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), examMasterVO.getPrintQuestionFile()));
                        vo.setPrintCommentaryFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), examMasterVO.getPrintCommentaryFile()));
                    }
                }
            }
        }
        return new ApiPagingResultDTO(totalCnt, resultList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getUserExamLogList(int userKey, int sPage, int listLimit) {
        int resultCode = OK.value();

        int totalCnt = 0;
        List<ExamLogVO> logList = new ArrayList<>();
        int startNumber = getPagingStartNumber(sPage, listLimit);

        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            totalCnt = examMapper.selectUserExamApplyLogListCount(userKey);
            logList = examMapper.selectUserExamApplyLogList(userKey, startNumber, listLimit);
            if (logList.size() > 0) {
                for (ExamLogVO logVO : logList) {
                    String examType = "";
                    if (logVO.getIsOnOff() == 0) {
                        if (logVO.getIsGichul() == 1 && logVO.getIsRealFree() == 0) examType = "기출문제";
                        else examType = "주간모의고사";
                    } else if (logVO.getIsOnOff() == 2) {
                        examType = "학원모의고사(온)";
                    } else if (logVO.getIsOnOff() == 3) {
                        examType = "학원모의고사(오프)";
                    }
                    logVO.setExamType(examType);
                }
            }
        }
        return new ApiPagingResultDTO(totalCnt, logList, resultCode);
    }
}
