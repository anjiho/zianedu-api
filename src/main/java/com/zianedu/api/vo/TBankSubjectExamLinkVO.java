package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TBankSubjectExamLinkVO {

    private int bankSubjectExamLinkKey;

    private int cKey;

    private int examKey;

    private int examQuestionBankSubjectKey;

    private int required;

    private String name;

    private String ctgName;

    private int questionNumber;

    public TBankSubjectExamLinkVO(){}

}
