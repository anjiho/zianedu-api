package com.zianedu.api.dto;

import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.utils.ZianApiUtils;
import lombok.Data;

@Data
public class ApiResultCodeDTO {

    private String keyCd;

    private Object keyValue;

    private int resultCode;

    private String resultMsg;

    public ApiResultCodeDTO(){}

    public ApiResultCodeDTO(String keyCd, Object keyValue, int code) {
        this.keyCd = keyCd;
        this.keyValue = keyValue;
        this.resultCode = code;
        this.resultMsg = ZianErrCode.getZianErrorMessage(code) == null ? ZianApiUtils.SUCCESS : ZianErrCode.getZianErrorMessage(code);
    }

}
