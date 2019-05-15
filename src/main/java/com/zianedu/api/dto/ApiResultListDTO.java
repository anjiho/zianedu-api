package com.zianedu.api.dto;

import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.utils.ZianApiUtils;
import lombok.Data;

import java.util.List;

@Data
public class ApiResultListDTO {

    private List<?> result;

    private int resultCode;

    private String resultMsg;

    public ApiResultListDTO(){}

    public ApiResultListDTO(List<?>list, int code) {
        this.result = list;
        this.resultCode = code;
        this.resultMsg = ZianErrCode.getZianErrorMessage(code) == null ? ZianApiUtils.SUCCESS : ZianErrCode.getZianErrorMessage(code);
    }
}
