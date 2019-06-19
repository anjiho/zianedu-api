package com.zianedu.api.mapper;

import com.zianedu.api.vo.BookBannerVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookStoreMapper {

    List<BookBannerVO> selectBookListFromMenuCtgKey(@Param("ctgKey") int ctgKey, @Param("listLimit") int listLimit);
}
