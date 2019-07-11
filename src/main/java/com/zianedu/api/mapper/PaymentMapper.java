package com.zianedu.api.mapper;

import com.zianedu.api.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaymentMapper {

    void insertTOrder(TOrderVO tOrderVO);

    void insertTOrderGoods(TOrderGoodsVO tOrderGoodsVO);

    void insertTOrderLec(TOrderLecVO tOrderLecVO);

    void insertTPayInipay(TPayInipayVO tPayInipayVO);

    void insertTPoint(TPointVO tPointVO);

}
