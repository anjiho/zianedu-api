package com.zianedu.api.dto;

import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.utils.ZianApiUtils;
import lombok.Data;

@Data
public class ApiResultStringDTO {

    private String resultStr;

    private int resultCode;

    private String resultMsg;

    public ApiResultStringDTO(){}

    public ApiResultStringDTO(String resultStr, int code) {
        this.resultStr = resultStr;
        this.resultCode = code;
        this.resultMsg = ZianErrCode.getZianErrorMessage(code) == null ? ZianApiUtils.SUCCESS : ZianErrCode.getZianErrorMessage(code);
    }
}
