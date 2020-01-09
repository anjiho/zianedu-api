package com.zianedu.api.mapper;

import com.zianedu.api.vo.BookBannerVO;
import com.zianedu.api.vo.BookListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookStoreMapper {

    List<BookBannerVO> selectBookListFromMenuCtgKey(@Param("ctgKey") int ctgKey, @Param("listLimit") int listLimit);

    List<BookListVO> selectBookListFromLeftMenuCtgKey(@Param("ctgKey") int ctgKey, @Param("startNumber") int startNumber, @Param("listLimit") int listLimit);

    Integer selectBookListFromLeftMenuCtgKeyCount(@Param("ctgKey") int ctgKey);

    List<BookListVO> selectBookListFromLeftMenuCtgKeyAtBest();

}
