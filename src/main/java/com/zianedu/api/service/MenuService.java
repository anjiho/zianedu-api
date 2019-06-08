package com.zianedu.api.service;

import com.zianedu.api.define.datasource.LeftMenuCtgKeyType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.mapper.MenuMapper;
import com.zianedu.api.vo.TCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Transactional(readOnly = true)
    public ApiResultListDTO getLeftMenuList(int ctgKey) {
        int resultCode = OK.value();

        List<TCategoryVO> leftMenuList = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            leftMenuList = menuMapper.selectTCategoryByParentKey(ctgKey);
        }
        return new ApiResultListDTO(leftMenuList, resultCode);
    }
}
