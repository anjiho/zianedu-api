package com.zianedu.api.vo;

import lombok.Data;

@Data
public class ExamSubjectTotalVO {

    private int staticsAnswerCnt;

    private int staticsAnswerScore;

    private int staticsUserGrade;

    private int staticsTotalAnswerCnt;

    public ExamSubjectTotalVO(){}

    public ExamSubjectTotalVO(int staticsAnswerCnt, int staticsAnswerScore, int staticsUserGrade, int staticsTotalAnswerCnt) {
        this.staticsAnswerCnt = staticsAnswerCnt;
        this.staticsAnswerScore = staticsAnswerScore;
        this.staticsUserGrade = staticsUserGrade;
        this.staticsTotalAnswerCnt = staticsTotalAnswerCnt;
    }
}
