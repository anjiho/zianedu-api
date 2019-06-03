package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.datasource.BbsMasterKeyType;
import com.zianedu.api.define.datasource.EmphasisType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.mapper.BoardMapper;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.mapper.TeacherMapper;
import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.utils.StringUtils;
import com.zianedu.api.utils.Util;
import com.zianedu.api.utils.ZianApiUtils;
import com.zianedu.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private ProductMapper productMapper;

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
