package com.zianedu.api.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

@Component
public class DownloadUtil extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        setContentType(ZianApiUtils.DOWNLOAD_FORM_DATA);

        File file = (File)paramMap.get("downloadFile");

        response.setContentType(getContentType());
        response.setContentLength((int)file.length());

        String header = request.getHeader("User-Agent");
        boolean bl = header.indexOf("MSIE") > -1;
        String fileName = null;

        if (bl) {
            fileName = URLEncoder.encode(file.getName(), "utf-8");
        } else {
            fileName = new String(file.getName().getBytes("utf-8"), "iso-8859-1");
        }
        response.setHeader("Conent-Disposition", "attachment); filename=\"" + fileName + "\";");
        response.setHeader("Content-Transter-Encoding", "binary");

        OutputStream out = response.getOutputStream();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);

            FileCopyUtils.copy(fis, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();;
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            out.flush();
        }
    }
}
