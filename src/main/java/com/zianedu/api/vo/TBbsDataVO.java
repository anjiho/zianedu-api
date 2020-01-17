package com.zianedu.api.vo;

import com.zianedu.api.utils.Util;
import lombok.Data;

@Data
public class TBbsDataVO {

    private int bbsKey;

    private int bbsMasterKey;

    private int bbsParentKey;

    private String indate;

    private String title;

    private int writeUserKey;

    private int ctgKey;

    private int ctgKey02;

    private int readCount;

    private int isShow;

    private int isNotice;

    private String pwd;

    private String contents;

    private int docType;

    private String imageFile1;

    private String imageFile2;

    private String imageFile3;

    private String file1;

    private String file2;

    private String file3;

    private String ip;

    private int isShowImageFile;

    private int bbsOldKey;

    private int isHaveAnswer;

    private int bbsCustomKey;

    private String userName;

    private String youtubeHtml;

    private int gKey;

    private String successSubject;

    private String lectureSubject;

    public TBbsDataVO() {}

    public TBbsDataVO(int bbsMasterKey, int userKey, String title, String contents, int isSecret, int ctgKey,
                      String youtubeHtml, int gKey, String successSubject, String lectureSubject) {
        this.bbsMasterKey = bbsMasterKey;
        this.bbsParentKey = 0;
        this.title = Util.isNullValue(title, "");
        this.writeUserKey = userKey;
        this.ctgKey = ctgKey;
        this.ctgKey02 = 0;
        this.readCount = 0;
        this.isShow = 0;
        this.isNotice = 0;
        this.pwd = String.valueOf(isSecret);
        this.contents = Util.isNullValue(contents, "");
        this.isHaveAnswer = 0;
        this.bbsCustomKey = 0;
        this.youtubeHtml = youtubeHtml;
        this.gKey = gKey;
        this.successSubject = successSubject;
        this.lectureSubject = lectureSubject;
    }

    public TBbsDataVO(int bbsKey, String title, String contents, int isSecret) {
        this.bbsKey = bbsKey;
        this.title = Util.isNullValue(title, "");
        this.pwd = String.valueOf(isSecret);
        this.contents = Util.isNullValue(contents, "");
    }

    public TBbsDataVO(int bbsMasterKey, int teacherKey, int userKey, String title, String contents, int isSecret, int isNotice) {
        this.bbsMasterKey = bbsMasterKey;
        this.bbsParentKey = 0;
        this.title = Util.isNullValue(title, "");
        this.writeUserKey = userKey;
        this.ctgKey = teacherKey;
        this.ctgKey02 = 0;
        this.readCount = 0;
        this.isShow = 0;
        this.isNotice = isNotice;
        this.pwd = String.valueOf(isSecret);
        this.contents = Util.isNullValue(contents, "");
        this.isHaveAnswer = 0;
        this.bbsCustomKey = 0;
        this.youtubeHtml = "";
        this.gKey = 0;
        this.successSubject = "";
        this.lectureSubject = "";
    }

    public TBbsDataVO(int bbsMasterKey, int bbsParentKey, int teacherKey, int userKey, String title, String contents, int isSecret, int isNotice) {
        this.bbsMasterKey = bbsMasterKey;
        this.bbsParentKey = bbsParentKey;
        this.title = Util.isNullValue(title, "");
        this.writeUserKey = userKey;
        this.ctgKey = teacherKey;
        this.ctgKey02 = 0;
        this.readCount = 0;
        this.isShow = 0;
        this.isNotice = isNotice;
        this.pwd = String.valueOf(isSecret);
        this.contents = Util.isNullValue(contents, "");
        this.isHaveAnswer = 0;
        this.bbsCustomKey = 0;
    }

    public TBbsDataVO(int bbsKey, String title, String contents, int isSecret, String youtubeHtml, int gKey, String successSubject, String lectureSubject) {
        this.bbsKey = bbsKey;
        this.title = Util.isNullValue(title, "");
        this.pwd = String.valueOf(isSecret);
        this.contents = Util.isNullValue(contents, "");
        this.youtubeHtml = youtubeHtml;
        this.gKey = gKey;
        this.successSubject = successSubject;
        this.lectureSubject = lectureSubject;
    }

    public TBbsDataVO(int bbsKey, int faqTypeKey, String title, String contents) {
        this.bbsKey = bbsKey;
        this.ctgKey = faqTypeKey;
        this.title = Util.isNullValue(title, "");
        this.contents = Util.isNullValue(contents, "");
        this.pwd = "";
    }


}
