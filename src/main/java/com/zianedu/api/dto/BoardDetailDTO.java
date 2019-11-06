package com.zianedu.api.dto;

import com.zianedu.api.vo.BoardDetailVO;
import com.zianedu.api.vo.CommentListVO;
import com.zianedu.api.vo.PrevNextVO;
import lombok.Data;

import java.util.List;

@Data
public class BoardDetailDTO {

    private BoardDetailVO boardDetailInfo;

    private List<CommentListVO> commentInfo;

    private PrevNextVO prevNextInfo;

    public BoardDetailDTO(){}

    public BoardDetailDTO(BoardDetailVO boardDetailInfo, List<CommentListVO> commentInfo, PrevNextVO prevNextInfo) {
        this.boardDetailInfo = boardDetailInfo;
        this.commentInfo = commentInfo;
        this.prevNextInfo = prevNextInfo;
    }
}
