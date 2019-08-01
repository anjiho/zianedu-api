package com.zianedu.api.service;

import com.zianedu.api.define.datasource.LeftMenuCtgKeyType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.mapper.CategoryMapper;
import com.zianedu.api.vo.TCategoryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class CategoryService {

    protected static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryMapper categoryMapper;

    public ApiResultListDTO getLeftMenuCtgKey() {
        int resultCode = OK.value();
        List<HashMap<String, String>> leftMenuCtgKeyList = LeftMenuCtgKeyType.getLeftMenuCtgKeyList();
        return new ApiResultListDTO(leftMenuCtgKeyList, resultCode);
    }

    /**
     * 도서의 메뉴 링크 값 가져오기
     * @param gKey
     * @return
     */
    @Transactional(readOnly = true)
    public int getBookLinkCtgKey(int gKey) {
        List<Integer> ctgKeyList = categoryMapper.selectCtgKetListFromTCategoryGoods(gKey);

        List<List<TCategoryVO>> bookLinkCtgKeyList = new ArrayList<>();
        List<TCategoryVO> list = new ArrayList<>();
        for (Integer ctgKey : ctgKeyList) {
            list = this.getSequentialCategoryList(ctgKey);
            if (list.size() > 3) {
                for (TCategoryVO vo : list) {
                    if (vo.getCtgKey() == 688) {
                        bookLinkCtgKeyList.add(list);
                    }
                }
            }
        }
        return bookLinkCtgKeyList.get(0).get(0).getCtgKey();
    }

    public List<TCategoryVO> getSequentialCategoryList(int ctgKey) {
        if (ctgKey == 0) return null;
        List<TCategoryVO>list = new ArrayList<>();
        int j = 0;
        for (int i=0; i<4; i++) {
            TCategoryVO tCategoryVO = new TCategoryVO();

            if (i == 0) tCategoryVO = categoryMapper.selectTCategoryInfoByCtgKey(ctgKey);
            else tCategoryVO = categoryMapper.selectTCategoryInfoByCtgKey(j);

            j = tCategoryVO.getParentKey();
            if (tCategoryVO.getParentKey() != 214) {
                list.add(tCategoryVO);
            }
        }
        return list;
    }

    /**
     * 모의고사 문제은행 문제 목록에서 단원 필드명 만들기
     * @param ctgKey
     * @return
     */
    public String getMakeUnitName(int ctgKey) {
        if (ctgKey == 0) return null;
        List<TCategoryVO>list = new ArrayList<>();
        int j = 0;
        for (int i=0; i<3; i++) {
            TCategoryVO tCategoryVO = new TCategoryVO();

            if (i == 0) tCategoryVO = categoryMapper.selectTCategoryInfoByCtgKey(ctgKey);
            else tCategoryVO = categoryMapper.selectTCategoryInfoByCtgKey(j);

            j = tCategoryVO.getParentKey();

            list.add(tCategoryVO);
        }
        String unitName = "";
        unitName = list.get(2).getName() + " > " + list.get(1).getName() + " > " + list.get(0).getName();
        return unitName;
    }

    public ApiResultListDTO getTCategoryListByParentKey(int parentKey) {
        int resultCode = OK.value();

        List<TCategoryVO> categoryList = new ArrayList<>();

        if (parentKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            categoryList = categoryMapper.selectTCategoryListByParentKey(parentKey);
        }
        return new ApiResultListDTO(categoryList, resultCode);
    }

}
