package com.zianedu.api.service;

import com.zianedu.api.utils.FileUtil;
import com.zianedu.api.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class FileUploadService {

    protected final static Logger logger = LoggerFactory.getLogger(FileUploadService.class);

    public Map<String, String> boardFileUpload(MultipartHttpServletRequest request, String savePath) {
        MultipartFile mf = request.getFile("file");

        if (mf == null) return null;

        Map<String, String> resultMap = new HashMap<>();
        String fileName = "";
        Iterator<String> it = request.getFileNames();

        try {
            while (it.hasNext()) {
                String uploadFileName = it.next();
                logger.info("uploadFileName>>>>>>>> " + uploadFileName);

                if (uploadFileName != null || !"".equals(uploadFileName)) {
                    MultipartFile multipartFile = request.getFile(uploadFileName);
                    logger.info("multipartFile>>>>>>>> " + uploadFileName);
                    /*
                    long fileSize = multipartFile.getSize();
                    //이미지 용량 제한 500kb
                    if (fileSize > 500000) {
                        map.put("error_code", FlowEduErrorCode.CUSTOM_IMAGE_FILE_SIZE_LIMIT.code());
                        return map;
                    }
                    */
                    //한글 꺠짐 방지처리
                    String originalFileName = multipartFile.getOriginalFilename();
                    /** 파일명이 한글일때 에러 처리 **/
                    /*
                    if (StringUtil.isKorean(originalFileName)) {
                        map.put("error_code", FlowEduErrorCode.CUSTOM_IMAGE_FILE_NAME_KOREAN.code());
                        return map;
                    }
                    */
                    //파일명 변경
                    //String finalFileName = FileUploadRename.multipartFileRename(multipartFile).toString();
                    String makeFileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
                    int filePos = originalFileName.lastIndexOf(".");
                    String fileExtension = originalFileName.substring(filePos+1);
                    //파일 확장자 예외처리
                    /*
                    if (!("hwp".equalsIgnoreCase(fileExtension) || "doc".equalsIgnoreCase(fileExtension)
                            || "docx".equalsIgnoreCase(fileExtension) || "pdf".equalsIgnoreCase(fileExtension))) {
                        map.put("error_code", FlowEduErrorCode.CUSTOM_IMAGE_FILE_EXTENSION_NOT_ALLOW.code());
                        return map;
                    }
                    */
                    String finalFileName = makeFileName + "_" + Util.returnNowDateByYyyymmddhhmmss() + "." + fileExtension;
                    //디렉토리 존재 확인
                    File uploadDirectory = new File(savePath);
                    if (!uploadDirectory.isDirectory()) {
                        uploadDirectory.mkdirs();
                    }
                    if (originalFileName != null || !"".equals(originalFileName)) {
                        File serverFile = new File(FileUtil.concatPath(uploadDirectory.toString(), finalFileName));
                        logger.info("serverFile ---------------> " + serverFile);
                        multipartFile.transferTo(serverFile);
                        //root경로 파일 삭제
                        FileUtil.fileDelete(finalFileName);
                        FileUtil.fileDelete(originalFileName);
                        logger.info("originalFileName ---------------> " + originalFileName);

                        fileName = serverFile.getName();
                    }
                    resultMap.put("file_name", fileName);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public List<String> boardFileListUpload(MultipartHttpServletRequest request, String savePath) throws Exception {
        List<String>resultList = new ArrayList<>();

        List<MultipartFile> fileList = request.getFiles("files");
        if (fileList.size() > 0) {
            for (MultipartFile mf : fileList ) {
                String originFileName = mf.getOriginalFilename();
                long fileSize = mf.getSize();

                logger.info("originFileName >> " + originFileName);
                logger.info("fileSize >> " + fileSize);

                String makeFileName = originFileName.substring( 0, originFileName.lastIndexOf(".") );
                int filePos = originFileName.lastIndexOf(".");
                String fileExtension = originFileName.substring(filePos+1);

                String finalFileName = makeFileName + "_" + Util.returnNowDateByYyyymmddhhmmss() + "." + fileExtension;
                //디렉토리 존재 확인
                File uploadDirectory = new File(savePath);
                if (!uploadDirectory.isDirectory()) {
                    uploadDirectory.mkdirs();
                }
                File serverFile = new File(FileUtil.concatPath(uploadDirectory.toString(), finalFileName));
                logger.info("serverFile ---------------> " + serverFile);
                mf.transferTo(serverFile);
                //root경로 파일 삭제
                FileUtil.fileDelete(finalFileName);
                FileUtil.fileDelete(originFileName);
                logger.info("originalFileName ---------------> " + originFileName);

                resultList.add(serverFile.getName());
            }
        }
        return resultList;
    }
}
