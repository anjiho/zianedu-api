package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class LectureApplyTeacherTypeVO {

    private int teacherKey;

    private String teacherName;

    private int subjectMenuKey;

    private int subjectCtgKey;

    private String imageTeacherList;

    private int theoryCnt;

    private int problemSolveCnt;

    private int singleSpecialCnt;

    private int prepareNoteCnt;

    private int mockExamCnt;

    private List<TeacherHomeLectureVO> videoLectureInfo;
}
