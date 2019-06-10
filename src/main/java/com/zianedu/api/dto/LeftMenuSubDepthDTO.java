package com.zianedu.api.dto;

import com.zianedu.api.vo.TCategoryVO;
import lombok.Data;

import java.util.List;

@Data
public class LeftMenuSubDepthDTO {

    private List<TCategoryVO> firstMenu;

    private List<TCategoryVO> secondMenu;
}
