package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class AcademyProductDTO {

    private AcademyLectureDetailVO academyLectureDetailInfo;

    private List<TeacherInfoVO> teacherInfo;

    public AcademyProductDTO(){}

    public AcademyProductDTO(AcademyLectureDetailVO academyLectureDetailInfo, List<TeacherInfoVO> teacherInfo) {
        this.academyLectureDetailInfo = academyLectureDetailInfo;
        this.teacherInfo = teacherInfo;
    }
}
