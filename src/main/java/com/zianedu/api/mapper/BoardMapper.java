package com.zianedu.api.mapper;

import com.zianedu.api.vo.GoodsReviewVO;
import com.zianedu.api.vo.TBbsDataVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardMapper {

    List<TBbsDataVO> selectTBbsDataList(@Param("bbsMasterKey") int bbsMasterKey, @Param("teacherKey") int teacherKey, @Param("listType") String listType,
                                         @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);

    List<GoodsReviewVO> selectGoodsReviewList(@Param("teacherKey") int teacherKey, @Param("listType") String listType,
                                           @Param("startNumber") int startNumber, @Param("listLimitNumber") int listLimitNumber);
}
