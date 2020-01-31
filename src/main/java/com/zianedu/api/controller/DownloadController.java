package com.zianedu.api.controller;

import com.zianedu.api.dto.ApiResultObjectDTO;
import com.zianedu.api.utils.ZianApiUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/download")
public class DownloadController {

    @RequestMapping(value = "/fileDownload", method = RequestMethod.GET, produces = ZianApiUtils.APPLICATION_JSON)
    public ApiResultObjectDTO download(@RequestParam(value = "filePath") String filePath, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder("C:/fileServer/");
        //StringBuilder sb = new StringBuilder("/Users/jihoan/Documents/100/bbs/");
        sb.append(filePath);
        String saveFileName = sb.toString();

        String contentType = ZianApiUtils.DOWNLOAD_FORM_DATA;

        File file = new File(saveFileName);
        long fileLength = file.length();

        response.setHeader("Content-Disposition", "attachment; filename=\"" + filePath + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Length", "" + fileLength);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
        // 그 정보들을 가지고 reponse의 Header에 세팅한 후

        try (FileInputStream fis = new FileInputStream(saveFileName); OutputStream out = response.getOutputStream();) {
            // saveFileName을 파라미터로 넣어 inputStream 객체를 만들고
            // response에서 파일을 내보낼 OutputStream을 가져와서
            int readCount = 0;
            byte[] buffer = new byte[1024];
            // 파일 읽을 만큼 크기의 buffer를 생성한 후
            while ((readCount = fis.read(buffer)) != -1) {
                out.write(buffer, 0, readCount);
                // outputStream에 씌워준다
            }
        } catch (Exception ex) {
            throw new RuntimeException("file Load Error");
        }
        return null;
    }
}
