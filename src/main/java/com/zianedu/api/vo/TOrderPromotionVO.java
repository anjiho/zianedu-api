package com.zianedu.api.vo;

import lombok.Data;

@Data
public class TOrderPromotionVO {

    private int jPmKey;

    private int jGKey;

    private int pmType;

    public TOrderPromotionVO(){}

    public TOrderPromotionVO(int jGKey, int pmType) {
        this.jGKey = jGKey;
        this.pmType = pmType;
    }

}
