package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.datasource.LectureStatusType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiPagingResultDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.dto.VideoProductDTO;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.utils.FileUtil;
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


}
