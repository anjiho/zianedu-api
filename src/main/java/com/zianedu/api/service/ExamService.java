package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.datasource.ZianCoreCode;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.AchievementManagementDTO;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.mapper.ExamMapper;
import com.zianedu.api.utils.DateUtils;
import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.utils.StringUtils;
import com.zianedu.api.utils.ZianUtils;
import com.zianedu.api.vo.AchievementTopInfoVO;
import com.zianedu.api.vo.ExamSubjectStaticsVO;
import com.zianedu.api.vo.ExamSubjectTotalVO;
import com.zianedu.api.vo.TExamUserVO;
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
                }
                //평균값 주입
                examSubjectTotalVO.setStaticsAnswerCnt((staticsAnswerCnt / examSubjectStaticsList.size()));
                examSubjectTotalVO.setStaticsAnswerScore((staticsAnswerScore / examSubjectStaticsList.size()));
                examSubjectTotalVO.setStaticsUserGrade((staticsUserGrade / examSubjectStaticsList.size()));
                examSubjectTotalVO.setStaticsTotalAnswerCnt((staticsTotalAnswerCnt / examSubjectStaticsList.size()));

            }
        }
        AchievementManagementDTO achievementManagementDTO = new AchievementManagementDTO(achievementTopInfoVO, examSubjectStaticsList, examSubjectTotalVO);
        return new ApiResultObjectDTO(achievementManagementDTO, resultCode);
    }

}
