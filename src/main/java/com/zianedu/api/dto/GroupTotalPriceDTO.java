package com.zianedu.api.dto;

import com.zianedu.api.utils.StringUtils;
import lombok.Data;

/**
 * 주문상품정보 상품별 가격 합계 모델링
 */
@Data
public class GroupTotalPriceDTO {

    private String videoTotalPriceName;

    private String academyTotalPriceName;

    private String promotionTotalPriceName;

    private String bookTotalPriceName;

    private String examTotalPriceName;

    private String retakeTotalPriceName;

    private String deliveryPriceName;

    public GroupTotalPriceDTO(){}

    public GroupTotalPriceDTO(int videoTotalPrice, int academyTotalPrice, int promotionTotalPrice,
                              int bookTotalPrice, int examTotalPrice, int deliveryPrice, int retakePrice) {

        this.videoTotalPriceName = videoTotalPrice == 0 ? null : StringUtils.addThousandSeparatorCommas(String.valueOf(videoTotalPrice)) + "원";
        this.academyTotalPriceName = academyTotalPrice == 0 ? null : StringUtils.addThousandSeparatorCommas(String.valueOf(academyTotalPrice)) + "원";
        this.promotionTotalPriceName = promotionTotalPrice == 0 ? null : StringUtils.addThousandSeparatorCommas(String.valueOf(promotionTotalPrice)) + "원";
        this.bookTotalPriceName = bookTotalPrice == 0 ? null : StringUtils.addThousandSeparatorCommas(String.valueOf(bookTotalPrice)) + "원";
        this.examTotalPriceName = examTotalPrice == 0 ? null : StringUtils.addThousandSeparatorCommas(String.valueOf(examTotalPrice)) + "원";
        this.deliveryPriceName = deliveryPrice == 0 ? null : StringUtils.addThousandSeparatorCommas(String.valueOf(deliveryPrice)) + "원";
        this.retakeTotalPriceName = deliveryPrice == 0 ? null : StringUtils.addThousandSeparatorCommas(String.valueOf(retakePrice)) + "원";
    }

}
