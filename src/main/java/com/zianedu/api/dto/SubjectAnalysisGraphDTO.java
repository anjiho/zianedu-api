package com.zianedu.api.dto;

import com.zianedu.api.vo.ScoreRateGraphVO;
import lombok.Data;

import java.util.List;

@Data
public class SubjectAnalysisGraphDTO {
    //과목명
    private String subjectName;
    //유형별 정답률(해당 회차별)
    private ScoreRateGraphVO scoreRateByTypeInfo;
    //유형별 정답률(누적)
    private ScoreRateGraphVO scoreRateByTypeInfo2;
    //패턴별 정답률(해당 회차별)
    private ScoreRateGraphVO scoreRateByPatternInfo;
    //패턴별 정답률(누적)
    private ScoreRateGraphVO scoreRateByPatternInfo2;
    //대단원별 정답률(해당 회차별)
    private ScoreRateGraphVO scoreRateByUnitInfo;
    //대단원별 정답률(누적)
    private ScoreRateGraphVO scoreRateByUnitInfo2;
}
