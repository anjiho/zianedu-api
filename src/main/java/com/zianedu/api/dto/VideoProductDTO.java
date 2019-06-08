package com.zianedu.api.dto;

import com.zianedu.api.vo.LectureBookVO;
import com.zianedu.api.vo.TGoodsPriceOptionVO;
import com.zianedu.api.vo.TLecCurriVO;
import com.zianedu.api.vo.VideoLectureDetailVO;
import lombok.Data;

import java.util.List;

@Data
public class VideoProductDTO {

    private VideoLectureDetailVO videoLectureDetailInfo;

    private List<TGoodsPriceOptionVO> goodsPriceOptionInfo;

    private List<LectureBookVO> lectureBookInfo;

    private List<TLecCurriVO> lectureList;

    public VideoProductDTO(){}

    public VideoProductDTO(VideoLectureDetailVO videoLectureDetailInfo, List<TGoodsPriceOptionVO> goodsPriceOptionInfo,
                           List<LectureBookVO> lectureBookInfo, List<TLecCurriVO> lectureList) {
        this.videoLectureDetailInfo = videoLectureDetailInfo;
        this.goodsPriceOptionInfo = goodsPriceOptionInfo;
        this.lectureBookInfo = lectureBookInfo;
        this.lectureList = lectureList;
    }
}
