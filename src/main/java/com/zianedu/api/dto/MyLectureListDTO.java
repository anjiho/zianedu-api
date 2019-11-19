package com.zianedu.api.dto;

import com.zianedu.api.vo.TLecCurriVO;
import lombok.Data;

import java.util.List;

@Data
public class MyLectureListDTO {

    private int totalCnt;

    private List<TLecCurriVO> lectureList;

    public MyLectureListDTO() {}

    public MyLectureListDTO(int totalCnt, List<TLecCurriVO> lectureList) {
        this.totalCnt = totalCnt;
        this.lectureList = lectureList;
    }
}
