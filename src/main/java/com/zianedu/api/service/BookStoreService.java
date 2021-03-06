package com.zianedu.api.service;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.define.datasource.BookStoreCommonType;
import com.zianedu.api.define.err.ZianErrCode;
import com.zianedu.api.dto.*;
import com.zianedu.api.mapper.BookStoreMapper;
import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.utils.PagingSupport;
import com.zianedu.api.utils.StringUtils;
import com.zianedu.api.utils.Util;
import com.zianedu.api.vo.BookBannerVO;
import com.zianedu.api.vo.BookListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class BookStoreService extends PagingSupport {

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

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getBookListFromLeftMenuCtgKey(int ctgKey, int sPage, int listLimit) {
        int resultCode = OK.value();

        int totalCount = 0;
        int startNumber = getPagingStartNumber(sPage, listLimit);

        List<BookListVO> bookList = new ArrayList<>();

        if (ctgKey == 0 && sPage == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            totalCount = bookStoreMapper.selectBookListFromLeftMenuCtgKeyCount(ctgKey);
            bookList = bookStoreMapper.selectBookListFromLeftMenuCtgKey(ctgKey, startNumber, listLimit);
            if (bookList.size() > 0) {
                for (BookListVO vo : bookList) {
                    String discountPercent = Util.getProductDiscountRate(Integer.parseInt(vo.getPrice()), Integer.parseInt(vo.getSellPrice()));
                    vo.setDiscountPercent(discountPercent);

                    String accrualRate = Util.getAccrualRatePoint(Integer.parseInt(vo.getSellPrice()), Integer.parseInt(vo.getPoint()));
                    vo.setAccrualRate(accrualRate);

                    vo.setPrice(StringUtils.addThousandSeparatorCommas(vo.getPrice()));
                    vo.setSellPrice(StringUtils.addThousandSeparatorCommas(vo.getSellPrice()));
                    vo.setPoint(StringUtils.addThousandSeparatorCommas(vo.getPoint()));
                    vo.setBookImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getImageList()));
                }
            }
        }
        return new ApiPagingResultDTO(totalCount, bookList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getBestBookList() {
        int resultCode = OK.value();

        List<BookListVO> bestBookList = bookStoreMapper.selectBookListFromLeftMenuCtgKeyAtBest();
        if (bestBookList.size() > 0) {
            for (BookListVO vo : bestBookList) {
                String discountPercent = Util.getProductDiscountRate(Integer.parseInt(vo.getPrice()), Integer.parseInt(vo.getSellPrice()));
                vo.setDiscountPercent(discountPercent);

                String accrualRate = Util.getAccrualRatePoint(Integer.parseInt(vo.getSellPrice()), Integer.parseInt(vo.getPoint()));
                vo.setAccrualRate(accrualRate);

                vo.setPrice(StringUtils.addThousandSeparatorCommas(vo.getPrice()));
                vo.setSellPrice(StringUtils.addThousandSeparatorCommas(vo.getSellPrice()));
                vo.setPoint(StringUtils.addThousandSeparatorCommas(vo.getPoint()));
                vo.setBookImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getImageList()));
            }
        }
        return new ApiResultListDTO(bestBookList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultListDTO getNewBookList() {
        int resultCode = OK.value();

        List<BookListVO> bestBookList = bookStoreMapper.selectBookListFromLeftMenuCtgKeyAtNew();
        if (bestBookList.size() > 0) {
            for (BookListVO vo : bestBookList) {
                String discountPercent = Util.getProductDiscountRate(Integer.parseInt(vo.getPrice()), Integer.parseInt(vo.getSellPrice()));
                vo.setDiscountPercent(discountPercent);

                String accrualRate = Util.getAccrualRatePoint(Integer.parseInt(vo.getSellPrice()), Integer.parseInt(vo.getPoint()));
                vo.setAccrualRate(accrualRate);

                vo.setPrice(StringUtils.addThousandSeparatorCommas(vo.getPrice()));
                vo.setSellPrice(StringUtils.addThousandSeparatorCommas(vo.getSellPrice()));
                vo.setPoint(StringUtils.addThousandSeparatorCommas(vo.getPoint()));
                vo.setBookImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getImageList()));
            }
        }
        return new ApiResultListDTO(bestBookList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiPagingResultDTO getSalesBookList(String bookMenuType, String searchText, String orderType, int sPage, int listLimit, int subjectKey) {
        int resultCode = OK.value();

        int totalCount = 0;
        int startNumber = getPagingStartNumber(sPage, listLimit);

        List<BookListVO> bookList = new ArrayList<>();

        if ("".equals(bookMenuType) && sPage == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            totalCount = bookStoreMapper.selectSalesBookListCount(bookMenuType, searchText, subjectKey);
            bookList = bookStoreMapper.selectSalesBookList(bookMenuType, searchText, orderType.toUpperCase(), startNumber, listLimit, subjectKey);
            if (bookList.size() > 0) {
                for (BookListVO vo : bookList) {
                    String discountPercent = Util.getProductDiscountRate(Integer.parseInt(vo.getPrice()), Integer.parseInt(vo.getSellPrice()));
                    vo.setDiscountPercent(discountPercent);

                    String accrualRate = Util.getAccrualRatePoint(Integer.parseInt(vo.getSellPrice()), Integer.parseInt(vo.getPoint()));
                    vo.setAccrualRate(accrualRate);

                    vo.setPrice(StringUtils.addThousandSeparatorCommas(vo.getPrice()));
                    vo.setSellPrice(StringUtils.addThousandSeparatorCommas(vo.getSellPrice()));
                    vo.setPoint(StringUtils.addThousandSeparatorCommas(vo.getPoint()));
                    vo.setBookImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), vo.getImageList()));
                }
            }
        }
        return new ApiPagingResultDTO(totalCount, bookList, resultCode);
    }

    @Transactional(readOnly = true)
    public ApiResultObjectDTO getBookDetailInfo(int gKey) {
        int resultCode = OK.value();

        BookDetailInfoDTO detailInfoDTO = null;
        BookListVO otherBookInfo = new BookListVO();

        if (gKey == 0) {
            resultCode = ZianErrCode.BAD_REQUEST.code();
        } else {
            BookListVO bookDetailInfo = bookStoreMapper.selectBookDetailInfo(gKey);
            List<BookListVO> writerOtherBookInfoList = new ArrayList<>();
            if (bookDetailInfo.getGKey() > 0) {
                bookDetailInfo.setDiscountPercent(Util.getProductDiscountRate(Integer.parseInt(bookDetailInfo.getPrice()), Integer.parseInt(bookDetailInfo.getSellPrice())));

                String accrualRate = Util.getAccrualRatePoint(Integer.parseInt(bookDetailInfo.getSellPrice()), Integer.parseInt(bookDetailInfo.getPoint()));
                bookDetailInfo.setAccrualRate(accrualRate);

                bookDetailInfo.setPrice(StringUtils.addThousandSeparatorCommas(bookDetailInfo.getPrice()));
                bookDetailInfo.setSellPrice(StringUtils.addThousandSeparatorCommas(bookDetailInfo.getSellPrice()));
                bookDetailInfo.setPoint(StringUtils.addThousandSeparatorCommas(bookDetailInfo.getPoint()));
                bookDetailInfo.setBookImageUrl(FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), bookDetailInfo.getImageList()));

                writerOtherBookInfoList = bookStoreMapper.selectWriterOtherBookInfo(bookDetailInfo.getGKey(), bookDetailInfo.getWriter());

                if (writerOtherBookInfoList.size() > 0) {
                    Collections.shuffle(writerOtherBookInfoList);   //랜덤 정렬
                    otherBookInfo = new BookListVO(writerOtherBookInfoList);
                }
            }
            detailInfoDTO = new BookDetailInfoDTO(bookDetailInfo, otherBookInfo);
        }
        return new ApiResultObjectDTO(detailInfoDTO, resultCode);
    }

    public ApiResultListDTO getBookStoreSelectBoxList(String bookStoreType) {
        int resultCode = OK.value();

        List<SelectboxDTO> selectBoxList = new ArrayList<>();
        if ("COMMON".equals(bookStoreType)) {
            selectBoxList = BookStoreCommonType.getBookStoreCommonTypeList();
        }
        return new ApiResultListDTO(selectBoxList, resultCode);
    }
}
