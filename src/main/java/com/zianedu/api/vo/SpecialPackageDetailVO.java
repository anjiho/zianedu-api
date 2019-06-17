package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class SpecialPackageDetailVO {

    private SpecialPackageProductVO specialPackageProductInfo;

    private List<TeacherHomeLectureVO> lectureList;

    public SpecialPackageDetailVO(){}

    public SpecialPackageDetailVO(SpecialPackageProductVO specialPackageProductInfo, List<TeacherHomeLectureVO> lectureList){
        this.specialPackageProductInfo = specialPackageProductInfo;
        this.lectureList = lectureList;
    }
}
