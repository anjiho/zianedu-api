package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class TeacherIntroduceVO {

    private String ctgName;

    private int ctgKey;

    List<TeacherIntroduceListVO> teacherIntroduceList;

}
