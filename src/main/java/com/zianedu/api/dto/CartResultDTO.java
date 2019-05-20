package com.zianedu.api.dto;

import com.zianedu.api.utils.StringUtils;
import com.zianedu.api.vo.CartListVO;
import lombok.Data;

import java.util.List;

@Data
public class CartResultDTO {

    private int orderPrice;

    private int deliveryPrice;

    private int totalPrice;

    private int totalPoint;

    private String orderPriceName;

    private String deliveryPriceName;

    private String totalPriceName;

    private String totalPointName;

    private List<CartListVO> academyCartInfo;

    private List<CartListVO> videoCartInfo;

    private List<CartListVO> promotionCartInfo;

    private List<CartListVO> bookCartInfo;

    private List<CartListVO> examCartInfo;

    public CartResultDTO() {}

    public CartResultDTO(int orderPrice, int deliveryPrice, int totalPrice, int totalPoint,
                         List<CartListVO>academyCartInfo, List<CartListVO>videoCartInfo,
                         List<CartListVO>promotionCartInfo, List<CartListVO>bookCartInfo, List<CartListVO>examCartInfo) {
        this.orderPrice = orderPrice;
        this.deliveryPrice = deliveryPrice;
        this.totalPrice = totalPrice;
        this.totalPoint = totalPoint;
        this.orderPriceName = StringUtils.addThousandSeparatorCommas(String.valueOf(orderPrice)) + "원";
        this.deliveryPriceName = StringUtils.addThousandSeparatorCommas(String.valueOf(deliveryPrice)) + "원";
        this.totalPriceName = StringUtils.addThousandSeparatorCommas(String.valueOf(totalPrice)) + "원";
        this.totalPointName = StringUtils.addThousandSeparatorCommas(String.valueOf(totalPoint)) + "점";
        this.academyCartInfo = academyCartInfo;
        this.videoCartInfo = videoCartInfo;
        this.promotionCartInfo = promotionCartInfo;
        this.bookCartInfo = bookCartInfo;
        this.examCartInfo = examCartInfo;
    }

}
