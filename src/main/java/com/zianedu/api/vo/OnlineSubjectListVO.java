package com.zianedu.api.vo;

import com.zianedu.api.dto.LectureProgressRateContain;
import com.zianedu.api.dto.LectureProgressRateDTO;
import lombok.Data;

@Data
public class OnlineSubjectListVO implements LectureProgressRateContain {

    private int jGKey;

    private int gKey;

    private String ctgName;

    private String startDate;

    private String endDate;

    private String name;

    private int pauseCnt;

    private int jLecKey;

    private int progressRate;

    @Override
    public Integer jLecKey() {
        return jLecKey;
    }

    @Override
    public void addLectureProgressRate(LectureProgressRateDTO lectureProgressRateDTO) {
        progressRate = lectureProgressRateDTO.getProgressRate();
    }
}
