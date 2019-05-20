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

    private String deliveryPriceName;

    public GroupTotalPriceDTO(){}

    public GroupTotalPriceDTO(int videoTotalPrice, int academyTotalPrice, int promotionTotalPrice,
                              int bookTotalPrice, int examTotalPrice, int deliveryPrice) {

        this.videoTotalPriceName = StringUtils.addThousandSeparatorCommas(String.valueOf(videoTotalPrice)) + "원";
        this.academyTotalPriceName = StringUtils.addThousandSeparatorCommas(String.valueOf(academyTotalPrice)) + "원";
        this.promotionTotalPriceName = StringUtils.addThousandSeparatorCommas(String.valueOf(promotionTotalPrice)) + "원";
        this.bookTotalPriceName = StringUtils.addThousandSeparatorCommas(String.valueOf(bookTotalPrice)) + "원";
        this.examTotalPriceName = StringUtils.addThousandSeparatorCommas(String.valueOf(examTotalPrice)) + "원";
        this.deliveryPriceName = StringUtils.addThousandSeparatorCommas(String.valueOf(deliveryPrice)) + "원";
    }

}
