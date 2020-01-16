package com.zianedu.api.mapper;

import com.zianedu.api.vo.BookBannerVO;
import com.zianedu.api.vo.BookListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookStoreMapper<selectSalesBookListCount> {

    List<BookBannerVO> selectBookListFromMenuCtgKey(@Param("ctgKey") int ctgKey, @Param("listLimit") int listLimit);

    List<BookListVO> selectBookListFromLeftMenuCtgKey(@Param("ctgKey") int ctgKey, @Param("startNumber") int startNumber, @Param("listLimit") int listLimit);

    Integer selectBookListFromLeftMenuCtgKeyCount(@Param("ctgKey") int ctgKey);

    List<BookListVO> selectBookListFromLeftMenuCtgKeyAtBest();

    List<BookListVO> selectBookListFromLeftMenuCtgKeyAtNew();

    List<BookListVO> selectSalesBookList(@Param("bookMenuType") String bookMenuType, @Param("searchText") String searchText,
                                         @Param("orderType") String orderType, @Param("startNumber") int startNumber,
                                         @Param("listLimit") int listLimit, @Param("subjectKey") int subjectKey);

    int selectSalesBookListCount(@Param("bookMenuType") String bookMenuType, @Param("searchText") String searchText, @Param("subjectKey") int subjectKey);

    BookListVO selectBookDetailInfo(@Param("gKey") int gKey);

    List<BookListVO> selectWriterOtherBookInfo(@Param("gKey") int gKey, @Param("writer") String writer);

}
