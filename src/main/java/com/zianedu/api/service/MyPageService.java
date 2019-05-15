package com.zianedu.api.service;

import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.repository.LectureProgressRateRepository;
import com.zianedu.api.vo.AcademySignUpVO;
import com.zianedu.api.vo.OnlineSignUpVO;
import com.zianedu.api.vo.OnlineSubjectListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class MyPageService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private LectureProgressRateRepository lectureProgressRateRepository;

    /**
     * 내 강의실 > 학원수강내역
     * @param userKey
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultListDTO getUserAcademySignUpList(int userKey) {
        int resultCode = OK.value();
        List<AcademySignUpVO>resultList = new ArrayList<>();

        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            resultList = productMapper.selectAcademySignUp(userKey);
        }
        return new ApiResultListDTO(resultList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getUserPromotionOnlineSignUpList(int userKey, String deviceType) {
        int resultCode = OK.value();
        List<OnlineSignUpVO>resultList = new ArrayList<>();

        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            resultList = productMapper.selectPromotionOnlineSignUp(userKey, deviceType);

            for (OnlineSignUpVO onlineSignUpVO : resultList) {
                if (onlineSignUpVO.getOnlineSignUpSubjectList() != null && onlineSignUpVO.getOnlineSignUpSubjectList().size() > 0) {
                    //강의진도률 주입
                    lectureProgressRateRepository.injectLectureProgressRateAny(onlineSignUpVO.getOnlineSignUpSubjectList());
                }
            }
        }
        return new ApiResultListDTO(resultList, resultCode);
    }


}
