package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.datasource.NoticeMasterKeyType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.dto.BannerNoticeDTO;
import com.zianedu.api.mapper.BannerMapper;
import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.utils.StringUtils;
import com.zianedu.api.utils.Util;
import com.zianedu.api.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@Service
public class BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private MenuService menuService;

    @Transactional(readOnly = true)
    public ApiResultListDTO getMainPageCtgKeyInfo(int ctgKey) {
        int resultCode = OK.value();

        List<TCategoryVO>tCategoryList = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            tCategoryList = bannerMapper.selectMainPageCtgKeyList(ctgKey);
        }
        return new ApiResultListDTO(tCategoryList, resultCode);
    }

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
    public ApiResultListDTO getBanner(int ctgKey, int listNumber) {
        int resultCode = OK.value();

        List<BannerVO> bannerList = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            bannerList = bannerMapper.selectBannerList(ctgKey, listNumber);
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
    public ApiResultListDTO getTeacherBannerList(int ctgKey, int subjectType) throws Exception {
        int resultCode = OK.value();

        List<TeacherBannerVO>teacherBannerList = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            teacherBannerList = bannerMapper.selectTeacherBannerList(ctgKey, subjectType);
            if (teacherBannerList.size() > 0) {
                for (TeacherBannerVO vo : teacherBannerList) {
                    if (vo.getTargetUrl().contains("&")) {
                        URL url = new URL(vo.getTargetUrl());
                        Map<String, List<String>> urlParamMap = Util.splitQuery(url);
                        int parentMnk = Integer.parseInt(urlParamMap.get("parent_mnk").get(0));
                        vo.setParentMnk(parentMnk);
                    }
                    vo.setTeacherImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getTeacherImage()));

                    TTeacherVO teacherInfo = teacherService.getTeacherInfo(vo.getTeacherKey());
                    vo.setTeacherInfo(teacherInfo);
                }
            }
        }
        return new ApiResultListDTO(teacherBannerList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getTeacherBannerList2(int ctgKey, int subjectType, int teacherMenuCtgKey) throws Exception {
        int resultCode = OK.value();

        List<TeacherBannerVO>teacherBannerList = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            List<TCategoryVO> teacherIntroduceList = menuService.getTeacherIntroduceLeftMenuByTeacherBanner(teacherMenuCtgKey);
            teacherBannerList = bannerMapper.selectTeacherBannerList(ctgKey, subjectType);
            if (teacherBannerList.size() > 0) {
                for (TeacherBannerVO vo : teacherBannerList) {
                    for (TCategoryVO tCategoryVO : teacherIntroduceList) {
                        List<TeacherVO> teacherList = tCategoryVO.getTeacherList();
                        for (TeacherVO teacherVO : teacherList) {
                            if (teacherVO.getTeacherKey() == vo.getTeacherKey()) {
                                vo.setReqKey(teacherVO.getReqKey());
                            }
                        }
                    }

                    if (vo.getTargetUrl().contains("&")) {
                        URL url = new URL(vo.getTargetUrl());
                        Map<String, List<String>> urlParamMap = Util.splitQuery(url);
                        int parentMnk = Integer.parseInt(urlParamMap.get("parent_mnk").get(0));
                        vo.setParentMnk(parentMnk);
                    }
                    vo.setTeacherImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getTeacherImage()));

                    TTeacherVO teacherInfo = teacherService.getTeacherInfo(vo.getTeacherKey());
                    vo.setTeacherInfo(teacherInfo);
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

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getBannerNoticeList(String subject) {
        int resultCode = OK.value();
        BannerNoticeDTO bannerNoticeInfo = null;

        if ("".equals(subject)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            List<NoticeVO> subjectOpenNoticeInfo = new ArrayList<>();
            List<NoticeVO> academyInfo = new ArrayList<>();
            List<NoticeVO> videoInfo = new ArrayList<>();
            if ("PUBLIC".equals(subject.toUpperCase())) {
                subjectOpenNoticeInfo = bannerMapper.selectBannerNoticeList(NoticeMasterKeyType.PUBLIC_SUBJECT_OPEN.getNoticeMasterKey());
                academyInfo = bannerMapper.selectBannerNoticeList(NoticeMasterKeyType.PUBLIC_ACADEMY_INFO.getNoticeMasterKey());
                videoInfo = bannerMapper.selectBannerNoticeList(NoticeMasterKeyType.PUBLIC_VIDEO_INFO.getNoticeMasterKey());
            } else if ("TECH".equals(subject.toUpperCase())) {
                subjectOpenNoticeInfo = bannerMapper.selectBannerNoticeList(NoticeMasterKeyType.TECH_SUBJECT_OPEN.getNoticeMasterKey());
                academyInfo = bannerMapper.selectBannerNoticeList(NoticeMasterKeyType.TECH_ACADEMY_INFO.getNoticeMasterKey());
                videoInfo = bannerMapper.selectBannerNoticeList(NoticeMasterKeyType.TECH_ACADEMY_INFO.getNoticeMasterKey());
            } else if ("POST".equals(subject.toUpperCase())) {
                subjectOpenNoticeInfo = bannerMapper.selectBannerNoticeList(NoticeMasterKeyType.POST_SUBJECT_OPEN.getNoticeMasterKey());
                academyInfo = bannerMapper.selectBannerNoticeList(NoticeMasterKeyType.POST_ACADEMY_INFO.getNoticeMasterKey());
                videoInfo = bannerMapper.selectBannerNoticeList(NoticeMasterKeyType.POST_VIDEO_INFO.getNoticeMasterKey());
            }
            bannerNoticeInfo = new BannerNoticeDTO(subjectOpenNoticeInfo, academyInfo, videoInfo);
        }
        return new ApiResultObjectDTO(bannerNoticeInfo, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getBannerBookList() {
        int resultCode = OK.value();

        List<BannerBookVO> bannerBookList = bannerMapper.selectBannerBookList();
        if (bannerBookList.size() > 0) {
            for (BannerBookVO vo : bannerBookList) {
                vo.setSellPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(vo.getSellPrice())) + "원");
                vo.setPriceName(StringUtils.addThousandSeparatorCommas(String.valueOf(vo.getPrice())) + "원");
                vo.setImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getImageList()));
            }
        }
        return new ApiResultListDTO(bannerBookList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getSearchKeywordList(String className) {
        int resultCode = OK.value();

        List<TSearchKeywordVO> searchKeywordList = new ArrayList<>();
        if ("".equals(className)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            searchKeywordList = bannerMapper.selectSearchKeywordList(className.toLowerCase());
        }
        return new ApiResultListDTO(searchKeywordList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getExamScheduleList() {
        int resultCode = OK.value();
        List<TScheduleVO> examScheduleList = bannerMapper.selectTScheduleList();
        for (TScheduleVO scheduleVO : examScheduleList) {
            scheduleVO.setDDay("D-" + scheduleVO.getDay());
        }
        return new ApiResultListDTO(examScheduleList, resultCode);
    }

}
