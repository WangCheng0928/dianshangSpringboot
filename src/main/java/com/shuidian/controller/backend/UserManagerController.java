package com.shuidian.controller.backend;

import com.shuidian.domain.UserInfo;
import com.shuidian.enums.Const;
import com.shuidian.service.IUserService;
import com.shuidian.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author: wangcheng
 * @Date: Created in 10:28 2018/4/2
 */
@RestController
@RequestMapping("/manage/User")
public class UserManagerController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "/adminlogin", method = RequestMethod.POST)
    public ServerResponse<UserInfo> login(String username, String password, HttpSession session){
        ServerResponse<UserInfo> response = iUserService.login(username, password);
        if (response.isSuccess()){
            UserInfo userInfo = response.getData();
            if (userInfo.getRole() == Const.Role.ROLE_ADMIN){
                // 说明是管理员登录
                session.setAttribute(Const.CURRRENT_USER, userInfo);
                return  response;
            }else {
                return ServerResponse.createByErrorMessage("不是管理员，无法登录");
            }
        }
        return response;
    }
}
