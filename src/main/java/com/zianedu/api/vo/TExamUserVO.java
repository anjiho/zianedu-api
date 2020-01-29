package com.zianedu.api.vo;

import com.zianedu.api.utils.ZianUtils;
import lombok.Data;

@Data
public class TExamUserVO {

    private int examKey;

    private String name;

    private String printQuestionFile;

    private String printCommentaryFile;

    private String printQuestionFileUrl;

    private String printCommentaryFileUrl;

    private int iscomplete;

    private String classCtgName;

    private String subjectCtgName;

    private String groupCtgName;

    private int examUserKey;

    private String acceptStartDate;

    private String acceptEndDate;

    private String serial;

    private String userName;

    private int userKey;

    private int playTime;

    private int isstart;

    public TExamUserVO(){}

    public TExamUserVO(int examKey, int userKey, String serial) {
        this.examKey = examKey;
        this.userKey = userKey;
        this.serial = serial;
    }

    public TExamUserVO(int examUserKey, int isComplete, int playTime, int isStart) {
        this.examUserKey = examUserKey;
        this.iscomplete = isComplete;
        this.playTime = playTime;
        this.isstart = isStart;
    }
}
