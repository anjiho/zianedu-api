package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.datasource.GoodsKindType;
import com.zianedu.api.define.datasource.LectureStatusType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.*;
import com.zianedu.api.mapper.*;
import com.zianedu.api.repository.LectureProgressRateRepository;
import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.utils.PagingSupport;
import com.zianedu.api.utils.Util;
import com.zianedu.api.vo.*;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.springframework.http.HttpStatus.OK;

@Service
public class MyPageService extends PagingSupport {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private TeacherMapper teacherMapper;

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

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultObjectDTO getUserVideoOnlineSignUpList(int userKey, String deviceType) {
        int resultCode = OK.value();
        OnlineSignUpDTO onlineSignUpDTO = new OnlineSignUpDTO();
        List<OnlineSignUpVO>resultList = new ArrayList<>();

        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            //내강의실 들어오면 시작안된 단관상품 시작일 업데이트
            /*productMapper.updateTOrderLecStartDtByUserKey(userKey);*/
            //과목 리스트 주입
            List<SubjectDTO>subjectList = productMapper.selectVideoOnlineSignUpSubjectList(userKey, deviceType);
            //유형 리스트 주입
            //List<TypeDTO>typeList = productMapper.selectVideoOnlineSignUpTypeList(userKey, deviceType);

            onlineSignUpDTO.setSubjectInfo(subjectList);
           // onlineSignUpDTO.setTypeInfo(typeList);

            onlineSignUpDTO.setOnlineSignUpList(resultList);
        }
        return new ApiResultObjectDTO(onlineSignUpDTO, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultObjectDTO getUserVideoOnlineSignUpTypeList(int userKey, int subjectCtgKey, String deviceType) {
        int resultCode = OK.value();
        List<TypeDTO> typeList = new ArrayList<>();

        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            //유형 리스트 주입
            typeList = productMapper.selectVideoOnlineSignUpTypeList(userKey, subjectCtgKey, deviceType);
        }
        return new ApiResultObjectDTO(typeList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getUserVideoOnlineSignUpLectureList(int jLecKey) throws Exception {
        int resultCode = OK.value();
        OnlineSubjectListVO onlineSubjectListVO = new OnlineSubjectListVO();

        if (jLecKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            onlineSubjectListVO = productMapper.selectLectureDetailInfoByJLecKey(jLecKey);
            if (onlineSubjectListVO != null) {
                Integer progressRate = productMapper.selectOnlineLectureProgressRate(jLecKey);
                onlineSubjectListVO.setProgressRateName(progressRate + "%");
            }
        }
        return new ApiResultObjectDTO(onlineSubjectListVO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getPromotionUserVideoOnlineSignUpLectureList(int jLecKey) throws Exception {
        int resultCode = OK.value();
        OnlineSubjectListVO onlineSubjectListVO = new OnlineSubjectListVO();

        if (jLecKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            onlineSubjectListVO = productMapper.selectLectureDetailInfoByJLecKey(jLecKey);
            if (onlineSubjectListVO != null) {
                Integer progressRate = productMapper.selectOnlineLectureProgressRate(jLecKey);
                onlineSubjectListVO.setProgressRateName(progressRate + "%");

                TOrderPromotionVO orderPromotionVO = productMapper.selectTOrderPromotionInfo(onlineSubjectListVO.getJPmKey());
                TOrderGoodsVO orderGoodsVO = productMapper.selectPromotionGKeyFromByJGKey(orderPromotionVO.getJGKey());
                TPromotionVO promotionVO = productMapper.selectTPromotionInfoByGKey(orderGoodsVO.getGKey());
                if (promotionVO != null) {
                    int limitDay = promotionVO.getLimitDay();
                    if (orderGoodsVO.getKind() < 100) {
                        limitDay = (30 * orderGoodsVO.getKind());
                    }
                    onlineSubjectListVO.setLimitDay(limitDay);
                    onlineSubjectListVO.setEndDate(Util.plusDate2(onlineSubjectListVO.getStartDt(), limitDay));
                }
            }
        }
        return new ApiResultObjectDTO(onlineSubjectListVO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getUserSignUpLectureNameList(int userKey, String deviceType, int subjectCtgKey, int stepCtgKey) {
        int resultCode = OK.value();

        List<SignUpLectureVO> signUpLectureNameList = new ArrayList<>();
        if (userKey == 0 && "".equals(deviceType)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            signUpLectureNameList = productMapper.selectSignUpLectureList(userKey, deviceType, subjectCtgKey, stepCtgKey);
        }
        return new ApiResultListDTO(signUpLectureNameList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getLecCtgInfo(int jlecKey) {
        int resultCode = OK.value();

        HashMap<String, Integer> hashMap = new HashMap<>();
        int teacherKey =0;

        if (jlecKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            int gKey = orderMapper.selectGkey(jlecKey);
            List<Integer> ctgKey = categoryMapper.selectCtgKetListFromTCategoryGoods(gKey);

            int ctg = 0;
            ctg =Integer.parseInt(ctgKey.get(0).toString());
            int parentCtgkey = categoryMapper.selectParentKeyFromCategory(ctg);

            if(parentCtgkey !=0 ){
                int parentCtgkey2 = categoryMapper.selectParentKeyFromCategory(parentCtgkey);

                if(parentCtgkey2 !=0 ){
                    int parentCtgkey3 = categoryMapper.selectParentKeyFromCategory(parentCtgkey2);

                    if(parentCtgkey3 !=0 ) {
                        teacherKey = teacherMapper.getTeacherKeyOfJLecKey(jlecKey);

                        hashMap.put("teacherKey", teacherKey);
                        hashMap.put("ctgKey", parentCtgkey3);
                    }else resultCode = ZianErrCode.BAD_REQUEST.code();
                }else resultCode = ZianErrCode.BAD_REQUEST.code();
            }else resultCode = ZianErrCode.BAD_REQUEST.code();
        }

        return new ApiResultObjectDTO(hashMap, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultCodeDTO getUserSignUpLectureCount(int userKey, String deviceType, int subjectCtgKey, int stepCtgKey) {
        int resultCode = OK.value();

        int cnt = 0;
        List<SignUpLectureVO> signUpLectureNameList = new ArrayList<>();
        if (userKey == 0 && "".equals(deviceType)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            signUpLectureNameList = productMapper.selectSignUpLectureList(userKey, deviceType, subjectCtgKey, stepCtgKey);
            cnt = signUpLectureNameList.size();
        }
        return new ApiResultCodeDTO("COUNT", cnt, resultCode);
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
            int pauseCnt = productMapper.selectTOrderLecPauseCnt(jLecKey);
            if (pauseCnt >= 3) {
                resultCode = ZianErrCode.CUSTOM_PAUSE_LIMIT_OVER_VIDEO.code();
            } else {
                videoPausePopupVO = productMapper.selectVideoPauseRequestPopup(jLecKey);
            }
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
            if (pauseCnt > 3) {
                resultCode = ZianErrCode.CUSTOM_PAUSE_LIMIT_OVER_VIDEO.code();
            } else {
                if ("STOP".equals(requestType)) {
                    productMapper.updateTOrderLecPauseCnt(jLecKey, pauseDay, 0);
                } else if ("START".equals(requestType)) {
                    int pauseTotalDay = productMapper.selectPauseTotalDay(jLecKey);
                    if (pauseTotalDay > 0) {
                        productMapper.updateTOrderLecPauseCnt(jLecKey, 0, pauseTotalDay);
                    }
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
     * @param jLecKey
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultListDTO getOnlineVideoPauseListByJLecKey(int jLecKey) {
        int resultCode = OK.value();

        List<OnlineVideoPauseVO> videoPauseList = new ArrayList<>();
        if (jLecKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            videoPauseList = productMapper.selectVideoOnlinePauseListByJLecKey(jLecKey);
            if (videoPauseList != null || videoPauseList.size() > 0) {
                lectureProgressRateRepository.injectLectureProgressRateAny(videoPauseList);
            }
        }
        return new ApiResultListDTO(videoPauseList, resultCode);
    }

    /**
     * 종료된 강좌 목록
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

    @Transactional(readOnly = true)
    public ApiResultListDTO getZianPassProductList(int userKey) {
        int resultCode = OK.value();

        List<ZianPassSignUpVO> zianPassList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            zianPassList = productMapper.selectSignUpZianPassList(userKey);
        }
        return new ApiResultListDTO(zianPassList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getSignUpZianPassTypeList(int jKey, String device) {
        int resultCode = OK.value();

        List<TypeDTO> zianPassTypeList = new ArrayList<>();
        if (jKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TOrderPromotionVO orderPromotionVO = productMapper.selectTOrderPromotionInfoByJGKey(jKey);
            zianPassTypeList = productMapper.selectSignUpZianPassTypeList(orderPromotionVO.getJPmKey(), device);
        }
        return new ApiResultListDTO(zianPassTypeList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getSinUpZianPassLectureNameList(int jGKey, String deviceType, int stepCtgKey) {
        int resultCode = OK.value();

        List<SignUpLectureVO> signUpLectureNameList = new ArrayList<>();
        if (jGKey == 0 && "".equals(deviceType)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TOrderPromotionVO orderPromotionVO = productMapper.selectTOrderPromotionInfoByJGKey(jGKey);
            signUpLectureNameList = productMapper.selectZianPassSubjectNameList(orderPromotionVO.getJPmKey(), stepCtgKey, deviceType);
        }
        return new ApiResultListDTO(signUpLectureNameList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getSignUpPackageProductList(int userKey, String deviceType) {
        int resultCode = OK.value();

        List<ZianPassSignUpVO> zianPassList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            int kind = 0;
            if ("PC".equals(deviceType)) kind = 100;
            else if ("MOBILE".equals(deviceType)) kind = 101;

            zianPassList = productMapper.selectSignUpPackageList(userKey, kind);
        }
        return new ApiResultListDTO(zianPassList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getSignUpPackageTypeList(int jKey, String device) {
        int resultCode = OK.value();

        List<TypeDTO> zianPassTypeList = new ArrayList<>();
        if (jKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            //TOrderGoodsVO orderGoodsVO = productMapper.selectPromotionGKeyFromByJGKey(jKey);
            TOrderPromotionVO orderPromotionVO = productMapper.selectTOrderPromotionInfoByJGKey(jKey);
            zianPassTypeList = productMapper.selectSignUpZianPassTypeList(orderPromotionVO.getJPmKey(), device);
        }
        return new ApiResultListDTO(zianPassTypeList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getSignUpAcademyTypeList(int userKey) {
        int resultCode = OK.value();

        List<TypeDTO> academyTypeList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            academyTypeList = productMapper.selectSignUpAcademyTypeList(userKey);
        }
        return new ApiResultListDTO(academyTypeList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getSignUpAcademyLectureNameList(int userKey, int stepCtgKey) {
        int resultCode = OK.value();

        List<SignUpLectureVO> signUpLectureNameList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            signUpLectureNameList = productMapper.selectSignUpAcademySubjectNameList(userKey, stepCtgKey);
        }
        return new ApiResultListDTO(signUpLectureNameList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getSignUpVideoLecturePauseTypeList(int userKey) {
        int resultCode = OK.value();

        List<TypeDTO> typeList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            typeList = productMapper.selectSignUpVideoLecturePauseTypeList(userKey);
        }
        return new ApiResultListDTO(typeList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getSignUpVideoLecturePauseSubjectList(int userKey, int stepCtgKey) {
        int resultCode = OK.value();

        List<SignUpLectureVO> signUpLectureNameList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            signUpLectureNameList = productMapper.selectSignUpVideoLecturePauseSubjectList(userKey, stepCtgKey);
        }
        return new ApiResultListDTO(signUpLectureNameList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getSignUpVideoLectureEndTypeList(int userKey, String deviceType) {
        int resultCode = OK.value();

        List<TypeDTO> typeList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            typeList = productMapper.selectSignUpVideoLectureEndTypeList(userKey, deviceType);
        }
        return new ApiResultListDTO(typeList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getSignUpVideoLectureEndSubjectList(int userKey, int stepCtgKey, String deviceType) {
        int resultCode = OK.value();

        List<SignUpLectureVO> signUpLectureNameList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            signUpLectureNameList = productMapper.selectSignUpVideoLectureEndSubjectList(userKey, stepCtgKey, deviceType);
        }
        return new ApiResultListDTO(signUpLectureNameList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getSignUpVideoLectureEndInfo(int jLecKey) {
        int resultCode = OK.value();

        OnlineVideoEndVO videoEndInfo = new OnlineVideoEndVO();
        if (jLecKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            videoEndInfo = productMapper.selectVideoOnlineEndListByJLecKey(jLecKey);
        }
        return new ApiResultObjectDTO(videoEndInfo, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getOneByOneQuestionList(int userKey, int sPage, int listLimit, String searchType, String searchText) {
        int resultCode = OK.value();

        int totalCount = 0;
        int startNumber = getPagingStartNumber(sPage, listLimit);

        List<ReferenceRoomVO> questionList = new ArrayList<>();
        List<ReferenceRoomVO> questionList3 = new CopyOnWriteArrayList<>();


        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TUserVO userInfo = userMapper.selectUserInfoByUserKey(userKey);
            if (userInfo.getAuthority() == 0) {
                userKey = 0;
            }
            totalCount = boardMapper.selectOneByOneQuestionListCount(userKey, searchType, searchText);
            questionList = boardMapper.selectOneByOneQuestionList(userKey, startNumber,listLimit, searchType, searchText);

            if (questionList.size() > 0) {


////                for (ReferenceRoomVO vo : questionList) {
//                    if (vo.getLevel() > 1) {
//                        //운영자가 쓴글 기준으로 불러올때
//                        List<ReferenceRoomVO> questionList2 = new CopyOnWriteArrayList<>();
//                        ReferenceRoomVO questionInfo = new ReferenceRoomVO();
//                        questionList2.add(vo);
//                        int len = vo.getLevel();
//                        int level = len;
//                        for (int i = 0; i < len-1; i++) {
//                            if (i == 0){
//                                questionInfo = boardMapper.selectOneByOneQuestionListByBbsParentKey(vo.getBbsParentKey());
//                                questionInfo.setLevel(--level);
//                            } else {
//                                questionInfo = boardMapper.selectOneByOneQuestionListByBbsParentKey(questionInfo.getBbsParentKey());
//                                questionInfo.setLevel(--level);
//                            }
//                            questionList2.add(questionInfo);
//                            if (questionInfo == null) {
//                                continue;
//                            }
//                        }
//                        Collections.sort(questionList2);
//                        int len2 = questionList2.size();
//                        for (int j=0; j<len2 ; j++) {
//                            if ((j+1) == questionList2.get(j).getLevel()) {
//                                questionList3.add(questionList2.get(j));
//                            }
//                        }
//                    } else if (vo.getLevel() == 1) {
//                        //일반 사용자가 쓴글 기준으로 불러올때
//                        questionList3.add(vo);
//                        ReferenceRoomVO questionInfo = boardMapper.selectOneByOneQuestionListByBbsKey(vo.getBbsKey());
//                        if (questionInfo != null) {
//                            questionList3.add(questionInfo);
//                            ReferenceRoomVO questionInfo2 = boardMapper.selectOneByOneQuestionListByBbsKey(questionInfo.getBbsKey());
//                            if (questionInfo2 != null) {
//                                questionList3.add(questionInfo2);
//                            }
//                        }
//                    }
//                }
            }
        }
        return new ApiPagingResultDTO(totalCount, questionList, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultObjectDTO getOneByOneQuestionDetailInfo (int bbsKey) {
        int resultCode = OK.value();

        ReferenceRoomDetailVO detailVO = new ReferenceRoomDetailVO();
        if (bbsKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
           detailVO  = boardMapper.selectTeacherReferenceRoomDetailInfo(bbsKey);
           boardMapper.updateTBbsReadCount(bbsKey);
        }
        return new ApiResultObjectDTO(detailVO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getZianPassEndList(int userKey) {
        int resultCode = OK.value();

        List<ZianPassEndListVO> endList = new ArrayList<>();
        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            endList = productMapper.selectZianPassEndList(userKey);
        }
        return new ApiResultListDTO(endList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getBoardListAtMyWrite(int userKey, String boardType, int sPage, int listLimit,
                                                    String searchType, String searchText) {
        int resultCode = OK.value();

        int totalCount = 0;
        int startNumber = getPagingStartNumber(sPage, listLimit);
        List<TBbsDataVO> boardList = new ArrayList<>();
        if (userKey == 0 && "".equals(boardType)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TUserVO userInfo = userMapper.selectUserInfoByUserKey(userKey);
            if (userInfo.getAuthority() == 0) {
                userKey = 0;
            }
            totalCount = boardMapper.selectBoardListAtMyWriteCount(userKey, boardType, searchType, searchText);
            boardList = boardMapper.selectBoardListAtMyWrite(userKey, boardType, startNumber, listLimit, searchType, searchText);
        }
        return new ApiPagingResultDTO(totalCount, boardList, resultCode);
    }
}

