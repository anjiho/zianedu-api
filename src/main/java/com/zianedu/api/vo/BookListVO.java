package com.zianedu.api.vo;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.utils.StringUtils;
import com.zianedu.api.utils.Util;
import lombok.Data;

import java.util.List;

@Data
public class BookListVO {

    private int gKey;

    private String goodsName;

    private String imageList;

    private String bookImageUrl;

    private String writer;

    private String publishDate;

    private String name;

    private String price;

    private String sellPrice;

    private String point;

    private String discountPercent;

    private String accrualRate;

    private int priceKey;

    private int subjectKey;

    private String subjectName;

    private int ctgKey;

    private int pageCnt;

    private String description;

    private String contentList;

    public BookListVO(){}

    public BookListVO(List<BookListVO> randomBookInfoList) {
        this.gKey = randomBookInfoList.get(0).getGKey();
        this.goodsName = randomBookInfoList.get(0).getGoodsName();
        this.imageList = randomBookInfoList.get(0).getImageList();
        this.writer = randomBookInfoList.get(0).getWriter();
        this.name = randomBookInfoList.get(0).getName();
        this.price = StringUtils.addThousandSeparatorCommas(randomBookInfoList.get(0).getPrice());
        this.sellPrice = StringUtils.addThousandSeparatorCommas(randomBookInfoList.get(0).getSellPrice());
        this.point = StringUtils.addThousandSeparatorCommas(randomBookInfoList.get(0).getPoint());
        this.priceKey = randomBookInfoList.get(0).getPriceKey();
        this.subjectKey = randomBookInfoList.get(0).getSubjectKey();
        this.publishDate = randomBookInfoList.get(0).getPublishDate();
        this.subjectName = randomBookInfoList.get(0).getSubjectName();
        this.pageCnt = randomBookInfoList.get(0).getPageCnt();
        this.discountPercent = Util.getProductDiscountRate(Integer.parseInt(randomBookInfoList.get(0).getPrice()), Integer.parseInt(randomBookInfoList.get(0).getSellPrice()));
        this.accrualRate = Util.getAccrualRatePoint(Integer.parseInt(randomBookInfoList.get(0).getSellPrice()), Integer.parseInt(randomBookInfoList.get(0).getPoint()));
        this.bookImageUrl = FileUtil.concatPath(ConfigHolder.getFileDomainUrl(), randomBookInfoList.get(0).getImageList());


    }
}
