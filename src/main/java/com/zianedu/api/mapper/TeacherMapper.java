package com.zianedu.api.mapper;

import com.zianedu.api.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {

    /** SELECT **/
    TTeacherVO selectTeacherInfo(@Param("teacherKey") int teacherKey);

    String selectTeacherIntroduceInfo(@Param("teacherKey") int teacherKey, @Param("device") int device, @Param("menuCtgKey") int menuCtgKey);

    List<GoodsListVO> selectGoodsListAtTeacherHome(@Param("teacherKey") int teacherKey, @Param("type") int type);

    List<BannerBookVO> selectTeacherBookList(@Param("teacherKey") int teacherKey);

    List<TeacherHomeLectureVO> selectTeacherVideoLectureList(@Param("teacherKey") int teacherKey, @Param("stepCtgKey") int stepCtgKey);

    List<TeacherHomeAcademyVO> selectTeacherAcademyLectureList(@Param("teacherKey") int teacherKey, @Param("stepCtgKey") int stepCtgKey);

    List<TeacherVideoAcademyProductVO> selectTeacherVideoAcademyProductList(@Param("teacherKey") int teacherKey);

    List<TeacherIntroduceVO> selectTeacherIntroduce(@Param("ctgKey") int ctgKey, @Param("pos") int pos);

    TeacherNameSubjectVO selectTeacherNameSubjectName(@Param("teacherKey") int teacherKey, @Param("reqKey") int reqKey);


    /** INSERT **/

    /** UPDATE **/
}
