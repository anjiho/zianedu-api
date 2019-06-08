package com.zianedu.api.service;

import com.zianedu.api.define.datasource.LeftMenuCtgKeyType;
import com.zianedu.api.dto.ApiResultListDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class CategoryService {

    public ApiResultListDTO getLeftMenuCtgKey() {
        int resultCode = OK.value();
        List<HashMap<String, String>> leftMenuCtgKeyList = LeftMenuCtgKeyType.getLeftMenuCtgKeyList();
        return new ApiResultListDTO(leftMenuCtgKeyList, resultCode);
    }
}
