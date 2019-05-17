package com.zianedu.api.vo;

import com.zianedu.api.dto.LectureProgressRateContain;
import com.zianedu.api.dto.LectureProgressRateDTO;
import lombok.Data;

@Data
public class OnlineVideoPauseVO implements LectureProgressRateContain {

    private int jKey;

    private int jGKey;

    private int GKey;

    private String startDate;

    private String endDate;

    private String stopStartDate;

    private String stopEndDate;

    private String ctgName;

    private String name;

    private int kind;

    private int jLecKey;

    private int pauseCnt;

    private int pauseDay;

    private int progressRate;

    private String progressRateName;

    @Override
    public Integer jLecKey() {
        return jLecKey;
    }

    @Override
    public void addLectureProgressRate(LectureProgressRateDTO lectureProgressRateDTO) {
        progressRate = lectureProgressRateDTO.getProgressRate();
        progressRateName = lectureProgressRateDTO.getProgressRate() + "%";
    }
}
