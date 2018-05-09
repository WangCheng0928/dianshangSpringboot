package com.shuidian.controller.user;

import com.shuidian.domain.UserInfo;
import com.shuidian.enums.Const;
import com.shuidian.enums.ResponseCode;
import com.shuidian.service.IUserService;
import com.shuidian.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author: wangcheng
 * @Date: Created in 15:23 2018/1/23
 */
@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private IUserService iUserService;

    //登录
    @RequestMapping(value = "login", method = RequestMethod.POST  )
    public ServerResponse<UserInfo> login(String username, String password, HttpSession session){
        ServerResponse<UserInfo> response = iUserService.login(username, password);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRRENT_USER, response.getData());
        }
        return response;
    }

    //登出
    @RequestMapping("/logout")
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRRENT_USER);
        return ServerResponse.createBySuccess();
    }

    //注册
    @RequestMapping("/register")
    public ServerResponse<String> register(UserInfo userInfo){
        ServerResponse<String> response = iUserService.register(userInfo);
        return response;
    }

    //获取用户信息
    @RequestMapping("/getUserInfo")
    public ServerResponse<UserInfo> getUserInfo(HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo != null){
            return ServerResponse.createBySuccess(userInfo);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
    }

    //忘记密码时获取密码问题
    @RequestMapping(value = "/forgetGetQuestion", method = RequestMethod.POST)
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }

    //忘记密码时验证问题答案
    @RequestMapping(value = "/forgetCheckAnswer", method = RequestMethod.POST)
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer){
        return iUserService.checkAnswer(username, question, answer);
    }

    //忘记密码时重置密码
    @RequestMapping(value = "/forgetResetPassword", method = RequestMethod.POST)
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken){
        return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    //登录状态下重置密码
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld,passwordNew, userInfo);
    }

    //更新用户信息
    @RequestMapping(value = "/updateInformation", method = RequestMethod.POST)
    public ServerResponse<UserInfo> updateInformation(HttpSession session, UserInfo userInfo){
        UserInfo currentUser = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        userInfo.setId(currentUser.getId());
        ServerResponse<UserInfo> response = iUserService.updateInformation(userInfo);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRRENT_USER, response.getData());
        }
        return response;
    }

    //获取用户详细信息
    @RequestMapping(value = "getDetailUserInfo", method = RequestMethod.POST)
    public ServerResponse<UserInfo> getDetailUserInfo(HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录, 需要强制登录status=10");
        }
        return iUserService.getDetailUserInfo(userInfo.getId());
    }
}
