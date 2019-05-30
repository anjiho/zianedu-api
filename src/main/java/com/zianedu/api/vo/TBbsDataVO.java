package com.zianedu.api.vo;

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

    public TBbsDataVO() {}

//    public TBbsDataVO(int bbsMasterKey, int bbsParentKey, String title, int teacherKey, int ctgKey02, String contents) {
//        this.bbsMasterKey = bbsMasterKey;
//        this.bbsParentKey = bbsParentKey;
//        this.indate = Util.returnNow();
//        this.title = title;
//        this.writeUserKey = UserSession.get() == null ? 5 : UserSession.getUserKey();
//        this.ctgKey = teacherKey;
//        this.ctgKey02 = ctgKey02;
//        this.readCount = 0;
//        this.isNotice = 0;
//        this.pwd = "";
//        this.contents = contents;
//        this.bbsCustomKey = 0;
//    }

}
