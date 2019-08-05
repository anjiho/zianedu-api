package com.zianedu.api.dto;

import com.zianedu.api.vo.TCategoryVO;
import lombok.Data;

import java.util.List;

@Data
public class LeftMenuSubDepthListDTO {

    private int ctgKey;

    private String menuName;

    private List<TCategoryVO> subMenuList;
}
