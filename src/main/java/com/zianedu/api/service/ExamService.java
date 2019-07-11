package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.mapper.ExamMapper;
import com.zianedu.api.utils.FileUtil;
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
}
