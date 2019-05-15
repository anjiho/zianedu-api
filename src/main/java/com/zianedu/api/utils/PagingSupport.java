package com.zianedu.api.utils;

public class PagingSupport {

    public static int getPagingStartNumber(int sPage, int pageInList) {
        int page_cnt = pageInList;
        int srow = page_cnt * (sPage - 1) + 1;

        int start = srow - 1;
        return start;
    }
}
