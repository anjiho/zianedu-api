package com.zianedu.api.vo;

import com.zianedu.api.dto.FileInfoDTO;
import lombok.Data;

import java.util.List;

@Data
public class BoardDetailVO {

    private int bbsKey;

    private String title;

    private String indate;

    private int readCount;

    private String fileName;

    private String contents;

    private String userId;

    private String userName;

    private String fileUrl;

    private String fileDetailName;

    private int ctgKey;

    private int writeUserKey;

    private String youtubeHtml;

    private int gKey;

    private String successSubject;

    private String lectureSubject;

    private List<FileInfoDTO> fileInfo;
}
