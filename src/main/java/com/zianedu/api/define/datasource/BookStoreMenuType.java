package com.zianedu.api.define.datasource;

public enum BookStoreMenuType {

    MD_RECOMMEND(841, "MD추천"),
    TOPICAL_BOOK(842, "화제의 도서"),
    PUBLIC_BEST_BOOK(843, "행정직 베스트 도서"),
    TECH_BEST_BOOK(844, "기술직 베스트 도서"),
    POST_BEST_BOOK(845, "계리직 베스트 도서")
    ;

    int bookStoreMenuCode;

    String bookStoreMenuStr;

    BookStoreMenuType(int bookStoreMenuCode, String bookStoreMenuStr) {
        this.bookStoreMenuCode = bookStoreMenuCode;
        this.bookStoreMenuStr = bookStoreMenuStr;
    }
}
