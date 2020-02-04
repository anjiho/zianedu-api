package com.zianedu.api.dto;

import com.zianedu.api.vo.TBankSubjectExamLinkVO;
import com.zianedu.api.vo.TExamMasterVO;
import lombok.Data;

import java.util.List;

@Data
public class ExamGateDTO {

    private TExamMasterVO examBasicInfo;

    //private List<TBankSubjectExamLinkVO> examSubjectInfo;

    private String examSubjectName;

    public ExamGateDTO(){}

    public ExamGateDTO(TExamMasterVO examBasicInfo, String examSubjectName) {
        this.examBasicInfo = examBasicInfo;
        this.examSubjectName = examSubjectName;
    }
}
