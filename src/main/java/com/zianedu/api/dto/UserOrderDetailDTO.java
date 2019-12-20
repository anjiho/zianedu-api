package com.zianedu.api.dto;

import com.zianedu.api.vo.DeliveryAddressVO;
import com.zianedu.api.vo.OrderDetailInfoVO;
import com.zianedu.api.vo.PaymentVO;
import com.zianedu.api.vo.TUserVO;
import lombok.Data;

import java.util.List;

@Data
public class UserOrderDetailDTO {

    private List<OrderDetailInfoVO> orderList;

    private TUserVO orderUserInfo;

    private DeliveryAddressVO deliveryAddressInfo;

    private PaymentVO paymentInfo;

    public UserOrderDetailDTO() {}

    public UserOrderDetailDTO(List<OrderDetailInfoVO> orderList, TUserVO orderUserInfo, DeliveryAddressVO deliveryAddressInfo, PaymentVO paymentInfo) {
        this.orderList = orderList;
        this.orderUserInfo = orderUserInfo;
        this.deliveryAddressInfo = deliveryAddressInfo;
        this.paymentInfo = paymentInfo;
    }
}
