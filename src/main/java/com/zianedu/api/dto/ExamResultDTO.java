package com.zianedu.api.dto;

import lombok.Data;

@Data
public class ExamResultDTO {

    private int examUserKey;

    private int userKey;

    private int examSbjUserKey;

    private int examQuestionBankKey;

    private int userAnswer;

    private int score;

}
