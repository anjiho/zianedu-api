package com.zianedu.api.define.datasource;

import com.zianedu.api.dto.SelectboxDTO;

import java.util.ArrayList;
import java.util.List;

public enum BookStoreTechType {
//    TechType1("정보보호론", 72),
//    TechType2("임업경영학", 73),
//    TechType3("조림학", 146),
//    TechType4("재배학", 146),
//    TechType5("식용작물학", 146),
//    TechType6("소프트웨어공학", 146),
//    TechType7("자료구조론", 146),
//    TechType8("토목설계", 146),
//    TechType9("건축구조", 146),
//    TechType10("건축계획", 146),
//    TechType11("건축시공", 146),
//    TechType12("건축역학", 146),
//    TechType13("환경공학", 146),
//    TechType14("전기이론", 146),
//    TechType15("전기기기", 146),
//    TechType16("전기자기학", 146),
//    TechType17("회로이론", 146),
//    TechType18("식품화학", 146),
//    TechType19("식품위생", 146),
//    TechType20("재난관리론", 146),
//    KOREA_HISTORY("안전관리론", 146),
//    KOREA_HISTORY("자동차구조원리 및 도로교통법규", 146),
//    KOREA_HISTORY("물리학개론", 146),
//    KOREA_HISTORY("작물생리학", 146),
//    KOREA_HISTORY("기계일반", 146),
//    KOREA_HISTORY("조림학", 146),
//    KOREA_HISTORY("조림학", 146),
//    KOREA_HISTORY("조림학", 146),
//    KOREA_HISTORY("조림학", 146),
//    KOREA_HISTORY("조림학", 146),
//
//    KOREA_HISTORY("컴퓨터일반", 146),
//    KOREA_HISTORY("면접", 146),
//    KOREA_HISTORY("기계설계", 146),
//    KOREA_HISTORY("측량학", 146),
//    KOREA_HISTORY("생물학개론", 146),
//    KOREA_HISTORY("통신이론", 146),
//    KOREA_HISTORY("전자공학개론", 146),
//    KOREA_HISTORY("무선공학개론", 146),
//    KOREA_HISTORY("화학공학일반", 146),
//    KOREA_HISTORY("공업화학", 146),
//    KOREA_HISTORY("조경학", 146),
//    KOREA_HISTORY("조경계획 및 생태계관리", 146),
//    KOREA_HISTORY("자동제어", 146),
//    KOREA_HISTORY("토질역학", 146),
//    KOREA_HISTORY("화학", 146),
//    KOREA_HISTORY("물리", 146),
//    KOREA_HISTORY("네트워크보안", 146),
//    KOREA_HISTORY("정보시스템보안", 146),
//    KOREA_HISTORY("프로그래밍언어론", 146),
//    KOREA_HISTORY("사회복지학", 146),
//    KOREA_HISTORY("공중보건", 146),
//    KOREA_HISTORY("보건행정", 146),
//    KOREA_HISTORY("가축사양학", 146),
//    KOREA_HISTORY("가축육종학", 146),
//    KOREA_HISTORY("환경보건", 146),
//    KOREA_HISTORY("토목직", 146),




    ;

    String bookStoreCommonTypeName;

    int bookStoreCommonTypeKey;

    BookStoreTechType(String bookStoreCommonTypeName, int bookStoreCommonTypeKey) {
        this.bookStoreCommonTypeName = bookStoreCommonTypeName;
        this.bookStoreCommonTypeKey = bookStoreCommonTypeKey;
    }

    public static List<SelectboxDTO> getBookStoreCommonTypeList() {
        List<SelectboxDTO>list = new ArrayList<>();
        for (BookStoreTechType commonType : BookStoreTechType.values()) {
            SelectboxDTO selectboxDTO = new SelectboxDTO(
                    commonType.bookStoreCommonTypeKey,
                    commonType.bookStoreCommonTypeName
            );
            list.add(selectboxDTO);
        }
        return list;
    }
}
