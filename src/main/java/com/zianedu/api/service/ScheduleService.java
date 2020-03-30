package com.zianedu.api.service;

import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.vo.TOrderLecVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    protected static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Autowired
    private ProductMapper productMapper;

    //일시정지 요청 마감된 강좌 일시정지 해제
    public void stopReleaseAtStopTermDeadLine() {
        logger.info("=================== 일시정지 해제 스케쥴링 시작 ================================");
        logger.info("=================== 해제 목록 가져오기 ================================");
        List<TOrderLecVO> stopTermDeadLineLectureList = productMapper.selectTOrderLecListAtStopRelease();
        int i=0;
        if (stopTermDeadLineLectureList.size() > 0) {
            logger.info("=================== 해제 시작 ================================");
            for (TOrderLecVO releaseLecture : stopTermDeadLineLectureList) {
                logger.info("=================== 강좌키 >> " + releaseLecture.getJLecKey() + " ================================");
                productMapper.updateTOrderLecPauseCnt(releaseLecture.getJLecKey(), 0, releaseLecture.getPauseDay());
                i++;
            }
            logger.info("=================== " + i +"개의 강좌 일시해제 ================================");
        } else {
            logger.info("=================== 해제 목록 없음 ================================");
        }
        logger.info("=================== 일시정지 해제 스케쥴링 끝 ================================");
    }
}
