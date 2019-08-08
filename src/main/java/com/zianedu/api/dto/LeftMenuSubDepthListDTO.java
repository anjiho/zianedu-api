package com.zianedu.api.dto;

import com.zianedu.api.vo.TCategoryVO;
import com.zianedu.api.vo.ZianPassSubMenuVO;
import lombok.Data;

import java.util.List;

@Data
public class LeftMenuSubDepthListDTO {

    private int ctgKey;

    private String menuName;

    private int isSingle;

    private List<TCategoryVO> subMenuList;

    private List<ZianPassSubMenuVO> zianPassSubMenuList;
}
