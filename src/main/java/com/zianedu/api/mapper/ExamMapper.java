package com.zianedu.api.mapper;

import com.zianedu.api.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamMapper {

    /** SELECT **/
    List<TExamUserVO> selectGiChulProblemList(@Param("userKey") int userKey, @Param("groupCtgKey") int groupCtgKey,
                                              @Param("classCtgKey") int classCtgKey, @Param("subjectCtgKey") int subjectCtgKey);

    List<TExamUserVO> selectGiChulProblemListByComplete(@Param("userKey") int userKey, @Param("groupCtgKey") int groupCtgKey,
                                              @Param("classCtgKey") int classCtgKey, @Param("subjectCtgKey") int subjectCtgKey);

    /** INSERT **/

    /** UPDATE **/
}
