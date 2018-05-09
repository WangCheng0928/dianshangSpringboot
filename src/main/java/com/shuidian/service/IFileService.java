package com.shuidian.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: wangcheng
 * @Date: Created in 17:01 2018/4/13
 */
public interface IFileService {
    String uploadFile(MultipartFile file, String path);
}
