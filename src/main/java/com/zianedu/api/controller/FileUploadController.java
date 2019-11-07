package com.zianedu.api.controller;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.dto.ApiResultCodeDTO;
import com.zianedu.api.service.FileUploadService;
import com.zianedu.api.utils.ZianApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/fileUpload")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping(value = "/boardFile", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("게시판 파일 업로드")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "게시판 파일", dataType = "file", paramType = "form", required = true)
    })
    public ApiResultCodeDTO boardFileUpload(MultipartHttpServletRequest request) {
        String savePath = ConfigHolder.getFileUploadPath() + "/100/bbs";
        Map<String, String> resultMap = fileUploadService.boardFileUpload(request, savePath);
        return new ApiResultCodeDTO("fileName", resultMap.get("file_name"), 200);
    }

    @RequestMapping(value = "/boardFileList", method = RequestMethod.POST, produces = ZianApiUtils.APPLICATION_JSON)
    @ApiOperation("게시판 파일 다중 업로드")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "게시판 파일", dataType = "file", paramType = "form", required = true)
    })
    public ApiResultCodeDTO boardFileListUpload(MultipartHttpServletRequest request) throws Exception {
        String savePath = ConfigHolder.getFileUploadPath() + "/100/bbs";
        List<String> resultList  = fileUploadService.boardFileListUpload(request, savePath);
        return new ApiResultCodeDTO("fileNameList", resultList, 200);
    }
}
