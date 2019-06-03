package com.zianedu.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TeacherHomeLectureVO {

    private int stepCtgKey;

    private String ctgName;

    @JsonProperty(value = "teacherLectureList")
    private List<TeacherHomeLectureListVO> teacherLectureList;

}
