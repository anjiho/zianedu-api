package com.zianedu.api.vo;

import com.zianedu.api.dto.FileInfoDTO;
import lombok.Data;

import java.util.List;

@Data
public class ReferenceRoomDetailVO {

    private int bbsKey;

    private String indate;

    private String title;

    private int readCount;

    private String userName;

    private String userId;

    private String contents;

    private int isNotice;

    private int pwd;

    private List<FileInfoDTO> fileInfo;
}
