package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TExamMasterVO {

    private int examKey;

    private int cKey;
    //시험명
    private String name;
    //등록일
    private String indate;
    //분류
    private int classCtgKey;
    //과목
    private int subjectCtgKey;
    //선택과목수
    private int selectSubjectCount;
    //시험신청기간 시작일
    private String acceptStartDate;
    //시험신청기간 종료일
    private String acceptEndDate;
    //온라인 시험기간 시작일
    private String onlineStartDate;
    //온라인 시험기간 종료일
    private String onlineEndDate;
    //온라인 시험시간
    private int onlineTime;
    //온라인 시험일
    private String offlineDate;
    //오프라인 모의고사 시간 (표시용)
    private String offlineTimePeriod;
    //오프라인 모의고사 시험 장소 (표시용)
    private String offlineTimePlace;
    // 답안수
    private int answerCount;
    // 과목당 문항수
    private int questionCount;
    // 시험지 파일명
    private String printQuestionFile;
    // 해설지 파일명
    private String printCommentaryFile;
    //통계에서 전체 석차 노출 여부(랭크 보여주기)
    private int isShowStaticTotalRank;
    //통계에서 전체 평균 노출 여부(평균 보여주기)
    private int isShowStaticTotalAvg;
    // 파일 노출여부(파일오픈)
    private int isShowFiles;
    //급수
    private int classGroupCtgKey;
    //시험년(출제년도)
    private int examYear;

    private int unitCtgKey = 0;
    //출제구분 Category Key, 단원별 모의고사에 연결할때 쓰임
    private int divisionCtgKey;

    private int refKey;
    //주간모의고사 on, off
    private int isRealFree;
    //기출문제 on, off
    private int isGichul;

    private String className;
}
