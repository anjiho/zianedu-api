package com.zianedu.api.service;

import com.zianedu.api.define.datasource.GoodsType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.CartResultDTO;
import com.zianedu.api.mapper.OrderMapper;
import com.zianedu.api.vo.CartListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class OrderService {

    protected final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderMapper orderMapper;

    @Transactional(readOnly = true)
    public CartResultDTO getUserCartList(int userKey) {
        int resultCode = OK.value();

        int orderPrice = 0;
        int deliveryPrice = 0;

        List<CartListVO>academyCartInfo = new ArrayList<>();
        List<CartListVO>videoCartInfo = new ArrayList<>();
        List<CartListVO>promotionCartInfo = new ArrayList<>();
        List<CartListVO>bookCartInfo = new ArrayList<>();
        List<CartListVO>examCartInfo = new ArrayList<>();

        if (userKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            academyCartInfo = orderMapper.selectCartList(userKey, GoodsType.ACADEMY.getGoodsTypeKey());
            videoCartInfo = orderMapper.selectCartList(userKey, GoodsType.VIDEO.getGoodsTypeKey());
            promotionCartInfo = orderMapper.selectCartList(userKey, GoodsType.PACKAGE.getGoodsTypeKey());
            bookCartInfo = orderMapper.selectCartList(userKey, GoodsType.BOOK.getGoodsTypeKey());
            examCartInfo = orderMapper.selectCartList(userKey, GoodsType.EXAM.getGoodsTypeKey());

            if (academyCartInfo.size() > 0) {
                for (CartListVO academyList : academyCartInfo) {
                    orderPrice += academyList.getSellPrice();
                }
            }

            if (videoCartInfo.size() > 0) {
                for (CartListVO videoList : videoCartInfo) {
                    orderPrice += videoList.getSellPrice();
                }
            }

            if (promotionCartInfo.size() > 0) {
                for (CartListVO promotionList : promotionCartInfo) {
                    if (promotionList.getKind() == 0) orderPrice += promotionList.getLinkSellPrice();
                    else if (promotionList.getKind() == 12) orderPrice += promotionList.getSellPrice();
                }
            }

            if (bookCartInfo.size() > 0) {
                for (CartListVO bookList : bookCartInfo) {
                    orderPrice += bookList.getSellPrice();
                }
                //배송비 추가
                deliveryPrice = 2500;
            }

            if (examCartInfo.size() > 0) {
                for (CartListVO examList : examCartInfo) {
                    orderPrice += examList.getSellPrice();
                }
            }
            logger.info("orderPrice ===============>" + orderPrice);
        }
        CartResultDTO cartResultDTO = new CartResultDTO(

        );
        return null;
    }
}
