package com.zianedu.api.vo;

import lombok.Data;

import java.util.List;

@Data
public class TeacherHomeVO {

    private TTeacherVO teacherInfo;

    private List<TBbsDataVO> referenceRoom;

    private List<TBbsDataVO> learningQna;

    private List<GoodsReviewVO> lectureReview;

    private List<GoodsListVO> videoLecture;

    private List<GoodsListVO> academyLecture;

    private List<BannerBookVO> teacherBook;

    public TeacherHomeVO() {}

    public TeacherHomeVO(TTeacherVO teacherInfo, List<TBbsDataVO> referenceRoom, List<TBbsDataVO> learningQna, List<GoodsReviewVO> lectureReview,
                         List<GoodsListVO> videoLecture, List<GoodsListVO> academyLecture, List<BannerBookVO> teacherBook) {
        this.teacherInfo = teacherInfo;
        this.referenceRoom = referenceRoom;
        this.learningQna = learningQna;
        this.lectureReview = lectureReview;
        this.videoLecture = videoLecture;
        this.academyLecture = academyLecture;
        this.teacherBook = teacherBook;
    }

}
