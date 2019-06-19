package com.zianedu.api.mapper;

import com.zianedu.api.vo.TCategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {

    List<Integer> selectCtgKetListFromTCategoryGoods(@Param("gKey") int gKey);

    Integer selectParentKeyFromCategory(@Param("ctgKey") int ctgKey);

    TCategoryVO selectTCategoryInfoByCtgKey(@Param("ctgKey") int ctgKey);
}
