package com.zianedu.api.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zianedu.api.dto.SubjectDTO;
import lombok.Data;

import java.util.List;

@Data
public class OnlineSignUpVO {

//    @JsonProperty(value = "onlineSubjectInfoList")
//    private List<OnlineSubjectInfoVO> onlineSubjectInfoList;

    private int jKey;

    private int stepCtgKey;

    private String ctgName;

    private List<SubjectDTO> subjectList;

    @JsonProperty(value = "onlineSignUpSubjectList")
    private List<OnlineSubjectListVO> onlineSignUpSubjectList;

}
