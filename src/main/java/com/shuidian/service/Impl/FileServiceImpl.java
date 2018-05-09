package com.shuidian.service.Impl;

import com.google.common.collect.Lists;
import com.shuidian.service.IFileService;
import com.shuidian.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author: wangcheng
 * @Date: Created in 17:01 2018/4/13
 * 上传文件首先获取文件-->获取文件扩展名，拼接成新的文件名
 * 获取到上传文件的路径 --> 保存到tomcat下 -->通过二进制流上传到ftp服务器
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String uploadFile(MultipartFile file, String path){
        String fileName = file.getOriginalFilename();
        //扩展名 abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName, path, uploadFileName);
        File fileDir = new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);
        String targetFileName = targetFile.getName();
        try {
            file.transferTo(targetFile);
            //文件已经上传成功了，这里是上传到tomcat目录
            //todo 将targetFile上传到我们的FTP服务器
            boolean result = FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            if (result == false) {
                targetFileName = null;
            }
            //todo 上传完之后，删除upload下面的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常: ",e);
            return null;
        }
        return targetFileName;
    }
}
