package com.zianedu.api.service;

import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.mapper.MenuMapper;
import com.zianedu.api.vo.TCategoryVO;

import com.zianedu.api.vo.TeacherVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Transactional(readOnly = true)
    public ApiResultListDTO getTechVodZianPassLeftMenu(int ctgKey) {
        int resultCode = OK.value();


        List<TCategoryVO> firstLeftMenuList = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            firstLeftMenuList = menuMapper.selectTCategoryByParentKey(ctgKey);
            if (firstLeftMenuList.size() > 0) {
                for (TCategoryVO vo : firstLeftMenuList) {
                    List<TCategoryVO> secondLeftMenuList = menuMapper.selectTCategoryByParentKey(vo.getCtgKey());
                    vo.setSecondMenuList(secondLeftMenuList);
                }
            }
        }
        return new ApiResultListDTO(firstLeftMenuList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getTeacherIntroduceLeftMenu(int ctgKey) {
        int resultCode = OK.value();


        List<TCategoryVO> teacherMenuList = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            teacherMenuList = menuMapper.selectTCategoryByParentKey(ctgKey);
            if (teacherMenuList.size() > 0) {
                for (TCategoryVO vo : teacherMenuList) {
                    List<TeacherVO> teacherInfo = menuMapper.selectTeacherListFromTeacherIntroduce(vo.getCtgKey());
                    vo.setTeacherList(teacherInfo);
                }
            }
        }
        return new ApiResultListDTO(teacherMenuList, resultCode);
    }
}
