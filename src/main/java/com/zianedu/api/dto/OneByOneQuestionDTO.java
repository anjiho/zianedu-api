package com.zianedu.api.dto;

import com.zianedu.api.vo.ReferenceRoomVO;
import lombok.Data;

import java.util.List;

@Data
public class OneByOneQuestionDTO {

    private ReferenceRoomVO questionList;

    private List<ReferenceRoomVO> commentList;
}
