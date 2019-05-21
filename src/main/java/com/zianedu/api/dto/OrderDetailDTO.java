package com.zianedu.api.dto;

import com.zianedu.api.vo.DeliveryAddressVO;
import com.zianedu.api.vo.OrderGoodsDetailVO;
import com.zianedu.api.vo.TUserVO;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetailDTO {

    List<OrderGoodsDetailVO> orderProductInfo;

    TUserVO orderUserInfo;

    DeliveryAddressVO deliveryAddressInfo;

    public OrderDetailDTO(){}

    public OrderDetailDTO(List<OrderGoodsDetailVO> orderProductInfo, TUserVO orderUserInfo, DeliveryAddressVO deliveryAddressInfo) {
        this.orderProductInfo = orderProductInfo;
        this.orderUserInfo = orderUserInfo;
        this.deliveryAddressInfo = deliveryAddressInfo;
    }
}
