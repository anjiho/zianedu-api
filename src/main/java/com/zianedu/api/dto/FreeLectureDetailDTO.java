package com.zianedu.api.dto;

import com.zianedu.api.vo.FreeLectureVO;
import com.zianedu.api.vo.TLecCurriVO;
import lombok.Data;

import java.util.List;

@Data
public class FreeLectureDetailDTO {

    private FreeLectureVO freeLectureInfo;

    private List<TLecCurriVO> freeLectureList;

    public FreeLectureDetailDTO() {}

    public FreeLectureDetailDTO(FreeLectureVO freeLectureInfo, List<TLecCurriVO> freeLectureList) {
        this.freeLectureInfo = freeLectureInfo;
        this.freeLectureList = freeLectureList;
    }
}
