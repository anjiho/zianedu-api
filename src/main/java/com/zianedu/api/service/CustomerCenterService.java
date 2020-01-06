package com.zianedu.api.service;

import com.zianedu.api.define.datasource.ConsultReserveTimeType;
import com.zianedu.api.define.datasource.ConsultReserveType;
import com.zianedu.api.define.datasource.ConsultStatusType;
import com.zianedu.api.define.datasource.ZianAcademyType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.*;
import com.zianedu.api.mapper.BoardMapper;
import com.zianedu.api.mapper.UserMapper;
import com.zianedu.api.utils.PagingSupport;
import com.zianedu.api.utils.StringUtils;
import com.zianedu.api.vo.TBbsDataVO;
import com.zianedu.api.vo.TConsultReserveVO;
import com.zianedu.api.vo.TUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class CustomerCenterService extends PagingSupport {

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private UserMapper userMapper;

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

    @Transactional(readOnly = true)
    public ApiResultListDTO getConsultTimeList(String reserveDate, int reserveLocation) {
        int resultCode = OK.value();

        List<ConsultTimeDTO> consultTimeList = ConsultReserveTimeType.getConsultTimeList();
        if ("".equals(reserveDate) && reserveLocation == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            List<TConsultReserveVO> reservedTimeList = boardMapper.selectConsultReserveTimeList(reserveDate, reserveLocation);
            for (ConsultTimeDTO timeDTO : consultTimeList) {
                for (TConsultReserveVO reserveVO : reservedTimeList) {
                    if (reserveVO.getReserveTimeKey() == timeDTO.getReserveTimeKey()) {
                        timeDTO.setReservedYn(true);
                    }
                }
            }
        }
        return new ApiResultListDTO(consultTimeList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getOftenQuestionDetailList(int ctgKey) {
        int resultCode = OK.value();

        List<TBbsDataVO> oftenQuestionList = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            oftenQuestionList = boardMapper.selectTBbsDataByCtgKey(ctgKey);
        }
        return new ApiResultListDTO(oftenQuestionList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getConsultList(int userKey, String reserveDate, int sPage, int listLimit) {
        int resultCode = OK.value();

        int consultListCount = 0;
        List<ConsultReserveListDTO> consultReserveList = new ArrayList<>();
        int startNumber = getPagingStartNumber(sPage, listLimit);

        if (userKey == 0 && "".equals(reserveDate)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TUserVO userInfo = userMapper.selectUserInfoByUserKey(userKey);
            if (userInfo.getAuthority() == 0) {
                userKey = 0;
            }
            consultListCount = boardMapper.selectConsultReserveListCount(userKey, reserveDate);
            consultReserveList = boardMapper.selectConsultReserveList(userKey, reserveDate, startNumber, listLimit);
            if (consultReserveList.size() > 0) {
                for (ConsultReserveListDTO dto : consultReserveList) {
                    dto.setReserveTimeName(ConsultReserveTimeType.getConsultReserveTimeName(dto.getReserveTimeKey()));
                    dto.setReserveTypeName(ConsultReserveType.getConsultReserveTypeName(dto.getReserveType()));
                    dto.setReserveLocationName(ZianAcademyType.getZianAcademyName(dto.getReserveLocation()));
                    dto.setConsultStatusName(ConsultStatusType.getConsultStatusTypeName(dto.getConsultStatus()));
                }
            }
        }
        return new ApiPagingResultDTO(consultListCount, consultReserveList, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO changeConsultReserveStatus(Integer[]idxs, int reserveStatusType) {
        int resultCode = OK.value();

        if (idxs.length == 0 && reserveStatusType == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            List<Integer>idxList = StringUtils.integerArrayToArrayList(idxs);
            boardMapper.updateTConsultReserveStatus(idxList, reserveStatusType);
        }
        return new ApiResultCodeDTO("RESULT", "SUCCESS", resultCode);
    }
}
