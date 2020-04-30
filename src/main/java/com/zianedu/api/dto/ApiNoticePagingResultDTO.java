package com.zianedu.api.dto;

import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.utils.ZianApiUtils;
import lombok.Data;

import java.util.List;

@Data
public class ApiNoticePagingResultDTO {

    private int cnt;

    private int noticeCnt;

    private List<?> result;

    private int resultCode;

    private String resultMsg;

    public ApiNoticePagingResultDTO(){}

    public ApiNoticePagingResultDTO(int cnt, List<?>list, int code) {
        this.cnt = cnt;
        this.result = list;
        this.resultCode = code;
        this.resultMsg = ZianErrCode.getZianErrorMessage(code) == null ? ZianApiUtils.SUCCESS : ZianErrCode.getZianErrorMessage(code);
    }
}
