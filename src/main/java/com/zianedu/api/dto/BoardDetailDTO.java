package com.zianedu.api.dto;

import com.zianedu.api.vo.BoardDetailVO;
import com.zianedu.api.vo.CommentListVO;
import lombok.Data;

import java.util.List;

@Data
public class BoardDetailDTO {

    private BoardDetailVO boardDetailInfo;

    private List<CommentListVO> commentInfo;

    public BoardDetailDTO(){}

    public BoardDetailDTO(BoardDetailVO boardDetailInfo, List<CommentListVO> commentInfo) {
        this.boardDetailInfo = boardDetailInfo;
        this.commentInfo = commentInfo;
    }
}
