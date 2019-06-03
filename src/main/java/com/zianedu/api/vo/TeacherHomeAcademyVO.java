package com.zianedu.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TeacherHomeAcademyVO {

    private int stepCtgKey;

    private String ctgName;

    @JsonProperty(value = "teacherAcademyList")
    private List<TeacherHomeAcademyListVO> teacherAcademyList;

}
