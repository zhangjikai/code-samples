package com.zhangjikai.controller;

import com.zhangjikai.pojo.Message;
import com.zhangjikai.pojo.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;

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
    public Message uploadFileHandler(@RequestParam("file") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            InputStream in = null;
            OutputStream out = null;

            try {
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
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

                Message msg = new Message();
                msg.setStatus(Status.SUCCESS);
                msg.setStatusMsg("File upload success");
                return msg;
            } catch (Exception e) {
                Message msg = new Message();
                msg.setStatus(Status.ERROR);
                msg.setError("File upload file");
                return msg;
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
            Message msg = new Message();
            msg.setStatus(Status.ERROR);
            msg.setError("File is empty");
            return msg;
        }
    }

    /**
     * Upload multiple file using Spring Controller
     */
    @RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Message uploadMultipleFileHandler(@RequestParam("file") MultipartFile[] files) throws IOException {

        Message msg = new Message();
        ArrayList<Integer> arr = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];

            if (!file.isEmpty()) {
                InputStream in = null;
                OutputStream out = null;

                try {
                    String rootPath = System.getProperty("catalina.home");
                    File dir = new File(rootPath + File.separator + "tmpFiles");
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


                } catch (Exception e) {
                    arr.add(i);
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
                arr.add(i);
            }


        }

        if(arr.size() > 0) {
            msg.setStatus(Status.ERROR);
            msg.setError("Files upload fail");
            msg.setErrorKys(arr);
        } else {
            msg.setStatus(Status.SUCCESS);
            msg.setStatusMsg("Files upload success");
        }
        return msg;
    }
}
