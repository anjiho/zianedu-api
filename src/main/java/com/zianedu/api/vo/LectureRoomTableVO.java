package com.zianedu.api.vo;

import lombok.Data;

@Data
public class LectureRoomTableVO {

    private String lectureDate;

    private int academyNumber;

    private String fileName;

    public LectureRoomTableVO(){}

    public LectureRoomTableVO(String lectureDate, int academyNumber, String fileName) {
        this.lectureDate = lectureDate;
        this.academyNumber = academyNumber;
        this.fileName = fileName;
    }
}
