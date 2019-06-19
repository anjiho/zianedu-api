package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.ApiResultListDTO;
import com.zianedu.api.mapper.BookStoreMapper;
import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.vo.BookBannerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class BookStoreService {

    @Autowired
    private BookStoreMapper bookStoreMapper;

    @Autowired
    private CategoryService categoryService;

    @Transactional(readOnly = true)
    public ApiResultListDTO getBookListFromOnlineStoreTopBanner(int ctgKey, int listLimit) {
        int resultCode = OK.value();

        List<BookBannerVO> bookBannerList = new ArrayList<>();

        if (ctgKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            bookBannerList = bookStoreMapper.selectBookListFromMenuCtgKey(ctgKey, listLimit);
            if (bookBannerList.size() > 0) {
                for (BookBannerVO vo : bookBannerList) {
                    vo.setBookImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getImageList()));
                    vo.setMenuLinkCtgKey(categoryService.getBookLinkCtgKey(vo.getGKey()));
                }
            }
        }
        return new ApiResultListDTO(bookBannerList, resultCode);
    }
}
