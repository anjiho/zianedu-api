package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.mapper.BannerMapper;
import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.utils.StringUtils;
import com.zianedu.api.vo.BannerVO;
import com.zianedu.api.vo.PopulateLectureVO;
import com.zianedu.api.vo.PopupVO;
import com.zianedu.api.vo.TeacherBannerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getMainPageTopBanner(int ctgKey) {
        int resultCode = OK.value();

        BannerVO bannerInfo = new BannerVO();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            bannerInfo = bannerMapper.selectBannerInfoBySingle(ctgKey);
            if (!"".equals(bannerInfo.getFileUrl())) {
                bannerInfo.setFullFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), bannerInfo.getFileUrl()));
            }
        }
        return new ApiResultObjectDTO(bannerInfo, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getBanner(int ctgKey) {
        int resultCode = OK.value();

        List<BannerVO> bannerList = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            bannerList = bannerMapper.selectBannerList(ctgKey);
            for (BannerVO vo : bannerList) {
                if (!"".equals(vo.getFileUrl())) {
                    vo.setFullFileUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getFileUrl()));
                }
            }
        }
        return new ApiResultListDTO(bannerList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getPopup(int ctgKey) {
        int resultCode = OK.value();

        List<PopupVO> popupList = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            popupList = bannerMapper.selectPopupList(ctgKey);
        }
        return new ApiResultListDTO(popupList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getTeacherBannerList(int ctgKey) {
        int resultCode = OK.value();

        List<TeacherBannerVO>teacherBannerList = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            teacherBannerList = bannerMapper.selectTeacherBannerList(ctgKey);
            if (teacherBannerList.size() > 0) {
                for (TeacherBannerVO vo : teacherBannerList) {
                    vo.setTeacherImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getTeacherImage()));
                }
            }
        }
        return new ApiResultListDTO(teacherBannerList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getPopulateAcademyLectureList(int ctgKey) {
        int resultCode = OK.value();

        List<PopulateLectureVO>populateLectureList = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            populateLectureList = bannerMapper.selectPopulateAcademyLectureList(ctgKey);
            if (populateLectureList.size() > 0) {
                for (PopulateLectureVO vo : populateLectureList) {
                    vo.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(vo.getPrice())) + "원");
                    vo.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(vo.getSellPrice())) + "원");
                    vo.setTeacherImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getTeacherImage()));

                    TeacherBannerVO teacherBannerVO = bannerMapper.selectTeacherNameAndSubjectName(vo.getTeacherKey());
                    if (teacherBannerVO != null) {
                        vo.setTeacherName(teacherBannerVO.getTeacherName());
                        vo.setSubjectName(teacherBannerVO.getSubjectName());
                    }
                }
            }
        }
        return new ApiResultListDTO(populateLectureList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getPopulateVideoLectureList(int ctgKey) {
        int resultCode = OK.value();

        List<PopulateLectureVO>populateLectureList = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            populateLectureList = bannerMapper.selectPopulateVideoLectureList(ctgKey);
            if (populateLectureList.size() > 0) {
                for (PopulateLectureVO vo : populateLectureList) {
                    vo.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(vo.getPrice())) + "원");
                    vo.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(vo.getSellPrice())) + "원");
                    vo.setTeacherImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getTeacherImage()));

                    String subjectName = bannerMapper.selectCtgNameAtLectureBannerList(vo.getGKey());
                    if (!"".equals(subjectName)) {
                        vo.setSubjectName(subjectName);
                    }
                }
            }
        }
        return new ApiResultListDTO(populateLectureList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getPackageLectureList(int ctgKey) {
        int resultCode = OK.value();

        List<PopulateLectureVO>packageLectureList = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            packageLectureList = bannerMapper.selectPackageLectureList(ctgKey);
            if (packageLectureList.size() > 0) {
                for (PopulateLectureVO vo : packageLectureList) {
                    vo.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(vo.getPrice())) + "원");
                    vo.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(vo.getSellPrice())) + "원");
                }
            }
        }
        return new ApiResultListDTO(packageLectureList, resultCode);
    }
}
