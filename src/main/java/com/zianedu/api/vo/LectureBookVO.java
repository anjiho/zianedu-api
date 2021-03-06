package com.zianedu.api.vo;

import lombok.Data;

@Data
public class LectureBookVO {

    private int gKey;

    private int bookKey;

    private int priceKey;

    private String writer;

    private String bookName;

    private String publishDate;

    private int sellPrice;

    private int price;

    private String sellPriceName;

    private String priceName;

    private int valueBit;

    private String isMain;

    private String imageView;

    private String imageList;

    private String imageViewUrl;

    private String imageListUrl;

    private String discountPercent;
}
