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
    public ApiResultCodeDTO reserveConsult(TConsultReserveVO tConsultReserveVO) {
        int resultCode = OK.value();

        if (tConsultReserveVO == null) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            int consultReserveCount = boardMapper.selectConsultReserveCount(
                    tConsultReserveVO.getReserveDate(), tConsultReserveVO.getReserveTimeKey(), tConsultReserveVO.getReserveLocation());
            if (consultReserveCount == 0) {
                TConsultReserveVO reserveVO = new TConsultReserveVO(tConsultReserveVO);
                boardMapper.insertTConsultReserve(reserveVO);
            } else {
                resultCode = ZianErrCode.CUSTOM_CONSULT_RESERVE_LIMIT.code();
            }
        }
        return new ApiResultCodeDTO("RESERVE_TIME_KEY", tConsultReserveVO.getReserveTimeKey(), resultCode);
    }
}
