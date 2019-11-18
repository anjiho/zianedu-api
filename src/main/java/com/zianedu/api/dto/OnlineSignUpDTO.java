package com.zianedu.api.dto;

import com.zianedu.api.vo.OnlineSignUpVO;
import lombok.Data;

import java.util.List;

@Data
public class OnlineSignUpDTO {

    private List<SubjectDTO> subjectInfo;

    private List<OnlineSignUpVO> onlineSignUpList;
}
