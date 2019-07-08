package com.zianedu.api.dto;

import com.zianedu.api.utils.StringUtils;
import lombok.Data;

/**
 * 주문상품정보 금액 합계 모델링
 */
@Data
public class ProductTotalPriceDTO {
    //전체 상품별 금액 합계
    private int productTotalPrice;
    //결제시 지급 마일리지
    private int totalPoint;
    //배송비
    private int deliveryPrice;

    private String productTotalPriceName;

    private String totalPointName;

    private String deliveryPriceName;

    public ProductTotalPriceDTO(){}

    public ProductTotalPriceDTO(int productTotalPrice, int totalPoint, int deliveryPrice) {
        this.productTotalPrice = productTotalPrice;
        this.totalPoint = totalPoint;
        this.deliveryPrice = deliveryPrice;
        this.productTotalPriceName = StringUtils.addThousandSeparatorCommas(String.valueOf(productTotalPrice)) + "원";
        this.totalPointName = StringUtils.addThousandSeparatorCommas(String.valueOf(totalPoint)) + "점";
        this.deliveryPriceName = StringUtils.addThousandSeparatorCommas(String.valueOf(deliveryPrice)) + "원";
    }
}
