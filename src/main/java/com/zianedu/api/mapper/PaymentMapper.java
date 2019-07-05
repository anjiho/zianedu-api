package com.zianedu.api.mapper;

import com.zianedu.api.vo.BookBannerVO;
import com.zianedu.api.vo.BookListVO;
import com.zianedu.api.vo.TOrderGoodsVO;
import com.zianedu.api.vo.TOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaymentMapper {

    void insertTOrder(TOrderVO tOrderVO);

    void insertTOrderGoods(TOrderGoodsVO tOrderGoodsVO);

}
