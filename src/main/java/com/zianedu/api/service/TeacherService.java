package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.datasource.BbsMasterKeyType;
import com.zianedu.api.define.datasource.EmphasisType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiPagingResultDTO;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.dto.ReferenceRoomDetailDTO;
import com.zianedu.api.mapper.BoardMapper;
import com.zianedu.api.mapper.MenuMapper;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.mapper.TeacherMapper;
import com.zianedu.api.utils.*;
import com.zianedu.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class TeacherService extends PagingSupport {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getTeacherHomeInfo(int teacherKey, int listLimit, int device, int menuCtgKey) {
        int resultCode = OK.value();

        TeacherHomeVO teacherHomeInfo = null;
        if (teacherKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            //강사기본정보
            TTeacherVO teacherInfo = this.getTeacherInfo(teacherKey);
            //강사 상세설명 정보
            teacherInfo.setIntroduce(teacherMapper.selectTeacherIntroduceInfo(teacherKey, device, menuCtgKey));
            //학습자료실
            List<TBbsDataVO> referenceRoom = boardMapper.selectTBbsDataList(
                    BbsMasterKeyType.LEARNING_REFERENCE_ROOM.getBbsMasterKey(), teacherKey, ZianApiUtils.LIMIT,0, listLimit
            );
            //학습 QNA
            List<TBbsDataVO> learningQna = boardMapper.selectTBbsDataList(
                    BbsMasterKeyType.LEARNING_QNA.getBbsMasterKey(), teacherKey,ZianApiUtils.LIMIT,0, listLimit
            );
            //수강후기
            List<GoodsReviewVO> lectureReview = boardMapper.selectGoodsReviewList(teacherKey, ZianApiUtils.LIMIT, 0, listLimit);
            //동영상강좌정보
            List<GoodsListVO> videoLecture = teacherMapper.selectGoodsListAtTeacherHome(teacherKey, 1);
            if (videoLecture.size() > 0) {
                for (GoodsListVO vo : videoLecture) {
                    //할인률 주입(상품이 여러개면 할인률이 제일 큰값으로 가져온다)
                    CalcPriceVO calcPrice = productMapper.selectTopCalcPrice(vo.getGKey());
                    String discountPercent = Util.getProductDiscountRate(calcPrice.getPrice(), calcPrice.getSellPrice());
                    vo.setDiscountPercent("[" + discountPercent + "할인]");
                }
            }
            //학원강좌정보
            List<GoodsListVO> academyLecture = teacherMapper.selectGoodsListAtTeacherHome(teacherKey, 2);
            //도서정보
            List<BannerBookVO> teacherBook = teacherMapper.selectTeacherBookList(teacherKey);
            if (teacherBook.size() > 0) {
                for (BannerBookVO vo : teacherBook) {
                    vo.setImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getImageList()));
                }
            }
            teacherHomeInfo = new TeacherHomeVO(teacherInfo, referenceRoom, learningQna, lectureReview, videoLecture, academyLecture, teacherBook);
        }
        return new ApiResultObjectDTO(teacherHomeInfo, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getTeacherLectureList(int teacherKey, int stepCtgKey) {
        int resultCode = OK.value();

        List<TeacherHomeLectureVO> teacherHomeLectureList = new ArrayList<>();
        if (teacherKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            teacherHomeLectureList = teacherMapper.selectTeacherVideoLectureList(teacherKey, stepCtgKey);
            if (teacherHomeLectureList.size() > 0) {
                for (TeacherHomeLectureVO vo : teacherHomeLectureList) {
                    for (TeacherHomeLectureListVO vo2 : vo.getTeacherLectureList()) {
                        vo2.setEmphasisName(EmphasisType.getEmphasisStr(vo2.getEmphasis()));    //NEW, BEST 주입
                        //vo2.setPcSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(vo2.getSellPrice())) + "원");
                        //vo2.setPcSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(vo2.getSellPrice())) + "원");
                        //할인률 주입
                        CalcPriceVO calcPriceVO = productMapper.selectTopCalcPrice(vo2.getGKey());
                        vo2.setDiscountPercent(Util.getProductDiscountRate(calcPriceVO.getPrice(), calcPriceVO.getSellPrice()) + "할인");
                        //동영상, 모바일, 동영상+모바일 리스트 주입
                        List<TGoodsPriceOptionVO> videoLectureKindList = productMapper.selectGoodsPriceOptionList(vo2.getGKey());
                        vo2.setVideoLectureKindList(videoLectureKindList);
                        //동영상 종류별 금액 주입하기
                        for (TGoodsPriceOptionVO priceOptionVO : videoLectureKindList) {
                            if (priceOptionVO.getKind() == 100) {
                                vo2.setPcSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(priceOptionVO.getSellPrice())) + "원");
                            } else if (priceOptionVO.getKind() == 101) {
                                vo2.setMobileSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(priceOptionVO.getSellPrice())) + "원");
                            } else if (priceOptionVO.getKind() == 102) {
                                vo2.setPcMobileSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(priceOptionVO.getSellPrice())) + "원");
                            }
                        }
                        //강사 이미지 URL
                        vo2.setTeacherImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo2.getImageTeacherList()));
                        //강좌책 주입
                        List<LectureBookVO> teacherBookList = productMapper.selectTeacherBookListFromVideoLectureLink(vo2.getGKey());
                        vo2.setTeacherLectureBook(teacherBookList);
                    }
                }
            }
        }
        return new ApiResultListDTO(teacherHomeLectureList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getTeacherAcademyList(int teacherKey, int stepCtgKey) {
        int resultCode = OK.value();

        List<TeacherHomeAcademyVO> teacherHomeAcademyList = new ArrayList<>();
        if (teacherKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            teacherHomeAcademyList = teacherMapper.selectTeacherAcademyLectureList(teacherKey, stepCtgKey);
        }
        return new ApiResultListDTO(teacherHomeAcademyList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getTeacherLearningQna(int teacherKey, int sPage, int listLimit, String searchType, String searchText) throws Exception {
        int resultCode = OK.value();

        List<ReferenceRoomVO> learningQnaList = new ArrayList<>();
        int totalCount = 0;
        int startNumber = getPagingStartNumber(sPage, listLimit);

        if (teacherKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            totalCount = boardMapper.selectTBbsDataListBySearchCount(
                    BbsMasterKeyType.LEARNING_QNA.getBbsMasterKey(),
                    teacherKey,
                    Util.isNullValue(searchType, ""),
                    Util.isNullValue(searchText, "")
            );
            learningQnaList = boardMapper.selectTBbsDataListBySearch(
                    BbsMasterKeyType.LEARNING_QNA.getBbsMasterKey(),
                    teacherKey,
                    startNumber,
                    listLimit,
                    Util.isNullValue(searchType, ""),
                    Util.isNullValue(searchText, "")
            );
            if (learningQnaList.size() > 0) {
                String standardDate = Util.plusDate(Util.returnNow(), -10);
                for (ReferenceRoomVO vo : learningQnaList) {
                    int diffDayCnt = Util.getDiffDayCount(Util.convertDateFormat3(standardDate), Util.convertDateFormat3(vo.getIndate()));

                    if (diffDayCnt >= 0 && diffDayCnt <= 10) vo.setNew(true);
                    else vo.setNew(false);
                }
            }
        }
        return new ApiPagingResultDTO(totalCount, learningQnaList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getReferenceRoomList(int teacherKey, int sPage, int listLimit, String searchType, String searchText) throws Exception {
        int resultCode = OK.value();

        List<ReferenceRoomVO> referenceRoomList = new ArrayList<>();
        int totalCount = 0;
        int startNumber = getPagingStartNumber(sPage, listLimit);

        if (teacherKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            totalCount = boardMapper.selectTBbsDataListBySearchCount(
                    BbsMasterKeyType.LEARNING_REFERENCE_ROOM.getBbsMasterKey(),
                    teacherKey,
                    Util.isNullValue(searchType, ""),
                    Util.isNullValue(searchText, "")
            );
            referenceRoomList = boardMapper.selectTBbsDataListBySearch(
                    BbsMasterKeyType.LEARNING_REFERENCE_ROOM.getBbsMasterKey(),
                    teacherKey,
                    startNumber,
                    listLimit,
                    Util.isNullValue(searchType, ""),
                    Util.isNullValue(searchText, "")
            );
            if (referenceRoomList.size() > 0) {
                String standardDate = Util.plusDate(Util.returnNow(), -10);
                for (ReferenceRoomVO vo : referenceRoomList) {
                    int diffDayCnt = Util.getDiffDayCount(Util.convertDateFormat3(standardDate), Util.convertDateFormat3(vo.getIndate()));

                    if (diffDayCnt >= 0 && diffDayCnt <= 10) vo.setNew(true);
                    else vo.setNew(false);
                }
            }
        }
        return new ApiPagingResultDTO(totalCount, referenceRoomList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getLectureReviewList(int teacherKey, int gKey, int sPage, int listLimit) {
        int resultCode = OK.value();

        int totalCount = 0;
        int startNumber = getPagingStartNumber(sPage, listLimit);
        List<GoodsReviewVO> lectureReviewList = new ArrayList<>();

        if (teacherKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            totalCount = boardMapper.selectTeacherReviewListCount(teacherKey, gKey);
            lectureReviewList = boardMapper.selectTeacherReviewList(teacherKey, gKey, startNumber, listLimit);
        }
        return new ApiPagingResultDTO(totalCount, lectureReviewList, resultCode);
    }

    /**
     * 강사소개 > 수강후기 > 강사의 상품목록 셀렉트박스
     * @param teacherKey
     * @return
     */
    @Transactional(readOnly = true)
    public ApiResultListDTO getTeacherVideoAcademyProductList(int teacherKey) {
        int resultCode = OK.value();

        int totalCount = 0;
        List<TeacherVideoAcademyProductVO> teacherVideoAcademyProductList = new ArrayList<>();

        if (teacherKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            teacherVideoAcademyProductList = teacherMapper.selectTeacherVideoAcademyProductList(teacherKey);
        }
        return new ApiResultListDTO(teacherVideoAcademyProductList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getTeacherReferenceRoomDetailInfo(int bbsKey, int teacherKey) {
        int resultCode = OK.value();

        ReferenceRoomDetailDTO detailDTO = null;

        if (teacherKey == 0 && bbsKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            detailDTO = new ReferenceRoomDetailDTO(
                    //학습자료실 상세정보
                    boardMapper.selectTeacherReferenceRoomDetailInfo(bbsKey),
                    //하단 이전글, 다음글
                    boardMapper.selectTeacherReferenceRoomPrevNext(
                            BbsMasterKeyType.LEARNING_REFERENCE_ROOM.getBbsMasterKey(), teacherKey, bbsKey)
            );
        }
        return new ApiResultObjectDTO(detailDTO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getTeacherLearningQnaDetailInfo(int bbsKey, int teacherKey) {
        int resultCode = OK.value();

        ReferenceRoomDetailDTO detailDTO = null;

        if (teacherKey == 0 && bbsKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            detailDTO = new ReferenceRoomDetailDTO(
                    //학습자료실 상세정보
                    boardMapper.selectTeacherReferenceRoomDetailInfo(bbsKey),
                    //하단 이전글, 다음글
                    boardMapper.selectTeacherReferenceRoomPrevNext(
                            BbsMasterKeyType.LEARNING_QNA.getBbsMasterKey(), teacherKey, bbsKey)
            );
        }
        return new ApiResultObjectDTO(detailDTO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getTeacherIntroduceList(int ctgKey, int pos) {
        int resultCode = OK.value();

        List<TeacherIntroduceVO> teacherIntroduceInfo = new ArrayList<>();

        if (ctgKey == 0 && pos < 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            teacherIntroduceInfo = teacherMapper.selectTeacherIntroduce(ctgKey, pos);
            if (teacherIntroduceInfo.size() > 0) {
                for (TeacherIntroduceVO vo : teacherIntroduceInfo) {
                    if (vo.getTeacherIntroduceList().size() > 0) {
                        //강사 이미지 URL주입
                        for (TeacherIntroduceListVO vo2 : vo.getTeacherIntroduceList()) {
                            vo2.setImageListUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo2.getImageList()));
                        }
                    }
                }
            }
        }
        return new ApiResultObjectDTO(teacherIntroduceInfo, resultCode);
    }

    /**
     * 강사정보 가져오기
     * @param teacherKey
     * @return
     */
    @Transactional(readOnly = true)
    public TTeacherVO getTeacherInfo(int teacherKey) {
        if (teacherKey == 0) return null;

        TTeacherVO teacherInfo = teacherMapper.selectTeacherInfo(teacherKey);
        if (teacherInfo != null) {
            teacherInfo.setImageListUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), teacherInfo.getImageList()));
            teacherInfo.setImageTeacherListUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), teacherInfo.getImageTeacherList()));
            teacherInfo.setImageTeacherViewUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), teacherInfo.getImageTeacherView()));
        }
        return teacherInfo;
    }


}
