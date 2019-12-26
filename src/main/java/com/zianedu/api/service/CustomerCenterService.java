package com.zianedu.api.service;

import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.mapper.BoardMapper;
import com.zianedu.api.vo.TConsultReserveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.HttpStatus.OK;

@Service
public class CustomerCenterService {

    @Autowired
    private BoardMapper boardMapper;

    //상담예약하기
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO reserveConsult(String reserveDate, int reserveTimeKey, int reserveType, int reserveLocation,
                                           String userName, String mobileNumber, String emailAddress, int ctgKey1, int ctgKey2,
                                           int ctgKey3, String reserveContents) {
        int resultCode = OK.value();

        if ("".equals(reserveDate)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            int consultReserveCount = boardMapper.selectConsultReserveCount(reserveDate, reserveTimeKey, reserveLocation);
            if (consultReserveCount == 0) {
                TConsultReserveVO reserveVO = new TConsultReserveVO(
                        reserveDate, reserveTimeKey, reserveType, reserveLocation, userName, mobileNumber,
                        emailAddress, ctgKey1, ctgKey2, ctgKey3, reserveContents
                );
                boardMapper.insertTConsultReserve(reserveVO);
            } else {
                resultCode = ZianErrCode.CUSTOM_CONSULT_RESERVE_LIMIT.code();
            }
        }
        return new ApiResultCodeDTO("RESERVE_TIME_KEY", reserveTimeKey, resultCode);
    }
}
