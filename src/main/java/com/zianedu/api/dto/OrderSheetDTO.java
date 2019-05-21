package com.zianedu.api.dto;

import com.zianedu.api.vo.CartListVO;
import com.zianedu.api.vo.TUserVO;
import lombok.Data;

import java.util.List;

@Data
public class OrderSheetDTO {

    private List<OrderProductListDTO> orderProductList;

    private OrderProductListDTO orderProduct;

    private ProductTotalPriceDTO productTotalPrice;

    private GroupTotalPriceDTO productGroupPrice;

    private int userPoint;

    private TUserVO orderUserInfo;

    public OrderSheetDTO(){}

    public OrderSheetDTO(List<OrderProductListDTO> orderProductList, ProductTotalPriceDTO productTotalPrice,
                         GroupTotalPriceDTO productGroupPrice, int userPoint, TUserVO orderUserInfo) {
        this.orderProductList = orderProductList;
        this.productTotalPrice = productTotalPrice;
        this.productGroupPrice = productGroupPrice;
        this.userPoint = userPoint;
        this.orderUserInfo = orderUserInfo;
    }

    public OrderSheetDTO(OrderProductListDTO orderProduct, ProductTotalPriceDTO productTotalPrice,
                         GroupTotalPriceDTO productGroupPrice, int userPoint, TUserVO orderUserInfo) {
        this.orderProduct = orderProduct;
        this.productTotalPrice = productTotalPrice;
        this.productGroupPrice = productGroupPrice;
        this.userPoint = userPoint;
        this.orderUserInfo = orderUserInfo;
    }


}
