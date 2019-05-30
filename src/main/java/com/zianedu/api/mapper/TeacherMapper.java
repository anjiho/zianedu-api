package com.zianedu.api.mapper;

import com.zianedu.api.vo.BannerVO;
import com.zianedu.api.vo.GoodsListVO;
import com.zianedu.api.vo.PopupVO;
import com.zianedu.api.vo.TTeacherVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {

    /** SELECT **/
    TTeacherVO selectTeacherInfo(@Param("teacherKey") int teacherKey);

    String selectTeacherIntroduceInfo(@Param("teacherKey") int teacherKey, @Param("device") int device, @Param("menuCtgKey") int menuCtgKey);

    List<GoodsListVO> selectGoodsListAtTeacherHome(@Param("teacherKey") int teacherKey, @Param("type") int type);

    /** INSERT **/

    /** UPDATE **/
}
