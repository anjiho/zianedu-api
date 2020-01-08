package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiPagingResultDTO;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.EventService;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/eventList/{eventType}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("이벤트 > 이벤트 리스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eventType", value = "이벤트 종류(PROCEED : 진행중 이벤트, DEAD : 마감된 이벤트) ", dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "리스트 개수", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "searchType", value = "검색 종류(title : 제목, content : 내용) ", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "searchText", value = "검색 값", dataType = "string", paramType = "query", required = false)
    })
    public ApiPagingResultDTO eventList(@PathVariable("eventType") String eventType,
                                                 @RequestParam("sPage") int sPage,
                                                 @RequestParam("listLimit") int listLimit,
                                                 @RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
                                                 @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText) {
        return eventService.getEventList(eventType, sPage, listLimit, searchType, searchText);
    }

    @RequestMapping(value = "/eventDetailInfo/{idx}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("이벤트 > 이벤트 상세정보")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idx", value = "이벤트 키값", dataType = "string", paramType = "path", required = true)
    })
    public ApiResultObjectDTO eventList(@PathVariable("idx") int idx) {
        return eventService.getEventDetailInfo(idx);
    }

    @RequestMapping(value = "/saveEventInfo", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("이벤트 정보 저장")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eventTitle", value = "제목", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "eventDesc", value = "설명", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "eventStartDate", value = "기간(시작일)", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "eventEndDate", value = "기간(종료일)", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "targetName", value = "대상", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "thumbnailFileName", value = "썸네일파일명", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "targetUrl", value = "URL경로", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultCodeDTO saveEventInfo(@RequestParam("eventTitle") String eventTitle,
                                            @RequestParam("eventDesc") String eventDesc,
                                            @RequestParam("eventStartDate") String eventStartDate,
                                            @RequestParam("eventEndDate") String eventEndDate,
                                            @RequestParam("targetName") String targetName,
                                            @RequestParam("thumbnailFileName") String thumbnailFileName,
                                            @RequestParam("targetUrl") String targetUrl) {
        return eventService.saveEventInfo(eventTitle, eventDesc, eventStartDate, eventEndDate, thumbnailFileName, targetUrl, targetName);
    }

    @RequestMapping(value = "/updateEventInfo", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("이벤트 정보 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idx", value = "이벤트 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "eventTitle", value = "제목", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "eventDesc", value = "설명", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "eventStartDate", value = "기간(시작일)", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "eventEndDate", value = "기간(종료일)", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "targetName", value = "대상", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "thumbnailFileName", value = "썸네일파일명", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "targetUrl", value = "URL경로", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultCodeDTO updateEventInfo(@RequestParam("idx") int idx,
                                          @RequestParam("eventTitle") String eventTitle,
                                          @RequestParam("eventDesc") String eventDesc,
                                          @RequestParam("eventStartDate") String eventStartDate,
                                          @RequestParam("eventEndDate") String eventEndDate,
                                          @RequestParam("targetName") String targetName,
                                          @RequestParam("thumbnailFileName") String thumbnailFileName,
                                          @RequestParam("targetUrl") String targetUrl) {
        return eventService.updateEventInfo(idx, eventTitle, eventDesc, eventStartDate, eventEndDate, thumbnailFileName, targetUrl, targetName);
    }

}
