package com.zianedu.api.vo;

import com.zianedu.api.utils.ZianUtils;
import lombok.Data;

@Data
public class TExamUserVO {

    private int examKey;

    private String goodsName;

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

    private int isOnline;

    public TExamUserVO(){}

    public TExamUserVO(int examKey, int userKey, String serial, String onOff) {
        this.examKey = examKey;
        this.userKey = userKey;
        this.serial = serial;
        this.isOnline = "1".equals(onOff) ? 0 : 1;
    }

    public TExamUserVO(int examUserKey, int isComplete, int playTime, int isStart) {
        this.examUserKey = examUserKey;
        this.iscomplete = isComplete;
        this.playTime = playTime;
        this.isstart = isStart;
    }
}
