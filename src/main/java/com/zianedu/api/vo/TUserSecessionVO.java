package com.zianedu.api.vo;

import com.zianedu.api.utils.Util;
import lombok.Data;

@Data
public class TUserSecessionVO {

    private int secessionKey;

    private int cKey;

    private int userKey;

    private String userId;

    private String name;

    private String indate;

    private String reason;

    private String memo;

    private String obtainDate;

    public TUserSecessionVO(){}

    public TUserSecessionVO(int userKey, String userId, String userName, String reason, String memo) {
        this.cKey = 100;
        this.userKey = userKey;
        this.userId = Util.isNullValue(userId, "");
        this.name = Util.isNullValue(userName, "");
        this.reason = Util.isNullValue(reason, "");
        this.memo = Util.isNullValue(memo, "");
    }

}
