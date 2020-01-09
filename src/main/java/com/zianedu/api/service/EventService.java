package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiPagingResultDTO;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.mapper.EventMapper;
import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.utils.PagingSupport;
import com.zianedu.api.utils.ZianUtils;
import com.zianedu.api.vo.TEventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.security.krb5.Config;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class EventService extends PagingSupport {

    @Autowired
    private EventMapper eventMapper;

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getEventList(String eventType,  int sPage, int listLimit, String searchType, String searchText) {
        int resultCode = OK.value();

        int totalCnt = 0;
        List<TEventVO> eventList = new ArrayList<>();
        int startNumber = getPagingStartNumber(sPage, listLimit);

        if ("".equals(eventType) && sPage == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            totalCnt = eventMapper.selectEventListCount(eventType, searchType, searchText);
            eventList = eventMapper.selectEventList(eventType, startNumber, listLimit, searchType, searchText);
            if (eventList.size() > 0) {
                for (TEventVO eventVO : eventList) {
                    eventVO.setThumbnailPath(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), eventVO.getThumbnailPath()));
                }
            }
        }
        return new ApiPagingResultDTO(totalCnt, eventList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getEventDetailInfo(int eventIdx) {
        int resultCode = OK.value();

        TEventVO eventInfo = new TEventVO();
        if (eventIdx == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            eventInfo = eventMapper.selectEventDetailInfo(eventIdx);
            if (eventInfo != null) {
                eventInfo.setThumbnailFileName(ZianUtils.getFileNameFromPath(eventInfo.getThumbnailPath()));
                eventInfo.setThumbnailPath(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), eventInfo.getThumbnailPath()));
            }
        }
        return new ApiResultObjectDTO(eventInfo, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO saveEventInfo(String eventTitle, String eventDesc, String eventStartDate, String eventEndDate,
                                          String thumbnailFileName, String targetUrl, String targetName) {
        int resultCode = OK.value();

        int idx = 0;
        if ("".equals(eventStartDate) && "".equals(eventEndDate)) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TEventVO eventVO = new TEventVO(
                    eventTitle, eventDesc, eventStartDate, eventEndDate, thumbnailFileName, targetUrl, targetName
            );
            eventMapper.insertTEvent(eventVO);
            idx = eventVO.getIdx();
        }
        return new ApiResultCodeDTO("RESULT", idx, resultCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO updateEventInfo(int idx, String eventTitle, String eventDesc, String eventStartDate,
                                            String eventEndDate, String thumbnailFileName, String targetUrl, String targetName) {
        int resultCode = OK.value();

        if (idx == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TEventVO eventVO = new TEventVO(
                    idx, eventTitle, eventDesc, eventStartDate, eventEndDate, thumbnailFileName, targetUrl, targetName
            );
            eventMapper.updateTEvent(eventVO);
        }
        return new ApiResultCodeDTO("RESULT", idx, resultCode);
    }

}
