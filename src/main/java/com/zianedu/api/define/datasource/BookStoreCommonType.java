package com.zianedu.api.define.datasource;

import com.zianedu.api.dto.SelectboxDTO;

import java.util.ArrayList;
import java.util.List;

public enum BookStoreCommonType {
    KOREAN("국어", 72),
    ENGLISH("영어", 73),
    KOREA_HISTORY("한국사", 146),

    ;

    String bookStoreCommonTypeName;

    int bookStoreCommonTypeKey;

    BookStoreCommonType(String bookStoreCommonTypeName, int bookStoreCommonTypeKey) {
        this.bookStoreCommonTypeName = bookStoreCommonTypeName;
        this.bookStoreCommonTypeKey = bookStoreCommonTypeKey;
    }

    public static List<SelectboxDTO> getBookStoreCommonTypeList() {
        List<SelectboxDTO>list = new ArrayList<>();
        for (BookStoreCommonType commonType : BookStoreCommonType.values()) {
            SelectboxDTO selectboxDTO = new SelectboxDTO(
                    commonType.bookStoreCommonTypeKey,
                    commonType.bookStoreCommonTypeName
            );
            list.add(selectboxDTO);
        }
        return list;
    }
}
