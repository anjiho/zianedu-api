package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.datasource.EmphasisType;
import com.zianedu.api.define.datasource.LectureStatusType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiPagingResultDTO;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.dto.VideoProductDTO;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.utils.StringUtils;
import com.zianedu.api.utils.Util;
import com.zianedu.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

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
                academyLectureDetailVO.setDiscountPercent(
                        Util.getProductDiscountRate(academyLectureDetailVO.getPrice(), academyLectureDetailVO.getSellPrice())
                );
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
    public ApiResultListDTO getSpecialPackageList() {
        int resultCode = OK.value();

        List<SpecialPackageVO> specialPackageList = productMapper.selectPromotionPackageList();
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
                List<TGoodsPriceOptionVO> videoLectureKindList = productMapper.selectGoodsPriceOptionList(vo.getGKey());
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
                specialPackageProductVO.setPcDiscountPercent(Util.getProductDiscountRate(specialPackageProductVO.getPcPrice(), specialPackageProductVO.getPcSellPrice()));
                specialPackageProductVO.setMobileDiscountPercent(Util.getProductDiscountRate(specialPackageProductVO.getMobilePrice(), specialPackageProductVO.getMobileSellPrice()));
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

}
