package com.zianedu.api.dto;

import com.zianedu.api.vo.PointListVO;
import lombok.Data;

import java.util.List;

@Data
public class PointInfoDTO {

    private String remainPointName;

    private List<PointListVO> pointList;

    public PointInfoDTO(){}

    public PointInfoDTO(String remainPointName, List<PointListVO> pointList) {
        this.remainPointName = remainPointName;
        this.pointList = pointList;
    }
}
