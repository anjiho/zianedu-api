package com.zianedu.api.mapper;

import com.zianedu.api.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerMapper {

    /** SELECT **/
    BannerVO selectBannerInfoBySingle(@Param("ctgKey") int ctgKey);

    List<BannerVO> selectBannerList(@Param("ctgKey") int ctgKey);

    List<PopupVO> selectPopupList(@Param("ctgKey") int ctgKey);

    /** INSERT **/

    /** UPDATE **/
}
