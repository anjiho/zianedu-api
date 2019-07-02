package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.datasource.GoodsKindType;
import com.zianedu.api.define.datasource.LectureStatusType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiPagingResultDTO;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.repository.LectureProgressRateRepository;
import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.utils.PagingSupport;
import com.zianedu.api.utils.StringUtils;
import com.zianedu.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class MyPageService extends PagingSupport {

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

    @Transactional(readOnly = true)
    public ApiResultListDTO getUserVideoOnlineSignUpList(int userKey, String deviceType) {
        int resultCode = OK.value();
        List<OnlineSignUpVO>resultList = new ArrayList<>();

        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            resultList = productMapper.selectVideoOnlineSignUp(userKey, deviceType);

            for (OnlineSignUpVO onlineSignUpVO : resultList) {
                if (onlineSignUpVO.getOnlineSignUpSubjectList() != null && onlineSignUpVO.getOnlineSignUpSubjectList().size() > 0) {
                    //강의진도률 주입
                    lectureProgressRateRepository.injectLectureProgressRateAny(onlineSignUpVO.getOnlineSignUpSubjectList());
                }
            }
        }
        return new ApiResultListDTO(resultList, resultCode);
    }

    /**
     * 내 강의실 > 수강중인강좌 > 수강중인강좌 상세정보
     * @param jLecKey
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultObjectDTO getUserOnlineLectureDetailInfo(int jLecKey) {
        int resultCode = OK.value();
        OnlineLectureDetailVO onlineLectureDetailVO = new OnlineLectureDetailVO();

        if (jLecKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            onlineLectureDetailVO = productMapper.selectOnlineLectureDetailInfo(jLecKey);

            if (onlineLectureDetailVO != null) {
                //강의진도률 주입
                Integer progressRate = productMapper.selectOnlineLectureProgressRate(jLecKey);
                onlineLectureDetailVO.setProgressRate((progressRate) + "%");
                //
                onlineLectureDetailVO.setTeacherImgUrl(
                        FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), onlineLectureDetailVO.getImageTeacherList())
                );
                onlineLectureDetailVO.setLectureStatusName(
                        LectureStatusType.getLectureStatusName(onlineLectureDetailVO.getStatus())
                );
                onlineLectureDetailVO.setLectureCntName(onlineLectureDetailVO.getLectureCnt() + "강");
                onlineLectureDetailVO.setTeacherName(onlineLectureDetailVO.getTeacherName() + "교수님");
            }
        }
        return new ApiResultObjectDTO(onlineLectureDetailVO, resultCode);
    }

    /**
     * 내 강의실 > 수강중인강좌 > 수강중인강좌 상세정보 > 강좌목차
     * @param jLecKey
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultListDTO getUserOnlineLectureSubjectList(int jLecKey) {
        int resultCode = OK.value();
        List<OnlineLectureSubjectListVO> resultList = new ArrayList<>();

        if (jLecKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            resultList = productMapper.selectOnlineLectureSubjectList(jLecKey);
            for (OnlineLectureSubjectListVO lectureSubjectListVO : resultList) {
                lectureSubjectListVO.setTime(lectureSubjectListVO.getRemainTime() + " / " + lectureSubjectListVO.getVodTime());
                lectureSubjectListVO.setProgressRateName(lectureSubjectListVO.getProgressRate() + "%");
            }
        }
        return new ApiResultListDTO(resultList, resultCode);
    }

    /**
     * 내 강의실 > 수강중인강좌 > 수강중인강좌 상세정보 > 회원리뷰
     * @param jLecKey
     * @param sPage
     * @param listLimit
     * @return
     */
    @Transactional(readOnly = true)
    public ApiPagingResultDTO getGoodsReviewList(int jLecKey, int sPage, int listLimit) {
        int resultCode = OK.value();

        int startNumber = PagingSupport.getPagingStartNumber(sPage, listLimit);
        int goodsReviewCnt = 0;
        List<TGoodsReviewVO> goodsReviewList = new ArrayList<>();

        if (jLecKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            goodsReviewCnt = productMapper.selectGoodsReviewListCount(jLecKey);
            goodsReviewList = productMapper.selectGoodsReviewList(jLecKey, startNumber, listLimit);
        }
        return new ApiPagingResultDTO(goodsReviewCnt, goodsReviewList, resultCode);
    }

    /**
     * 내 강의실 > 수강중인강좌 > 동영상 수강중인강좌 > 일시정지 버튼 시 팝업 데이터
     * @param jLecKey
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultObjectDTO getVideoPauseRequestPopup(int jLecKey) {
        int resultCode = OK.value();

        VideoPausePopupVO videoPausePopupVO = new VideoPausePopupVO();
        if (jLecKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            videoPausePopupVO = productMapper.selectVideoPauseRequestPopup(jLecKey);
        }
        return new ApiResultObjectDTO(videoPausePopupVO, resultCode);
    }

    /**
     * 강좌 일시정지 요청, 일시정지 해제
     * @param jLecKey
     * @param pauseDay
     * @param requestType
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO requestVideoStartStop(int jLecKey, int pauseDay, String requestType) {
        int resultCode = OK.value();

        if (jLecKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            int pauseCnt = productMapper.selectTOrderLecPauseCnt(jLecKey);
            if (pauseCnt >= 3) {
                resultCode = ZianErrCode.CUSTOM_PAUSE_LIMIT_OVER_VIDEO.code();
            } else {
                if ("STOP".equals(requestType)) {
                    productMapper.updateTOrderLecPauseCnt(jLecKey, pauseDay);
                } else if ("START".equals(requestType)) {
                    pauseDay = 0;
                    productMapper.updateTOrderLecPauseCnt(jLecKey, pauseDay);
                }
                TOrderLecStartStopLogVO startStopLogVO = new TOrderLecStartStopLogVO(jLecKey, requestType);
                productMapper.insertTOrderLecStartStopLog(startStopLogVO);
            }
        }
        return new ApiResultCodeDTO("J_LEC_KEY", jLecKey, resultCode);
    }

    /**
     * 일시정지강좌 목록
     * @param userKey
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultListDTO getOnlineVideoPauseList(int userKey) {
        int resultCode = OK.value();

        List<OnlineVideoPauseVO> videoPauseList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            videoPauseList = productMapper.selectVideoOnlinePauseList(userKey);
            if (videoPauseList != null || videoPauseList.size() > 0) {
                lectureProgressRateRepository.injectLectureProgressRateAny(videoPauseList);
            }
        }
        return new ApiResultListDTO(videoPauseList, resultCode);
    }

    /**
     * 일시정지강좌 목록
     * @param userKey
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultListDTO getOnlineVideoEndList(int userKey) {
        int resultCode = OK.value();

        List<OnlineVideoEndVO> videoEndList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            videoEndList = productMapper.selectVideoOnlineEndList(userKey);
            if (videoEndList.size() > 0 || videoEndList != null) {
                for (OnlineVideoEndVO videoEndVO : videoEndList) {
                    videoEndVO.setKindName(GoodsKindType.getGoodsKindName(videoEndVO.getKind()));
                    if (videoEndVO.getExtendDay() == -1) {
                        videoEndVO.setExtendDayName("");
                    } else {
                        videoEndVO.setExtendDayName(videoEndVO.getExtendDay() + "일 재수강");
                    }
                }
            }
        }
        return new ApiResultListDTO(videoEndList, resultCode);
    }

}
