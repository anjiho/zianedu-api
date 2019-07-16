package com.zianedu.api.vo;

import com.zianedu.api.utils.DateUtils;
import lombok.Data;

@Data
public class AchievementTopInfoVO {

    private String subjectName;

    private String serial;

    private String examDate = DateUtils.todayToStrKor();

    public AchievementTopInfoVO(){}

    public AchievementTopInfoVO(String subjectName, String serial) {
        this.subjectName = subjectName;
        this.serial = serial;
    }

}
