package com.zianedu.api.dto;

import com.zianedu.api.vo.LectureApplyTeacherTypeVO;
import com.zianedu.api.vo.TeacherHomeLectureVO;
import lombok.Data;

import java.util.List;

@Data
public class LectureApplyProductDTO {

    private String subjectName;

    private int subjectKey;

    private List<LectureApplyTeacherTypeVO> teacherTypeInfo;

    //private List<TeacherHomeLectureVO> lectureInfo;
}
