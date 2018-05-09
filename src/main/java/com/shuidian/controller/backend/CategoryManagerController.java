package com.shuidian.controller.backend;

import com.shuidian.domain.Category;
import com.shuidian.domain.UserInfo;
import com.shuidian.enums.Const;
import com.shuidian.enums.ResponseCode;
import com.shuidian.service.ICategoryService;
import com.shuidian.service.IUserService;
import com.shuidian.util.ServerResponse;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Author: wangcheng
 * @Date: Created in 11:43 2018/4/2
 */
@RestController
@RequestMapping("/category")
public class CategoryManagerController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    //新增品类
    @RequestMapping("/addCategory")
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") Integer parentId){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录, 请登录");
        }
        //校验一下是否是管理员
        if (iUserService.checkAdminRole(userInfo).isSuccess()){
            return iCategoryService.addCategory(categoryName, parentId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    //更新品类名字
    @RequestMapping("/setCategoryName")
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录, 请登录");
        }
        if (iUserService.checkAdminRole(userInfo).isSuccess()){
            //更新categoryname
            return iCategoryService.updateCategory(categoryId, categoryName);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    //获取平级子节点品类
    @RequestMapping(value = "/getParallelCategory", method = RequestMethod.GET)
    public ServerResponse getChildrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录, 请登录");
        }
        if (iUserService.checkAdminRole(userInfo).isSuccess()){
            //查询子节点的category信息，并且不递归,保持平级
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "/getDeepCategory", method = RequestMethod.GET)
    public ServerResponse getChildrenDeepCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录, 请登录");
        }
        if (iUserService.checkAdminRole(userInfo).isSuccess()){
            //查询当前子节点的id和递归子节点的id
            return iCategoryService.getChildrenDeepCategory(categoryId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

//    @RequestMapping("/test")
//    public ServerResponse<List<Category>> testJpa(){
//        return iCategoryService.contextLoads();
//    }
}
