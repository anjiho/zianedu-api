package com.zianedu.api.dto;

import com.zianedu.api.vo.PrevNextVO;
import com.zianedu.api.vo.ReferenceRoomDetailVO;
import lombok.Data;

import java.util.List;

@Data
public class ReferenceRoomDetailDTO {

    private ReferenceRoomDetailVO referenceRoomDetailInfo;

    private List<PrevNextVO> prevNextBbsList;

    public ReferenceRoomDetailDTO(){}

    public ReferenceRoomDetailDTO(ReferenceRoomDetailVO referenceRoomDetailInfo, List<PrevNextVO> prevNextBbsList) {
        this.referenceRoomDetailInfo = referenceRoomDetailInfo;
        this.prevNextBbsList = prevNextBbsList;
    }
}
