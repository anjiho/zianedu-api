package com.zianedu.api.dto;

import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.utils.ZianApiUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@Data
public class ApiResultObjectDTO {

    private Object result;

    private int resultCode;

    private String resultMsg;

    public ApiResultObjectDTO(){}

    public ApiResultObjectDTO(Object result, int code) {
        this.result = result;
        this.resultCode = code;
        this.resultMsg = ZianErrCode.getZianErrorMessage(code) == null ? ZianApiUtils.SUCCESS : ZianErrCode.getZianErrorMessage(code);
    }
}
