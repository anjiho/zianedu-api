package com.zianedu.api.dto;

import com.zianedu.api.vo.CommentListVO;
import com.zianedu.api.vo.PrevNextVO;
import com.zianedu.api.vo.ReferenceRoomDetailVO;
import lombok.Data;

import java.util.List;

@Data
public class ReferenceRoomDetailDTO {

    private ReferenceRoomDetailVO referenceRoomDetailInfo;

    private List<PrevNextVO> prevNextBbsList;

    private List<CommentListVO> commentInfo;

    public ReferenceRoomDetailDTO(){}

    public ReferenceRoomDetailDTO(ReferenceRoomDetailVO referenceRoomDetailInfo, List<PrevNextVO> prevNextBbsList, List<CommentListVO> commentInfo) {
        this.referenceRoomDetailInfo = referenceRoomDetailInfo;
        this.prevNextBbsList = prevNextBbsList;
        this.commentInfo = commentInfo;
    }
}
