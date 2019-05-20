package com.zianedu.api.dto;

import com.zianedu.api.utils.StringUtils;
import lombok.Data;

/**
 * 주문상품정보 상품 리스트 모델링
 */
@Data
public class OrderProductListDTO {

    private String productType;

    private String productName;

    private int count;

    private String sellPriceName;

    private int kind;

    public OrderProductListDTO(){}

    public OrderProductListDTO(String productType, String productName, int count, int sellPrice, int kind) {
        this.productType = productType;
        this.productName = productName;
        this.count = count;
        this.sellPriceName = StringUtils.addThousandSeparatorCommas(String.valueOf(sellPrice)) + "원";
        this.kind = kind;
    }
}
