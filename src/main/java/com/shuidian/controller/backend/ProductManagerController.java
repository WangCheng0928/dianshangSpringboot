package com.shuidian.controller.backend;

import com.google.common.collect.Maps;
import com.shuidian.domain.Product;
import com.shuidian.domain.UserInfo;
import com.shuidian.enums.Const;
import com.shuidian.enums.ResponseCode;
import com.shuidian.service.IFileService;
import com.shuidian.service.IProductService;
import com.shuidian.service.IUserService;
import com.shuidian.util.PropertiesUtil;
import com.shuidian.util.ServerResponse;
import com.shuidian.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author: wangcheng
 * @Date: Created in 10:44 2018/4/10
 */
@RestController
@RequestMapping("/manage/product")
public class ProductManagerController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    @RequestMapping("/save")
    public ServerResponse productSave(HttpSession session, Product product){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(userInfo).isSuccess()){
            return iProductService.saveOrUpdateProduct(product);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("/setSaleStatus")
    public ServerResponse setSaleStatus(HttpSession session, Integer prductId, Integer status){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(userInfo).isSuccess()){
            return iProductService.setSaleStatus(prductId, status);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("/getProductDetail")
    public ServerResponse getProductDetail(HttpSession session, Integer prductId){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(userInfo).isSuccess()){
            return iProductService.manageProductDetail(prductId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("/getList")
    public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10")Integer pageSize){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(userInfo).isSuccess()){
            return iProductService.getProductList(pageNum, pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("/searchProduct")
    public ServerResponse searchProduct(HttpSession session,String productName, Integer productId, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10")Integer pageSize){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(userInfo).isSuccess()){
            return iProductService.searchProduct(productName, productId, pageNum, pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("/uploadFile")
    public ServerResponse uploadFile(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        }
        if (iUserService.checkAdminRole(userInfo).isSuccess()){
            //        String path = request.getSession().getClass().getClassLoader().getResource("../upload").getPath();
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.uploadFile(file, path);
            if (targetFileName != null){
                String url = PropertiesUtil.getProperty(Const.FTP_PREFIX_KEY) + targetFileName;
                Map fileMap = Maps.newHashMap();
                fileMap.put("uri", targetFileName);
                fileMap.put("url", url);
                return ServerResponse.createBySuccess(fileMap);
            }else {
                return ServerResponse.createByErrorMessage("连接服务器失败");
            }
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("/richtextImgUpload")
    public Map richtextImgUpload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        Map resultMap = Maps.newHashMap();
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            resultMap.put("success",false);
            resultMap.put("msg", "请登录管理员");
            return resultMap;
        }
        //富文本中对于返回值有自己的要求,我们使用是simditor所以按照simditor的要求进行返回
//        {
//            "success": true/false,
//                "msg": "error message", # optional
//            "file_path": "[real file path]"
//        }
        if (iUserService.checkAdminRole(userInfo).isSuccess()){
            //        String path = request.getSession().getClass().getClassLoader().getResource("../upload").getPath();
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.uploadFile(file, path);
            if (StringUtils.isBlank(targetFileName)){
                resultMap.put("success",false);
                resultMap.put("msg", "上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty(Const.FTP_PREFIX_KEY) + targetFileName;
            resultMap.put("success",true);
            resultMap.put("msg", "上传成功");
            resultMap.put("file_path", url);
            response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
            return resultMap;
        }else {
            resultMap.put("success",false);
            resultMap.put("msg", "无权限操作");
            return resultMap;
        }
    }
}
