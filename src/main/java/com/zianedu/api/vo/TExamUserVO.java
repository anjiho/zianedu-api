package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TExamUserVO {

    private int examKey;

    private String name;

    private String printQuestionFile;

    private String printCommentaryFile;

    private String printQuestionFileUrl;

    private String printCommentaryFileUrl;

    private int isComplete;

    private String classCtgName;

    private String subjectCtgName;

    private String groupCtgName;

    private int examUserKey;

    private String acceptStartDate;

    private String acceptEndDate;

    private String serial;

    private String userName;
}
