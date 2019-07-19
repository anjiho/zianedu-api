package com.zianedu.api.dto;

import com.zianedu.api.vo.ScoreRateGraphVO;
import lombok.Data;


@Data
public class ScoreRateDTO {

    private ScoreRateGraphVO scoreRateByTypeInfo;
    private ScoreRateGraphVO scoreRateByPatternInfo;
    private ScoreRateGraphVO scoreRateByUnitInfo;

    public ScoreRateDTO(){}

    public ScoreRateDTO(ScoreRateGraphVO scoreRateByTypeInfo, ScoreRateGraphVO scoreRateByPatternInfo, ScoreRateGraphVO scoreRateByUnitInfo) {
        this.scoreRateByTypeInfo = scoreRateByTypeInfo;
        this.scoreRateByPatternInfo = scoreRateByPatternInfo;
        this.scoreRateByUnitInfo = scoreRateByUnitInfo;
    }
}
