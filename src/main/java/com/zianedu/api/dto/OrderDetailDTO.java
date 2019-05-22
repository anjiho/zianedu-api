package com.zianedu.api.dto;

import com.zianedu.api.vo.DeliveryAddressVO;
import com.zianedu.api.vo.OrderGoodsDetailVO;
import com.zianedu.api.vo.PaymentVO;
import com.zianedu.api.vo.TUserVO;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetailDTO {

    List<OrderGoodsDetailVO> orderProductInfo;

    TUserVO orderUserInfo;

    DeliveryAddressVO deliveryAddressInfo;

    PaymentVO paymentInfo;

    public OrderDetailDTO(){}

    public OrderDetailDTO(List<OrderGoodsDetailVO> orderProductInfo, TUserVO orderUserInfo,
                          DeliveryAddressVO deliveryAddressInfo, PaymentVO paymentInfo) {
        this.orderProductInfo = orderProductInfo;
        this.orderUserInfo = orderUserInfo;
        this.deliveryAddressInfo = deliveryAddressInfo;
        this.paymentInfo = paymentInfo;
    }
}
