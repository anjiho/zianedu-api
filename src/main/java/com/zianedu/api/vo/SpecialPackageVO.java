package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class SpecialPackageVO {

    private int ctgGKey;

    private int gKey;

    private String goodsName;

    private int examYear;

    private int limitDay;

    private String discountPercent;

    private String pcSellPriceName;

    private String mobileSellPriceName;

    private String pcMobileSellPriceName;

    private List<TGoodsPriceOptionVO> videoLectureKindList;

    private List<SpecialPackageLectureVO> includeProductList;
}
