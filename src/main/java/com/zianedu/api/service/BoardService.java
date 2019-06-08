package com.zianedu.api.service;

import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.mapper.BoardMapper;
import com.zianedu.api.utils.PagingSupport;
import com.zianedu.api.vo.TBbsDataVO;
import com.zianedu.api.vo.TGoodsReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class BoardService extends PagingSupport {

    @Autowired
    private BoardMapper boardMapper;

    /**
     * 리뷰 저장
     * @param gKey
     * @param userKey
     * @param title
     * @param contents
     * @param starPoint
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResultCodeDTO saveProductReview(int gKey, int userKey, String title, String contents, int starPoint) {
        int resultCode = OK.value();

        int gReviewKey = 0;
        if (gKey == 0 && userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            TGoodsReviewVO vo = new TGoodsReviewVO(gKey, userKey, title, contents, starPoint);
            boardMapper.insertTGoodsReview(vo);
            gReviewKey = vo.getGReviewKey();
        }
        return new ApiResultCodeDTO("gReviewKey", gReviewKey, resultCode);
    }

}
