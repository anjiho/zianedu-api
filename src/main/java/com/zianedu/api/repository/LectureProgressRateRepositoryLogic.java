package com.zianedu.api.repository;

import com.zianedu.api.dto.LectureProgressRateContain;
import com.zianedu.api.dto.LectureProgressRateDTO;
import com.zianedu.api.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 강의진도률 주입하는 클래스
 */
@Repository
public class LectureProgressRateRepositoryLogic implements LectureProgressRateRepository {

    @Autowired
    private ProductMapper productMapper;


    @Override
    public void injectLectureProgressRateAny(List<?> lectureProgressRateAny) {
        if (lectureProgressRateAny == null || lectureProgressRateAny.size() == 0) return;

        List<LectureProgressRateContain> contains = lectureProgressRateAny
                .stream()
                .filter(Objects::nonNull)
                .filter(any->any instanceof LectureProgressRateContain)
                .map(any->(LectureProgressRateContain)any)
                .collect(Collectors.toList());

        this.injectLectureProgressRateContain(contains);
    }

    @Override
    public void injectLectureProgressRateContain(List<LectureProgressRateContain> lectureProgressRateContains) {
        if (lectureProgressRateContains == null || lectureProgressRateContains.size() == 0) return;

        List<LectureProgressRateContain> injectable = lectureProgressRateContains
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Integer> jLecKeys = injectable
                .stream()
                .map(LectureProgressRateContain::jLecKey)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (jLecKeys == null || jLecKeys.size() == 0) return;

        for (LectureProgressRateContain contain : injectable) {
            LectureProgressRateDTO lectureProgressRateDTO = new LectureProgressRateDTO();
            Integer progressRate = productMapper.selectOnlineLectureProgressRate(contain.jLecKey());
            lectureProgressRateDTO.setJLecKey(contain.jLecKey());
            lectureProgressRateDTO.setProgressRate(progressRate);

            contain.addLectureProgressRate(lectureProgressRateDTO);
        }
    }
}
