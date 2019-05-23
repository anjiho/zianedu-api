package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.mapper.BannerMapper;
import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.vo.BannerVO;
import com.zianedu.api.vo.PopupVO;
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
}
