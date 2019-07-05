package com.zianedu.api.dto;

import com.zianedu.api.utils.StringUtils;
import lombok.Data;

/**
 * 주문상품정보 상품 리스트 모델링
 */
@Data
public class OrderProductListDTO {

    private int gKey;

    private int priceKey;

    private Long cartKey;

    private String productType;

    private String productName;

    private int count;

    private String sellPriceName;

    private int kind;

    private int type;

    private int extendDay;

    public OrderProductListDTO(){}

    public OrderProductListDTO(int gKey, int priceKey, Long cartKey, int type, String productType, String productName,
                               int count, int sellPrice, int kind, int extendDay) {
        this.gKey = gKey;
        this.priceKey = priceKey;
        this.cartKey = cartKey;
        this.type = type;
        this.productType = productType;
        this.productName = productName;
        this.count = count;
        this.sellPriceName = StringUtils.addThousandSeparatorCommas(String.valueOf(sellPrice)) + "원";
        this.kind = kind;
        this.extendDay = extendDay;
    }
}
