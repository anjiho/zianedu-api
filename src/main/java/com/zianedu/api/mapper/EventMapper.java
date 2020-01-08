package com.zianedu.api.mapper;

import com.zianedu.api.vo.TEventVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EventMapper {

    /** SELECT **/
    List<TEventVO> selectEventList(@Param("eventType") String eventType, @Param("startNumber") int startNumber,
                                   @Param("listLimitNumber") int listLimitNumber, @Param("searchType") String searchType, @Param("searchText") String searchText);

    int selectEventListCount(@Param("eventType") String eventType, @Param("searchType") String searchType, @Param("searchText") String searchText);


    /** INSERT **/
    void insertTEvent(TEventVO tEventVO);
}
