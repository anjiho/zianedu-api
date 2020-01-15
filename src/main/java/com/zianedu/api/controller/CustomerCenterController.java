package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiPagingResultDTO;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.service.CustomerCenterService;
import com.zianedu.api.utils.GsonUtil;
import com.zianedu.api.utils.ZianApiUtils;
import com.zianedu.api.vo.TConsultReserveVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customerCenter")
public class CustomerCenterController {

    @Autowired
    private CustomerCenterService customerCenterService;

    @RequestMapping(value = "/getOftenQuestionDetailList/{ctgKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("자주하는 질문(세부질문) 목록 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ctgKey", value = "카테고리 키값", dataType = "in", paramType = "path", required = true)
    })
    public ApiResultListDTO getOftenQuestionDetailList(@PathVariable("ctgKey") int ctgKey) {
        return customerCenterService.getOftenQuestionDetailList(ctgKey);
    }

    @RequestMapping(value = "/reserveConsult", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("상담예약하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "reserveDate", value = "예약월(YYYY-MM-DD)", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "reserveTimeKey", value = "예약시간 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "reserveType", value = "예약종류 키값", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "reserveLocation", value = "학원관(1관:1, 2관:2)", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "userName", value = "예약자명", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "mobileNumber", value = "핸드폰번호", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "emailAddress", value = "이메일주소", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "ctgKey1", value = "분류1", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "ctgKey2", value = "분류2", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "ctgKey3", value = "분류3", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "reserveContents", value = "상담요청내용", dataType = "string", paramType = "query", required = true)
    })
    public ApiResultCodeDTO saveBoardFileList(@ModelAttribute TConsultReserveVO tConsultReserveVO) {
        return customerCenterService.reserveConsult(tConsultReserveVO);
    }

    @RequestMapping(value = "/getReserveTime", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("예약시간 목록 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reserveDate", value = "예약월(YYYY-MM-DD)", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "reserveLocation", value = "학원관(1관:1, 2관:2)", dataType = "int", paramType = "query", required = true)
    })
    public ApiResultListDTO getReserveTime(@RequestParam("reserveDate") String reserveDate,
                                           @RequestParam("reserveLocation") int reserveLocation) {
        return customerCenterService.getConsultTimeList(reserveDate, reserveLocation);
    }

    @RequestMapping(value = "/getConsultReserveList/{userKey}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("상담예약 목록 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userKey", value = "사용자 키값", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "reserveStartDate", value = "예약월(YYYY-MM-DD)", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "reserveEndDate", value = "예약월(YYYY-MM-DD)", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "sPage", value = "페이징 시작 넘버", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "listLimit", value = "리스트 개수", dataType = "int", paramType = "query", required = true)
    })
    public ApiPagingResultDTO getConsultReserveList(@PathVariable("userKey") int userKey,
                                                    @RequestParam("reserveStartDate") String reserveStartDate,
                                                    @RequestParam("reserveEndDate") String reserveEndDate,
                                                    @RequestParam("sPage") int sPage,
                                                    @RequestParam("listLimit") int listLimit) {
        return customerCenterService.getConsultList(userKey, reserveStartDate, reserveEndDate, sPage, listLimit);
    }

    @RequestMapping(value = "/changeConsultReserveStatus", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("상담예약 상태변경하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idxList", value = "상담예약내역 선택된 인덱스 값 목록 > [1234,1234,...]", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "reserveStatusType", value = "1:상담대기, 2:상담완료, 3:예약취소 ", dataType = "string", paramType = "query", required = true),
    })
    public ApiResultCodeDTO changeConsultReserveStatus(@RequestParam("idxList") String idxList,
                                                       @RequestParam("reserveStatusType") int reserveStatusType) {
        Integer[] idxArray = GsonUtil.convertToIntegerArrayFromString(idxList);
        return customerCenterService.changeConsultReserveStatus(idxArray, reserveStatusType);
    }

    @RequestMapping(value = "/getCounselReserveDetailInfo/{idx}", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("상담예약 상세정보 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idx", value = "상담예약 키값", dataType = "int", paramType = "path", required = true)
    })
    public ApiResultObjectDTO getCounselReserveDetailInfo(@PathVariable("idx") int idx) {
        return customerCenterService.getCounselReserveDetailInfo(idx);
    }
}
