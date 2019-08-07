package com.zianedu.api.dto;

import com.zianedu.api.vo.ZianPassProductListVO;
import lombok.Data;

import java.util.List;

@Data
public class ZianPassProductDTO {

    private int affiliationCtgKey;

    private String affiliationName;

    private List<ZianPassProductListVO> zianPassProductList;
}
