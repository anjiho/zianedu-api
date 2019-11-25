package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.dto.LeftMenuSubDepthListDTO;
import com.zianedu.api.dto.ZianPassProductDTO;
import com.zianedu.api.mapper.CategoryMapper;
import com.zianedu.api.mapper.MenuMapper;
import com.zianedu.api.mapper.ProductMapper;
import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.vo.TCategoryVO;

import com.zianedu.api.vo.TeacherVO;
import com.zianedu.api.vo.ZianPassSubMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.OK;

@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

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
    public List<TCategoryVO> getLectureApplySubjectLeftMenuList(int ctgKey, String[] subjectMenuKeys) {
        int resultCode = OK.value();

        List<TCategoryVO> leftMenuList = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            String[] subjectMenuKeyStrs = new String[0];
            if (subjectMenuKeys.length > 0) {
                subjectMenuKeyStrs = subjectMenuKeys;
            }
            List<String> subjectMenuKeyList = new ArrayList<>();
            if (subjectMenuKeys.length > 0) {
                subjectMenuKeyList = Arrays.asList(subjectMenuKeyStrs);
            }
            leftMenuList = menuMapper.selectTCategoryByParentKeyAtLectureApply(ctgKey, subjectMenuKeyList);
        }
        return leftMenuList;
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
            teacherMenuList = menuMapper.selectTCategoryByCtgKey(ctgKey);
            if (teacherMenuList.size() > 0) {
                for (TCategoryVO vo : teacherMenuList) {
                    //전체일때
                    if (vo.getPos() == 0) {
                        List<TCategoryVO> leftMenuList = menuMapper.selectTCategoryByParentKey(vo.getParentKey());
                        List<TeacherVO> teacherInfoList = new ArrayList<>();
                        for (TCategoryVO tCategoryVO : leftMenuList) {
                            List<TeacherVO> teacherInfo = menuMapper.selectTeacherListFromTeacherIntroduce(tCategoryVO.getCtgKey());
                            for (TeacherVO teacherVO : teacherInfo) {
                                teacherVO.setTeacherImage(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), teacherVO.getTeacherImage()));
                            }
                            teacherInfoList.addAll(teacherInfo);
                        }
                        vo.setTeacherList(teacherInfoList);
                    } else {
                        List<TeacherVO> teacherInfo = menuMapper.selectTeacherListFromTeacherIntroduce(vo.getCtgKey());
                        if (teacherInfo.size() > 0) {
                            for (TeacherVO teacherVO : teacherInfo) {
                                teacherVO.setTeacherImage(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), teacherVO.getTeacherImage()));
                            }
                        }
                        vo.setTeacherList(teacherInfo);
                    }
                }
            }
        }
        return new ApiResultListDTO(teacherMenuList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getZianPassMenu(int ctgKey) {
        int resultCode = OK.value();

        List<LeftMenuSubDepthListDTO> leftMenuSubDepthList = new ArrayList<>();
        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            List<TCategoryVO> firstMenu = categoryMapper.selectTCategoryListByParentKey(ctgKey);
            if (firstMenu.size() > 0) {
                for (TCategoryVO categoryVO : firstMenu) {

                    LeftMenuSubDepthListDTO leftMenuSubDepthListDTO = new LeftMenuSubDepthListDTO();
                    leftMenuSubDepthListDTO.setCtgKey(categoryVO.getCtgKey());
                    leftMenuSubDepthListDTO.setMenuName(categoryVO.getName());
                    /**
                     * 지안패스일때 직렬별 그룹 서브메뉴 추가
                     */
                    if (categoryVO.getCtgKey() == 7026 || categoryVO.getCtgKey() == 7172 || categoryVO.getCtgKey() == 7176) {
                        leftMenuSubDepthListDTO.setIsSingle(0);
                        List<ZianPassSubMenuVO> subMenuList = productMapper.selectZianPassSubMenuList(categoryVO.getCtgKey());
                        leftMenuSubDepthListDTO.setZianPassSubMenuList(subMenuList);
                    }
                    leftMenuSubDepthList.add(leftMenuSubDepthListDTO);
                }
            }
        }
        return new ApiResultListDTO(leftMenuSubDepthList, resultCode);
    }
}
