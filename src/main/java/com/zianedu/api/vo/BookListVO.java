package com.zianedu.api.vo;

import lombok.Data;

@Data
public class BookListVO {

    private int gKey;

    private String goodsName;

    private String imageList;

    private String bookImageUrl;

    private String writer;

    private String publishDate;

    private String name;

    private String price;

    private String sellPrice;

    private String point;

    private String discountPercent;

    private String accrualRate;

    private int priceKey;
}
