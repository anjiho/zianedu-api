package com.zianedu.api.dto;

import com.zianedu.api.vo.PointListVO;
import com.zianedu.api.vo.UserPointInfoVO;
import lombok.Data;

import java.util.List;

@Data
public class PointInfoDTO {

    private UserPointInfoVO userPointInfo;

    private List<PointListVO> pointList;

    public PointInfoDTO(){}

    public PointInfoDTO(UserPointInfoVO userPointInfo, List<PointListVO> pointList) {
        this.userPointInfo = userPointInfo;
        this.pointList = pointList;
    }
}
