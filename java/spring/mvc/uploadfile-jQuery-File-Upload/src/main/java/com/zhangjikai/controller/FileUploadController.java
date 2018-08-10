package com.zhangjikai.controller;

import com.zhangjikai.pojo.FileMeta;
import com.zhangjikai.pojo.FileMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjk on 2016/1/14.
 */
@RestController
public class FileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    /**
     * Upload single file using Spring Controller
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public FileMsg uploadFileHandler(@RequestParam("file") MultipartFile file) throws IOException {


        if (!file.isEmpty()) {
            InputStream in = null;
            OutputStream out = null;

            try {
                // 获得在tomcat中项目的路径， 需要在web.xml配置ft.webapp
                String webRootPath = System.getProperty("ft.webapp");
                //logger.info(webRootPath);
               // String rootPath = System.getProperty("catalina.home");
                File dir = new File(webRootPath + File.separator + "uploadFiles");
                if (!dir.exists())
                    dir.mkdirs();
                File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                in = file.getInputStream();
                out = new FileOutputStream(serverFile);
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = in.read(b)) > 0) {
                    out.write(b, 0, len);
                }
                out.close();
                in.close();
                logger.info("Server File Location=" + serverFile.getAbsolutePath());

                FileMeta fileMeta = new FileMeta();
                fileMeta.setName(file.getOriginalFilename());
                fileMeta.setUrl("uploadFiles" + File.separator + file.getOriginalFilename());
                List<FileMeta> files = new ArrayList<>();
                files.add(fileMeta);
                FileMsg fileMsg = new FileMsg();
                fileMsg.setFiles(files);
                return fileMsg;

            } catch (Exception e) {
                FileMeta fileMeta = new FileMeta();
                fileMeta.setName(file.getOriginalFilename());
                fileMeta.setUrl("uploadFiles" + File.separator + file.getOriginalFilename());
                List<FileMeta> files = new ArrayList<>();
                files.add(fileMeta);
                FileMsg fileMsg = new FileMsg();
                fileMsg.setFiles(files);
                return fileMsg;
            } finally {
                if (out != null) {
                    out.close();
                    out = null;
                }

                if (in != null) {
                    in.close();
                    in = null;
                }
            }
        } else {
            FileMeta fileMeta = new FileMeta();
            fileMeta.setName(file.getOriginalFilename());
            fileMeta.setUrl("uploadFiles" + File.separator + file.getOriginalFilename());
            List<FileMeta> files = new ArrayList<>();
            files.add(fileMeta);
            FileMsg fileMsg = new FileMsg();
            fileMsg.setFiles(files);
            return fileMsg;
        }
    }
}
