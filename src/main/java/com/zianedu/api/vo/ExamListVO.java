package com.zianedu.api.vo;

import lombok.Data;

@Data
public class ExamListVO {

    private int num;

    private int examUserKey;

    private int examSbjUserKey;

    private int examQuestionBankKey;

    private String questionImage;

    private String commentaryImage;

    private int answer;
}
