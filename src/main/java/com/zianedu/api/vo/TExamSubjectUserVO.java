package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TExamSubjectUserVO {

    private int examSbjUserKey;

    private int examUserKey;

    private int examSbjKey;

    private int examKey;

    private int bankSubjectExamLinkKey;

    private int examQuesBankSubjectKey;

    private int userKey;

    private String indate;

    private int requiredType;

    private int isSelected;

    private String subjectName;

    public TExamSubjectUserVO(){}

    public TExamSubjectUserVO(int examUserKey, int examKey, int bankSubjectExamLinkKey, int examQuesBankSubjectKey, int userKey) {
        this.examUserKey = examUserKey;
        this.examKey = examKey;
        this.bankSubjectExamLinkKey = bankSubjectExamLinkKey;
        this.examQuesBankSubjectKey = examQuesBankSubjectKey;
        this.userKey = userKey;
    }
}
