package com.zianedu.api.dto;

import lombok.Data;

@Data
public class SelectboxDTO {

    private Object key;

    private Object value;

    public SelectboxDTO() {}

    public SelectboxDTO(Object key, Object value) {
        this.key = key;
        this.value = value;
    }
}
