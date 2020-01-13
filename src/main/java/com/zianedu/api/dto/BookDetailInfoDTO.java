package com.zianedu.api.dto;

import com.zianedu.api.vo.BookListVO;
import lombok.Data;

@Data
public class BookDetailInfoDTO {

    private BookListVO bookDetailInfo;

    private BookListVO otherBookInfo;

    public BookDetailInfoDTO(){}

    public BookDetailInfoDTO(BookListVO bookDetailInfo, BookListVO otherBookInfo) {
        this.bookDetailInfo = bookDetailInfo;
        this.otherBookInfo = otherBookInfo;
    }
}
