package com.zianedu.api.dto;

import com.zianedu.api.vo.NoticeVO;
import lombok.Data;

import java.util.List;

@Data
public class BannerNoticeDTO {
    //개강안내
    private List<NoticeVO> subjectOpenNoticeInfo;
    //학원소식
    private List<NoticeVO> academyInfo;
    //동영상소식
    private List<NoticeVO> videoInfo;

    public BannerNoticeDTO(){}

    public BannerNoticeDTO(List<NoticeVO> subjectOpenNoticeInfo, List<NoticeVO> academyInfo, List<NoticeVO> videoInfo) {
        this.subjectOpenNoticeInfo = subjectOpenNoticeInfo;
        this.academyInfo = academyInfo;
        this.videoInfo = videoInfo;
    }
}
