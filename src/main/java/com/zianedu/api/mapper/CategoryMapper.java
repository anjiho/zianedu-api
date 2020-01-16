package com.zianedu.api.mapper;

import com.zianedu.api.dto.SelectboxDTO;
import com.zianedu.api.vo.TCategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {

    List<Integer> selectCtgKetListFromTCategoryGoods(@Param("gKey") int gKey);

    Integer selectParentKeyFromCategory(@Param("ctgKey") int ctgKey);

    TCategoryVO selectTCategoryInfoByCtgKey(@Param("ctgKey") int ctgKey);

    List<TCategoryVO> selectTCategoryListByParentKey(@Param("parentKey") int parentKey);

    List<SelectboxDTO> selectBookStoreSubjectSelectBoxList(@Param("bookStoreType") String bookStoreType);
}
