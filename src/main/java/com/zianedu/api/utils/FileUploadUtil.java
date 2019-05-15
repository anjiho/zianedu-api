package com.zianedu.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by jihoan on 2017. 7. 24..
 */
public class FileUploadUtil {

    protected final static Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

    public static HashMap<String, String> fileUpload(MultipartHttpServletRequest request, String savePath, String uploadType) {
        HashMap<String, String> map = new HashMap<>();
        String fileName = "";

        Iterator<String> it = request.getFileNames();
        try {
            while (it.hasNext()) {
                String uploadFileName = it.next();

                String filePath = "";
                if ("BANNER".equals(uploadType)) {
                    filePath = "100/main/";
                } else if ("VIDEO".equals(uploadType)) {
                    if("imageListFile".equals(uploadFileName)) {
                        filePath = "100/book/";
                    } else {
                        filePath = "100/lec/";
                    }
                } else if ("CURRI".equals(uploadType)) {
                    filePath = "100/lec/";
                } else if ("PREVIEW".equals(uploadType)) {
                    filePath = "100/res/";
                } else if ("ACADEMY".equals(uploadType)) {
                    if("imageListFile".equals(uploadFileName)) {
                        filePath = "100/promotion/";
                    } else {
                        filePath = "100/lec/";
                    }
                } else if ("BOOK".equals(uploadType)) {
                    filePath = "100/book/";
                } else if ("PACKAGE".equals(uploadType)) {
                    if("imageListFile".equals(uploadFileName)) {
                        filePath = "100/promotion/";
                    } else {
                        filePath = "100/promotion/";
                    }
                }

                if (uploadFileName != null || !"".equals(uploadFileName)) {
                    MultipartFile multipartFile = request.getFile(uploadFileName);
                    //한글 꺠짐 방지처리
                    String originalFileName = multipartFile.getOriginalFilename();
                    //파일명 변경
                    //String finalFileName = FileUploadRename.multipartFileRename(multipartFile).toString();
                    String makeFileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
                    int filePos = originalFileName.lastIndexOf(".");
                    String fileExtension = originalFileName.substring(filePos+1);
                    String finalFileName = makeFileName + "_" + Util.returnNowDateByYyyymmddhhmmss() + "." + fileExtension;

                    if (originalFileName != null || !"".equals(originalFileName)) {
                        File serverFile = new File(FileUtil.concatPath(savePath, filePath, finalFileName));
                        multipartFile.transferTo(serverFile);
                        //root경로 파일 삭제
                        FileUtil.fileDelete(finalFileName);
                        FileUtil.fileDelete(originalFileName);

                        fileName = serverFile.getName();
                    }
                    if ("BANNER".equals(uploadType)) {
                        map.put("filePath", filePath + fileName);
                    } else if ("VIDEO".equals(uploadType)) {    //동영상 상품 파일 업로드 시
                        if("imageListFile".equals(uploadFileName)) {
                            filePath = "100/book/";
                            map.put("imageListFilePath", filePath + fileName);
                        } else {
                            filePath = "100/lec/";
                            map.put("imageViewFilePath", filePath + fileName);
                        }
                    } else if ("CURRI".equals(uploadType)) {    //동영상 상품 > 강의입력 > 강의자료 파일 업로드 시
                        filePath = "100/lec/";
                        map.put("dataFilePath", filePath + fileName);
                    } else if ("PREVIEW".equals(uploadType)) {  //도서등록 > 미리보기 이미지 업로드 시
                        filePath = "100/res/";
                        map.put("previewFilePath", filePath + fileName);
                    } else if ("ACADEMY".equals(uploadType)) {  //학원강의 등록 파일 업로드 시
                        if("imageListFile".equals(uploadFileName)) {
                            filePath = "100/promotion/";
                            map.put("imageListFilePath", filePath + fileName);
                        } else {
                            filePath = "100/lec/";
                            map.put("imageViewFilePath", filePath + fileName);
                        }
                    } else if ("BOOK".equals(uploadType)) { //도서상품 파일 업로드 시
                        filePath = "100/book/";
                        if("imageListFile".equals(uploadFileName)) {
                            map.put("imageListFilePath", filePath + fileName);
                        } else {
                            map.put("imageViewFilePath", filePath + fileName);
                        }
                    } else if ("PACKAGE".equals(uploadType)) { //패키지 상품 파일 업로드 시
                        filePath = "100/promotion/";
                        if("imageListFile".equals(uploadFileName)) {
                            map.put("imageListFilePath", filePath + fileName);
                        } else {
                            map.put("imageViewFilePath", filePath + fileName);
                        }
                    }

                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static List<Map<String, Object>> fileUploadByVideoImg(MultipartHttpServletRequest request, String savePath, String uploadType) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        String fileName = "";

        Iterator<String> it = request.getFileNames();
        try {
            while (it.hasNext()) {
                String uploadFileName = it.next();
                //uploadFileName : 프론트에서 정의한 프로퍼티(imageListFile,.. )
                if (uploadFileName != null || !"".equals(uploadFileName)) {
                    MultipartFile multipartFile = request.getFile(uploadFileName);
                    //한글 꺠짐 방지처리
                    String originalFileName = multipartFile.getOriginalFilename();
                    /** 파일명이 한글일때 에러 처리 **/
                    /*
                    if (StringUtil.isKorean(originalFileName)) {
                        return fileName;
                    }
                    */
                    //파일명 변경
                    //String finalFileName = FileUploadRename.multipartFileRename(multipartFile).toString();
                    String makeFileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
                    int filePos = originalFileName.lastIndexOf(".");
                    String fileExtension = originalFileName.substring(filePos+1);
                    String finalFileName = makeFileName + "_" + Util.returnNowDateByYyyymmddhhmmss() + "." + fileExtension;

                    if (originalFileName != null || !"".equals(originalFileName)) {
                        File serverFile = new File(FileUtil.concatPath(savePath, finalFileName));
                        multipartFile.transferTo(serverFile);
                        //root경로 파일 삭제
                        FileUtil.fileDelete(finalFileName);
                        FileUtil.fileDelete(originalFileName);

                        fileName = serverFile.getName();
                    }
                    String filePath = "";
//                    if ("BANNER".equals(uploadType)) {
//                        filePath = "100\\main\\";
//                    }
                    String imageListFilePath = "100\\book\\";
                    String imageViewFilePath = "100\\lec\\";

                    map.put("imageList", imageListFilePath + fileName);
                    map.put("imageView", imageViewFilePath + fileName);
                }
                list.add(map);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }



    public static Map<String, Object> boardFileUpload(MultipartHttpServletRequest request, String savePath) {
        List<MultipartFile> mf = request.getFiles("file_name");
        if (mf.size() == 0) return null;

        Map<String, Object> map = new HashMap<>();
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
                    map.put("file_name", fileName);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static int size(Iterable data) {

        if (data instanceof Collection) {
            return ((Collection<?>) data).size();
        }
        int counter = 0;
        for (Object i : data) {
            counter++;
        }
        return counter;
    }

}
