package com.shuidian.service;

import com.shuidian.domain.UserInfo;
import com.shuidian.util.ServerResponse;

/**
 * @Author: wangcheng
 * @Date: Created in 10:01 2018/2/27
 */
public interface IUserService {
    ServerResponse<UserInfo> login(String username, String password);
    ServerResponse<String> register(UserInfo userInfo);
    ServerResponse selectQuestion(String username);
    ServerResponse<String> checkAnswer(String username, String question, String answer);
    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, UserInfo userInfo);
    ServerResponse<UserInfo> updateInformation(UserInfo userInfo);
    ServerResponse<UserInfo> getDetailUserInfo(Integer userId);
    ServerResponse checkAdminRole(UserInfo userInfo);
}
