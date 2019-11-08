package com.zianedu.api.mapper;

import com.zianedu.api.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {

    /** SELECT **/
    Integer selectSubjectCtgKeyByMenuCtgKey(@Param("menuCtgKey") int menuCtgKey);

    List<TCategoryVO> selectTCategoryByParentKey(@Param("ctgKey") int ctgKey);

    List<TCategoryVO> selectTCategoryByCtgKey(@Param("ctgKey") int ctgKey);

    List<TeacherVO> selectTeacherListFromTeacherIntroduce(@Param("ctgKey") int ctgKey);

    /** INSERT **/

    /** UPDATE **/
}
