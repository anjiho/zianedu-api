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

    private String sellPrice;

    private int valueBit;

    private String isMain;
}
