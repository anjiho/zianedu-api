package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.datasource.EmphasisType;
import com.zianedu.api.define.datasource.GoodsType;
import com.zianedu.api.define.datasource.LectureStatusType;
import com.zianedu.api.define.datasource.PromotionPmType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.*;
import com.zianedu.api.mapper.ExamMapper;
import com.zianedu.api.mapper.MenuMapper;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.utils.*;
import com.zianedu.api.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class ProductService extends PagingSupport {

    protected final String ZIAN_PASS_HTML_URL = "http://52.79.40.214/views/zianPass/";

    protected final String YEAR_MEMBER_HTML_URL = "http://52.79.40.214/views/yearMember/";

    protected int resultCode = OK.value();

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private ExamMapper examMapper;

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getVideoProductDetailInfo(int gKey, int device) {
        int resultCode = OK.value();

        VideoProductDTO videoProductDTO = null;
        VideoLectureDetailVO videoLectureDetailVO = null;
        List<TGoodsPriceOptionVO> priceOptionVO = new ArrayList<>();
        List<LectureBookVO> lectureBookList = new ArrayList<>();
        List<TLecCurriVO> lectureList = new ArrayList<>();
        if (gKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            //상품 기본 정보
            videoLectureDetailVO = productMapper.selectOnlineVideoLectureDetailInfo(gKey);
            if (videoLectureDetailVO != null) {
                videoLectureDetailVO.setStatusName(LectureStatusType.getLectureStatusName(videoLectureDetailVO.getStatus()));
                videoLectureDetailVO.setTeacherImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), videoLectureDetailVO.getImageTeacherList()));
            }
            //상품 종류별 가격정보
            priceOptionVO = productMapper.selectGoodsPriceOption(gKey);
            if (priceOptionVO.size() > 0) {
                for (TGoodsPriceOptionVO vo : priceOptionVO) {
                    String discountPercent = Util.getProductDiscountRate(vo.getPrice(), vo.getSellPrice());
                    vo.setDiscountPercent(discountPercent);
                }
            }
            //강의교재 정보
            lectureBookList = productMapper.selectTeacherBookListFromVideoLectureLink(gKey);
            if (lectureBookList.size() > 0) {
                for (LectureBookVO vo : lectureBookList) {
                    vo.setImageListUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getImageList()));
                    vo.setImageViewUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getImageView()));
                    vo.setDiscountPercent(Util.getProductDiscountRate(vo.getPrice(), vo.getSellPrice()));
                }
            }
            //강의목록
            lectureList = productMapper.selectLectureListFromVideoProduct(gKey, device);

            videoProductDTO = new VideoProductDTO(
                    videoLectureDetailVO, priceOptionVO, lectureBookList, lectureList
            );

        }
        return new ApiResultObjectDTO(videoProductDTO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getAcademyProductDetailInfo(int gKey) {
        int resultCode = OK.value();

        AcademyProductDTO academyProductDTO = null;
        AcademyLectureDetailVO academyLectureDetailVO = null;
        List<TeacherInfoVO> teacherInfoList = new ArrayList<>();
        if (gKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            //학원수강 상세정보
            academyLectureDetailVO = productMapper.selectAcademyLectureDetailInfo(gKey);
            if (academyLectureDetailVO != null) {
                //할인률 주입
                if (academyLectureDetailVO.getPrice() > 0 && academyLectureDetailVO.getSellPrice() > 0) {
                    academyLectureDetailVO.setDiscountPercent(
                            Util.getProductDiscountRate(academyLectureDetailVO.getPrice(), academyLectureDetailVO.getSellPrice())
                    );
                }
                academyLectureDetailVO.setImageViewUrl(
                        FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), academyLectureDetailVO.getImageView())
                );
            }
            //강사 리스트
            teacherInfoList = productMapper.selectAcademyLectureTeacherList(gKey);
            if (teacherInfoList.size() > 0) {
                for (TeacherInfoVO vo : teacherInfoList) {
                    vo.setTeacherImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getTeacherImage()));
                }
            }
            academyProductDTO = new AcademyProductDTO(academyLectureDetailVO, teacherInfoList);
        }
        return new ApiResultObjectDTO(academyProductDTO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getAcademyLectureListFromCategoryMenu(int ctgKey, int stepCtgKey, int teacherKey) {
        int resultCode = OK.value();

        List<AcademyLectureListVO> list = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            list = productMapper.selectAcademyLectureListFromCategoryMenu(ctgKey, stepCtgKey, teacherKey);

            if (list.size() > 0) {
                for (AcademyLectureListVO vo : list) {
                    if (vo.getLectureList().size() > 0) {
                        //판매 교재 주입
                        for (AcademyLectureListDetailVO vo2 : vo.getLectureList()) {
                             List<LectureBookVO> lectureBook = productMapper.selectTeacherBookListFromVideoLectureLink(vo2.getGKey());
                             vo2.setLectureBook(lectureBook);
                        }
                    }
                }
            }
        }
        return new ApiResultListDTO(list, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getVideoLectureListFromCategoryMenu(int ctgKey, int stepCtgKey, int teacherKey) {
        int resultCode = OK.value();

        List<TeacherHomeLectureVO> teacherHomeLectureList = new ArrayList<>();

        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            teacherHomeLectureList = productMapper.selectVideoLectureListFromCategoryMenu(ctgKey, stepCtgKey, teacherKey);
            if (teacherHomeLectureList.size() > 0) {
                for (TeacherHomeLectureVO vo : teacherHomeLectureList) {
                    for (TeacherHomeLectureListVO vo2 : vo.getTeacherLectureList()) {
                        //NEW, BEST 주입
                        vo2.setEmphasisName(EmphasisType.getEmphasisStr(vo2.getEmphasis()));
                        //할인률 주입
                        CalcPriceVO calcPriceVO = productMapper.selectTopCalcPrice(vo2.getGKey());
                        if (calcPriceVO.getPrice() > 0 && calcPriceVO.getSellPrice() > 0) {
                            vo2.setDiscountPercent(Util.getProductDiscountRate(calcPriceVO.getPrice(), calcPriceVO.getSellPrice()) + "할인");
                        } else {
                            vo2.setDiscountPercent("");
                        }
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
    public List<TeacherHomeLectureVO> getLectureApplyVideoLectureListFromCategoryMenu(int ctgKey, String[] stepCtgKeys, int teacherKey) {
        List<TeacherHomeLectureVO> teacherHomeLectureList = new ArrayList<>();

        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            List<String>stepKeyList = Arrays.asList(stepCtgKeys);
            //teacherHomeLectureList = productMapper.selectVideoLectureListFromCategoryMenuFromApplyLecture(ctgKey, stepKeyList, teacherKey);
            teacherHomeLectureList = productMapper.selectVideoLectureListFromCategoryMenuFromApplyLecture2(ctgKey, stepKeyList, teacherKey);
            if (teacherHomeLectureList.size() > 0) {
                for (TeacherHomeLectureVO vo : teacherHomeLectureList) {
                    for (TeacherHomeLectureListVO vo2 : vo.getTeacherLectureList()) {
                        //NEW, BEST 주입
                        vo2.setEmphasisName(EmphasisType.getEmphasisStr(vo2.getEmphasis()));
                        //할인률 주입
                        CalcPriceVO calcPriceVO = productMapper.selectTopCalcPrice(vo2.getGKey());
                        if (calcPriceVO.getPrice() > 0 && calcPriceVO.getSellPrice() > 0) {
                            vo2.setDiscountPercent(Util.getProductDiscountRate(calcPriceVO.getPrice(), calcPriceVO.getSellPrice()));
                        } else {
                            vo2.setDiscountPercent("");
                        }
                        //동영상, 모바일, 동영상+모바일 리스트 주입
                        List<TGoodsPriceOptionVO> videoLectureKindList = productMapper.selectGoodsPriceOptionList(vo2.getGKey());
                        vo2.setVideoLectureKindList(videoLectureKindList);
                        //동영상 종류별 금액 주입하기
                        for (TGoodsPriceOptionVO priceOptionVO : videoLectureKindList) {
                            if (priceOptionVO.getKind() == 100) {
                                vo2.setPcSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(priceOptionVO.getSellPrice())) + "원");
                                if (priceOptionVO.getSellPrice() > 0 && priceOptionVO.getPrice() > 0) {
                                    priceOptionVO.setPcDiscountPercent(Util.getProductDiscountRate(priceOptionVO.getPrice(), priceOptionVO.getSellPrice()));
                                }
                            } else if (priceOptionVO.getKind() == 101) {
                                vo2.setMobileSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(priceOptionVO.getSellPrice())) + "원");
                                if (priceOptionVO.getSellPrice() > 0 && priceOptionVO.getPrice() > 0) {
                                    priceOptionVO.setMobileDiscountPercent(Util.getProductDiscountRate(priceOptionVO.getPrice(), priceOptionVO.getSellPrice()));
                                }
                            } else if (priceOptionVO.getKind() == 102) {
                                vo2.setPcMobileSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(priceOptionVO.getSellPrice())) + "원");
                                if (priceOptionVO.getSellPrice() > 0 && priceOptionVO.getPrice() > 0) {
                                    priceOptionVO.setPcMobileDiscountPercent(Util.getProductDiscountRate(priceOptionVO.getPrice(), priceOptionVO.getSellPrice()));
                                }
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
        return teacherHomeLectureList;
    }

//    @Transactional(readOnly = true)
//    public List<TeacherHomeLectureVO> getLectureApplyVideoLectureListFromCategoryMenuByList(int ctgKey, String stepCtgKeys, int teacherKeys) {
//        List<TeacherHomeLectureVO> teacherHomeLectureList = new ArrayList<>();
//
//        if (ctgKey == 0) {
//            resultCode = ZianErrCode.BAD_REQUEST.code();
//        } else {
//            teacherHomeLectureList = productMapper.selectVideoLectureListFromCategoryMenuFromApplyLecture(ctgKey, stepCtgKeys, teacherKeys);
//            if (teacherHomeLectureList.size() > 0) {
//                for (TeacherHomeLectureVO vo : teacherHomeLectureList) {
//                    for (TeacherHomeLectureListVO vo2 : vo.getTeacherLectureList()) {
//                        //NEW, BEST 주입
//                        vo2.setEmphasisName(EmphasisType.getEmphasisStr(vo2.getEmphasis()));
//                        //할인률 주입
//                        CalcPriceVO calcPriceVO = productMapper.selectTopCalcPrice(vo2.getGKey());
//                        if (calcPriceVO.getPrice() > 0 && calcPriceVO.getSellPrice() > 0) {
//                            vo2.setDiscountPercent(Util.getProductDiscountRate(calcPriceVO.getPrice(), calcPriceVO.getSellPrice()) + "할인");
//                        } else {
//                            vo2.setDiscountPercent("");
//                        }
//                        //동영상, 모바일, 동영상+모바일 리스트 주입
//                        List<TGoodsPriceOptionVO> videoLectureKindList = productMapper.selectGoodsPriceOptionList(vo2.getGKey());
//                        vo2.setVideoLectureKindList(videoLectureKindList);
//                        //동영상 종류별 금액 주입하기
//                        for (TGoodsPriceOptionVO priceOptionVO : videoLectureKindList) {
//                            if (priceOptionVO.getKind() == 100) {
//                                vo2.setPcSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(priceOptionVO.getSellPrice())) + "원");
//                            } else if (priceOptionVO.getKind() == 101) {
//                                vo2.setMobileSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(priceOptionVO.getSellPrice())) + "원");
//                            } else if (priceOptionVO.getKind() == 102) {
//                                vo2.setPcMobileSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(priceOptionVO.getSellPrice())) + "원");
//                            }
//                        }
//                        //강사 이미지 URL
//                        vo2.setTeacherImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo2.getImageTeacherList()));
//                        //강좌책 주입
//                        List<LectureBookVO> teacherBookList = productMapper.selectTeacherBookListFromVideoLectureLink(vo2.getGKey());
//                        vo2.setTeacherLectureBook(teacherBookList);
//                    }
//                }
//            }
//        }
//        return teacherHomeLectureList;
//    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getSpecialPackageList(int menuCtgKey, String[] subjectMenuKeys, String[] teacherKeys, String[] stepCtgKeys, String device) {
        int resultCode = OK.value();

        List<String>subjectKeyList = new ArrayList<>();
        List<String>teacherKeyList = new ArrayList<>();
        List<String>stepCtgKeyList = new ArrayList<>();

        if (subjectMenuKeys.length > 0 || subjectMenuKeys != null) subjectKeyList = Arrays.asList(subjectMenuKeys);
        if (teacherKeys.length > 0 || teacherKeys != null) teacherKeyList = Arrays.asList(teacherKeys);
        if (stepCtgKeys.length > 0 || stepCtgKeys != null) stepCtgKeyList = Arrays.asList(stepCtgKeys);


        List<SpecialPackageVO> specialPackageList = productMapper.selectPromotionPackageList(menuCtgKey, stepCtgKeyList,  teacherKeyList, subjectKeyList);
        if (specialPackageList.size() > 0) {
            for (SpecialPackageVO vo : specialPackageList) {
                //할인률 주입
                CalcPriceVO calcPriceVO = productMapper.selectTopCalcPrice(vo.getGKey());
                if (calcPriceVO.getPrice() > 0 && calcPriceVO.getSellPrice() > 0) {
                    vo.setDiscountPercent(Util.getProductDiscountRate(calcPriceVO.getPrice(), calcPriceVO.getSellPrice()) + "할인");
                } else {
                    vo.setDiscountPercent("");
                }
                //동영상, 모바일, 동영상+모바일 리스트 주입
                List<TGoodsPriceOptionVO> videoLectureKindList = productMapper.selectGoodsPriceOptionList2(vo.getGKey());
                vo.setVideoLectureKindList(videoLectureKindList);
                //동영상 종류별 금액 주입하기
                for (TGoodsPriceOptionVO priceOptionVO : videoLectureKindList) {
                    if (priceOptionVO.getKind() == 100) {
                        vo.setPcSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(priceOptionVO.getSellPrice())) + "원");
                    } else if (priceOptionVO.getKind() == 101) {
                        vo.setMobileSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(priceOptionVO.getSellPrice())) + "원");
                    } else if (priceOptionVO.getKind() == 102) {
                        vo.setPcMobileSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(priceOptionVO.getSellPrice())) + "원");
                    }
                }
                //패키지 안에 포함되어 있는 동영상 상품 리스트 가져오기
                List<SpecialPackageLectureVO> packageProductList = productMapper.selectSpecialPackageIncludeProductList(vo.getGKey());
                if (packageProductList.size() > 0) {
                    for (SpecialPackageLectureVO lectureVO : packageProductList) {
                        lectureVO.setImageList(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), lectureVO.getImageList())); //교수 이미지 URL
                        lectureVO.setEmphasisName(EmphasisType.getEmphasisStr(lectureVO.getEmphasis()));    //NEW, BEST 주입

                        //할인률 주입
                        CalcPriceVO calcPriceVO2 = productMapper.selectTopCalcPrice(lectureVO.getGKey());
                        if (calcPriceVO2.getPrice() > 0 && calcPriceVO2.getSellPrice() > 0) {
                            lectureVO.setDiscountPercent(Util.getProductDiscountRate(calcPriceVO.getPrice(), calcPriceVO.getSellPrice()) + "할인");
                        }

                        int deviceCode = 1;
                        if ("MOBILE".equals(device)) deviceCode = 3;
                        List<TLecCurriVO> lectureList = productMapper.selectLectureListFromVideoProduct(lectureVO.getGKey(), deviceCode);   //동영상 상품에 포함된 강좌 목록 가져오기
                        if (lectureList.size() > 0) {
                            for (TLecCurriVO lecCurriVO : lectureList) {
                                lecCurriVO.setNumStr(StringUtils.addZeroTwoDigitUnder(lecCurriVO.getNum()));
                            }
                        }
                        lectureVO.setLectureList(lectureList);
                    }
                }
                vo.setIncludeProductList(packageProductList);
            }
        }
        return new ApiResultListDTO(specialPackageList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getSpecialPackageDetailInfo(int gKey) {
        int resultCode = OK.value();

        SpecialPackageProductVO specialPackageProductVO = null;
        List<TeacherHomeLectureVO> lectureList = new ArrayList<>();
        if (gKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            specialPackageProductVO = productMapper.selectPromotionPackageDetailInfo(gKey);
            if (specialPackageProductVO != null) {
                if (specialPackageProductVO.getPcPrice() > 0 && specialPackageProductVO.getPcSellPrice() > 0) {
                    specialPackageProductVO.setPcDiscountPercent(Util.getProductDiscountRate(specialPackageProductVO.getPcPrice(), specialPackageProductVO.getPcSellPrice()));
                }
                if (specialPackageProductVO.getMobilePrice() > 0 && specialPackageProductVO.getMobileSellPrice() > 0) {
                    specialPackageProductVO.setMobileDiscountPercent(Util.getProductDiscountRate(specialPackageProductVO.getMobilePrice(), specialPackageProductVO.getMobileSellPrice()));
                }
            }

            lectureList = productMapper.selectPromotionPackageTeacherList(gKey);
            for (TeacherHomeLectureVO vo : lectureList) {
                if (vo.getTeacherLectureList().size() > 0) {
                    for (TeacherHomeLectureListVO vo2 : vo.getTeacherLectureList()) {
                        //NEW, BEST 주입
                        vo2.setEmphasisName(EmphasisType.getEmphasisStr(vo2.getEmphasis()));
                        //할인률 주입
                        CalcPriceVO calcPriceVO = productMapper.selectTopCalcPrice(vo2.getGKey());
                        if (calcPriceVO.getPrice() > 0 && calcPriceVO.getSellPrice() > 0) {
                            vo2.setDiscountPercent(Util.getProductDiscountRate(calcPriceVO.getPrice(), calcPriceVO.getSellPrice()) + "할인");
                        } else {
                            vo2.setDiscountPercent("");
                        }
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
                    }
                }
            }
        }
        SpecialPackageDetailVO packageDetailVO = new SpecialPackageDetailVO(specialPackageProductVO, lectureList);
        return new ApiResultObjectDTO(packageDetailVO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getMockExamProductList(int onOffKey, int ctgKey, int sPage, int listLimit, String searchType, String searchText) {
        int resultCode = OK.value();

        int totalCnt = 0;
        List<MockExamProductVO> mockExamProductList = new ArrayList<>();
        int startNumber = getPagingStartNumber(sPage, listLimit);

        if (onOffKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            totalCnt = productMapper.selectMockExamProductListCount(onOffKey, ctgKey, searchType, searchText);
            mockExamProductList = productMapper.selectMockExamProductList(onOffKey, ctgKey, searchType, searchText, startNumber, listLimit);
            if (mockExamProductList.size() > 0) {
                for (MockExamProductVO productVO : mockExamProductList) {
                    TExamMasterVO examVO = examMapper.selectTExamDateInfo(productVO.getExamKey());
                    if (examVO != null) {
                        productVO.setAcceptStartDate(examVO.getAcceptStartDate());
                        productVO.setAcceptEndDate(examVO.getAcceptEndDate());
                        productVO.setStareDate(examVO.getOnlineStartDate());
                        productVO.setEndDate(examVO.getOnlineEndDate());
                        productVO.setClassName(examVO.getClassName());

                        List<TBankSubjectExamLinkVO> examSubjectList = examMapper.selectExamMasterSubjectList(productVO.getExamKey());
                        List<String> ctgNameList = new ArrayList<>();
                        if (examSubjectList.size() > 0) {
                            for (TBankSubjectExamLinkVO examLinkVO : examSubjectList) {
                                ctgNameList.add(examLinkVO.getCtgName());
                            }
                        }
                        String ctgNameStr = StringUtils.implodeList(",", ctgNameList);
                        productVO.setSubjectName(ctgNameStr);
                    }
                }
            }
        }
        return new ApiPagingResultDTO(totalCnt, mockExamProductList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getFreeVideoLectureStepList(int ctgKey, String freeLectureType) {
        List<TypeDTO> stepList = new ArrayList<>();

        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            stepList = productMapper.selectFreeLectureStepList(ctgKey, freeLectureType);
        }
        return new ApiResultListDTO(stepList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getFreeVideoLectureListFromCategoryMenu(int sPage, int listLimit, int ctgKey, int stepCtgKey, String freeLectureType) {
        int resultCode = OK.value();

        int freeLectureListCount = 0;
        List<FreeLectureVO> teacherHomeLectureList = new ArrayList<>();

        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            freeLectureListCount = productMapper.selectFreeLectureListCountFromCategoryMenu(ctgKey, stepCtgKey, freeLectureType);

            int startNumber = PagingSupport.getPagingStartNumber(sPage, listLimit);
            teacherHomeLectureList = productMapper.selectFreeLectureListFromCategoryMenu(startNumber, listLimit, ctgKey, stepCtgKey, freeLectureType);
            if (teacherHomeLectureList.size() > 0) {
                for (FreeLectureVO vo : teacherHomeLectureList) {
                    vo.setFreeThumbnailImg(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getFreeThumbnailImg()));
                    //for (TeacherHomeLectureListVO vo2 : vo.getTeacherLectureList()) {
                        //NEW, BEST 주입

                        //강사 이미지 URL

//                        List<TLecCurriVO> lectureCurriList = productMapper.selectTLecCurriList(vo2.getLecKey(), device);
//                        if (lectureCurriList.size() > 0) {
//                            int num = 1;
//                            for (TLecCurriVO curriVO : lectureCurriList) {
//                                curriVO.setNumStr("0" + num);
//                                curriVO.setDataFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), curriVO.getDataFile()));
//                                num++;
//                            }
//                        }
//                        //무료강의 리스트 주입
//                        vo2.setLectureList(lectureCurriList);
                    }
 //               }
            }
        }
        return new ApiPagingResultDTO(freeLectureListCount, teacherHomeLectureList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getFreeVideoLectureDetailInfo(int lecKey, String device) {
        FreeLectureDetailDTO freeLectureDetailDTO = null;

        if (lecKey == 0 && "".equals(device)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            FreeLectureVO freeLectureInfo = productMapper.selectFreeLectureListFromLecKey(lecKey);
            List<TLecCurriVO>freeLectureList = new ArrayList<>();
            if (freeLectureInfo != null) {
                freeLectureInfo.setFreeThumbnailImg(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), freeLectureInfo.getFreeThumbnailImg()));

                freeLectureList = productMapper.selectTLecCurriList(lecKey, device);
                if (freeLectureList.size() > 0) {
                    int num = 1;
                    for (TLecCurriVO curriVO : freeLectureList) {
                        curriVO.setNumStr(StringUtils.addZeroTwoDigitUnder(num));
                        num++;
                    }
                }
            }
            freeLectureDetailDTO = new FreeLectureDetailDTO(freeLectureInfo, freeLectureList);
        }
        return new ApiResultObjectDTO(freeLectureDetailDTO, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getZianPassProductSubjectList(int parentKey) {
        List<TypeDTO> subjectList = new ArrayList<>();

        if (parentKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            subjectList = productMapper.selectZianPassProductAffiliationList(parentKey, PromotionPmType.ZIAN_PASS.getPromotionPmKey());
        }
        return new ApiResultListDTO(subjectList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getZianPassProductList(int parentKey) {
        int resultCode = OK.value();

        List<ZianPassProductDTO> zianPassProductDTOList = new ArrayList<>();
        if (parentKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            zianPassProductDTOList = productMapper.selectZianPassProductList(parentKey, PromotionPmType.ZIAN_PASS.getPromotionPmKey());
            if (zianPassProductDTOList.size() > 0) {
                for (ZianPassProductDTO productDTO : zianPassProductDTOList) {
                    if (productDTO.getZianPassProductList().size() > 0) {
                        for (ZianPassProductListVO productListVO : productDTO.getZianPassProductList()) {
                            String fileName = "zianpass_" + productListVO.getGKey() + ".html";
                            productListVO.setTargetUrl(ZIAN_PASS_HTML_URL + fileName);
                        }
                    }
                }
            }
        }
        return new ApiResultListDTO(zianPassProductDTOList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getYearMemberProductSubjectList(int parentKey) {
        List<TypeDTO> subjectList = new ArrayList<>();

        if (parentKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            subjectList = productMapper.selectZianPassProductAffiliationList(parentKey, PromotionPmType.YEAR_MEMBER.getPromotionPmKey());
        }
        return new ApiResultListDTO(subjectList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getYearMemberProductList(int parentKey) {
        int resultCode = OK.value();

        List<ZianPassProductDTO> zianPassProductDTOList = new ArrayList<>();
        if (parentKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            zianPassProductDTOList = productMapper.selectZianPassProductList(parentKey, PromotionPmType.YEAR_MEMBER.getPromotionPmKey());
            if (zianPassProductDTOList.size() > 0) {
                for (ZianPassProductDTO productDTO : zianPassProductDTOList) {
                    if (productDTO.getZianPassProductList().size() > 0) {
                        for (ZianPassProductListVO productListVO : productDTO.getZianPassProductList()) {
                            String fileName = "yearmember" + productListVO.getGKey() + ".html";
                            productListVO.setTargetUrl(YEAR_MEMBER_HTML_URL + fileName);
                        }
                    }
                }
            }
        }
        return new ApiResultListDTO(zianPassProductDTOList, resultCode);
    }



    @Transactional(readOnly = true)
    public ApiResultObjectDTO getVideoLectureList(int gKey, String device) {
        int resultCode = OK.value();

        MyLectureListDTO dto = null;
        if (gKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            int deviceCode = 1;
            if ("MOBILE".equals(device)) deviceCode = 3;

            int totalCnt = productMapper.selectLectureListFromVideoProductCount(gKey);
            List<TLecCurriVO>lectureList = productMapper.selectLectureListFromVideoProduct(gKey, deviceCode);
            if (lectureList.size() > 0) {
                for (TLecCurriVO vo : lectureList) {
                    vo.setNumStr(StringUtils.addZeroTwoDigitUnder(vo.getNum()));
                }
            }
            dto = new MyLectureListDTO(totalCnt, lectureList);
        }
        return new ApiResultObjectDTO(dto, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getVideoLectureListByJLecKey(int jLecKey, String device) {
        int resultCode = OK.value();

        MyLectureListDTO dto = null;
        if (jLecKey == 0 && "".equals(device)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            List<TLecCurriVO>lectureList = productMapper.selectVideoLectureListByJLecKey(jLecKey, device);
            int totalCnt = lectureList.size();

            if (lectureList.size() > 0) {
                for (TLecCurriVO vo : lectureList) {
                    vo.setNumStr(StringUtils.addZeroTwoDigitUnder(vo.getNum()));
                }
            }
            dto = new MyLectureListDTO(totalCnt, lectureList);
        }
        return new ApiResultObjectDTO(dto, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getLectureApplySubjectList(int menuCtgKey, String goodsType) {
        List<TCategoryVO> subjectMenuList = new ArrayList<>();

        if (menuCtgKey == 0 ) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            //subjectMenuList = menuMapper.selectTCategoryByParentKey(menuCtgKey);
            subjectMenuList = productMapper.selectLectureApplySubjectListBySubjectKey(menuCtgKey, GoodsType.getGoodsTypeKey(goodsType));
        }
        return new ApiResultListDTO(subjectMenuList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getLectureApplySubjectListFromSubjectKey(int menuCtgKey) {
        List<TCategoryVO> subjectMenuList = new ArrayList<>();

        if (menuCtgKey == 0 ) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            subjectMenuList = menuMapper.selectTCategoryByParentKey(menuCtgKey);
            //applySubjectList = productMapper.selectLectureApplySubjectList(menuCtgKey, GoodsType.getGoodsTypeKey(goodsType));
        }
        return new ApiResultListDTO(subjectMenuList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getLectureApplyTeacherList(int menuCtgKey, String goodsType) {
        List<LectureApplyTeacherVO> applyTeacherList = new ArrayList<>();

        if (menuCtgKey == 0 && "".equals(goodsType)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            applyTeacherList = productMapper.selectLectureApplyTeacherList(menuCtgKey, GoodsType.getGoodsTypeKey(goodsType));
        }
        return new ApiResultListDTO(applyTeacherList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getLectureApplyTeacherTypeList(int menuCtgKey, String[] subjectMenuKeys, String[] teacherKeys, String[] stepCtgKeys, String goodsType) {
        List<LectureApplyProductDTO> lectureApplyProductList = new ArrayList<>();

        if (menuCtgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            //과목가져오기
            List<TCategoryVO> lectureApplySubjectList = menuService.getLectureApplySubjectLeftMenuList(menuCtgKey, goodsType, subjectMenuKeys);
            if (lectureApplySubjectList.size() > 0) {
                List<String>teacherKeyList = new ArrayList<>();
                List<String>stepCtgKeyList = new ArrayList<>();
                if (teacherKeys.length > 0) {
                    teacherKeyList = Arrays.asList(teacherKeys);
                }
                if (stepCtgKeys.length > 0 || stepCtgKeys != null) {
                    stepCtgKeyList = Arrays.asList(stepCtgKeys);
                }

                for (TCategoryVO subjectVO : lectureApplySubjectList) {
                    LectureApplyProductDTO productDTO = new LectureApplyProductDTO();

                    List<LectureApplyTeacherTypeVO> teacherTypeList = new ArrayList<>();
                    List<LectureApplyAcademyListVO> academyList = new ArrayList<>();

                    if (GoodsType.getGoodsTypeKey(goodsType) == 1) {
                        teacherTypeList = productMapper.selectLectureApplyTeacherTypeList2(menuCtgKey, subjectVO.getCtgKey(), teacherKeyList, stepCtgKeyList);
                        if (teacherTypeList.size() > 0) {
                            for (LectureApplyTeacherTypeVO teacherTypeVO : teacherTypeList) {
                                teacherTypeVO.setImageTeacherList(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), teacherTypeVO.getImageTeacherList()));
                                if (subjectVO.getCtgKey() == teacherTypeVO.getSubjectCtgKey()) {
                                    productDTO.setTeacherTypeInfo(teacherTypeList);
                                }
                                List<TeacherHomeLectureVO> videoLectureList = this.getLectureApplyVideoLectureListFromCategoryMenu(teacherTypeVO.getSubjectCtgKey(), stepCtgKeys, teacherTypeVO.getTeacherKey());
                                teacherTypeVO.setVideoLectureInfo(videoLectureList);
                            }
                        }
                    } else if (GoodsType.getGoodsTypeKey(goodsType) == 2) {
                        List<LectureApplyAcademyListVO> lectureApplyAcademyListVOS = new ArrayList<>();
                        academyList = productMapper.selectAcademyLectureListFromCategoryMenuAtLectureApply(menuCtgKey, stepCtgKeyList, teacherKeyList);
                        for (LectureApplyAcademyListVO academyVO : academyList) {
                            LectureApplyAcademyListVO lectureApplyAcademyListVO = new LectureApplyAcademyListVO();
                            if (subjectVO.getCtgKey() == academyVO.getSubjectCtgKey()) {
                                academyVO.setImageView(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), academyVO.getImageView()));
                                academyVO.setEmphasisStr(EmphasisType.getEmphasisStr(academyVO.getEmphasis()));
                                if (academyVO.getPrice() > 0 && academyVO.getSellPrice2() > 0) {
                                    academyVO.setDiscountPercent(Util.getProductDiscountRate(academyVO.getPrice(), academyVO.getSellPrice2()) + " 할인");
                                }
                                lectureApplyAcademyListVO = academyVO;
                                lectureApplyAcademyListVOS.add(lectureApplyAcademyListVO);
                                productDTO.setAcademyLectureInfo(lectureApplyAcademyListVOS);
                            }
                            //선생님 목록
//                            List<TeacherInfoVO>teacherInfoList = productMapper.selectAcademyLectureTeacherList(academyVO.getGKey());
//                            if (teacherInfoList.size() > 0) {
//                                for (TeacherInfoVO teacherInfoVO : teacherInfoList) {
//                                    teacherInfoVO.setTeacherImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), teacherInfoVO.getTeacherImage()));
//                                }
//                                academyVO.setTeacherInfoList(teacherInfoList);
//                            }
                        }
                    }
                    productDTO.setSubjectName(subjectVO.getName());
                    productDTO.setSubjectKey(subjectVO.getCtgKey());

                    lectureApplyProductList.add(productDTO);
                }
            }
        }
        return new ApiResultObjectDTO(lectureApplyProductList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getSpecialPackageSubjectList(int menuCtgKey) {
        List<TCategoryVO> subjectMenuList = new ArrayList<>();

        if (menuCtgKey == 0 ) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            List<String>subjectMenuKeys = new ArrayList<>();
            subjectMenuList = productMapper.selectSpecialPackageSubjectListFromSearch(menuCtgKey, subjectMenuKeys);
        }
        return new ApiResultListDTO(subjectMenuList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getSpecialPackageTeacherList(int menuCtgKey) {
        List<LectureApplyTeacherVO> teacherList = new ArrayList<>();

        if (menuCtgKey == 0 ) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            teacherList = productMapper.selectSpecialPackageTeacherList(menuCtgKey);
        }
        return new ApiResultListDTO(teacherList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getPackagePriceKey(int gKey) {
        int priceKey = 0;

        if (gKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            priceKey = productMapper.selectPackagePriceKey(gKey);
        }
        return new ApiResultObjectDTO("PRICE_KEY", priceKey);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getGoodsInfoByJLecKey(int jLecKey) {
        int resultCode = OK.value();

        VideoLectureDetailVO vo = null;
        if (jLecKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            vo = productMapper.selectGoodsInfoByJLecKey(jLecKey);
        }
        return new ApiResultObjectDTO(vo, resultCode);
    }

}
