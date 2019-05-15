package com.zianedu.api.repository;

import com.zianedu.api.dto.LectureProgressRateContain;

import java.util.List;

public interface LectureProgressRateRepository {

    void injectLectureProgressRateAny(List<?> lectureProgressRateAny);

    void injectLectureProgressRateContain(List<LectureProgressRateContain> lectureProgressRateContains);
}
