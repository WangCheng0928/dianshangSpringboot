package com.shuidian.service.Impl;

import com.shuidian.domain.UserInfo;
import com.shuidian.enums.Const;
import com.shuidian.repository.UserInfoRepository;
import com.shuidian.service.IUserService;
import com.shuidian.util.MD5Util;
import com.shuidian.util.ServerResponse;
import com.shuidian.util.StringUtils;
import com.shuidian.util.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @Author: wangcheng
 * @Date: Created in 10:09 2018/2/27
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    public ServerResponse<String> checkRegister(UserInfo userInfo){
        String username = userInfo.getUsername();
        String email = userInfo.getEmail();
        String phone = userInfo.getPhone();
        if (StringUtils.isNotBlank(username)){
            //开始校验
            System.out.println(username.matches(Const.USERNAME));
            if (username.matches(Const.USERNAME)){
                Long resultCount = userInfoRepository.countByUsername(username);
                if (resultCount > 0){
                    return ServerResponse.createByErrorMessage("该用户已存在");
                }
            }else {
                return ServerResponse.createByErrorMessage("用户名不合法");
            }
        }
        if(StringUtils.isNotBlank(email)){
            //开始校验
            if (email.matches(Const.EMAIL)){
                Long resultCount = userInfoRepository.countByEmail(email);
                if (resultCount > 0){
                    return ServerResponse.createByErrorMessage("该邮箱已存在");
                }
            }else {
                return ServerResponse.createByErrorMessage("请输入正确的邮箱");
            }
        }
        if (StringUtils.isNotBlank(phone)){
            // 开始校验
            if (phone.matches(Const.PHONE)){
                Long resultCount = userInfoRepository.countByPhone(phone);
                if (resultCount > 0){
                    return ServerResponse.createByErrorMessage("该手机号已存在");
                }
            }else {
                return ServerResponse.createByErrorMessage("请输入正确的手机号");
            }
        }
        return ServerResponse.createBySuccess();
    }

    public ServerResponse<String> checkLogin(String str){
        if (StringUtils.isNotBlank(str)){
            //开始校验
            if (str.matches(Const.USERNAME)){
                Long resultCount = userInfoRepository.countByUsername(str);
                if (resultCount==0){
                    return ServerResponse.createByErrorMessage("该用户不存在");
                }
            } else if (str.matches(Const.PHONE)){
                Long resultCount = userInfoRepository.countByPhone(str);
                if (resultCount == 0){
                    return ServerResponse.createByErrorMessage("该手机号不存在");
                }
            }else if (str.matches(Const.EMAIL)){
                Long resultCount = userInfoRepository.countByEmail(str);
                if (resultCount==0){
                    return ServerResponse.createByErrorMessage("该邮箱不存在");
                }
            }else {
                return ServerResponse.createByErrorMessage("用户名格式不正确");
            }
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse<UserInfo> login(String username, String password) {
        Long result = userInfoRepository.countByUsername(username);
        if (result == 0){
            return ServerResponse.createByErrorMessage("用户名不存在！");
        }

        //todo 密码登录MD5
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        UserInfo userInfo = userInfoRepository.findByUsernameAndPassword(username, md5Password);
        if (userInfo == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        userInfo.setPassword("");
        return ServerResponse.createBySuccess("登录成功", userInfo);
    }

    @Override
    public ServerResponse<String> register(UserInfo userInfo) {
        try {
            ServerResponse<String> checkResult = this.checkRegister(userInfo);
            if (!checkResult.isSuccess()){
                return checkResult;
            }
            if (userInfo.getRole() == null){
                userInfo.setRole(Const.Role.ROLE_CUSTOMER);
            }else {
                userInfo.setRole(userInfo.getRole());
            }
            userInfo.setPassword(MD5Util.MD5EncodeUtf8(userInfo.getPassword()));
            Date date = new Date();
            userInfo.setCreateTime(date);
            userInfoRepository.save(userInfo);
            return ServerResponse.createBySuccessMessage("注册成功");
        }catch (Exception e){
            return ServerResponse.createByErrorMessage("注册失败");
        }
    }

    public ServerResponse selectQuestion(String username){
        ServerResponse checkResponse = this.checkLogin(username);
        if (!checkResponse.isSuccess()){
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userInfoRepository.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题是空的");
    }

    public ServerResponse<String> checkAnswer(String username, String question, String answer){
        int resultCount = userInfoRepository.countByUsernameAndQuestionAndAnswer(username, question, answer);
        if (resultCount > 0){
            //说明问题及问题答案是这个用户的，并且是正确的
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }

    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken){
        if (StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("参数错误,token需重新传递");
        }
        ServerResponse checkResponse = this.checkLogin(username);
        if (!checkResponse.isSuccess()){
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
        if (StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("token无效或者过期");
        }
        if (StringUtils.equals(forgetToken, token)){
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int resultCount = userInfoRepository.updatePasswordByUsername(username, md5Password);
            if (resultCount > 0){
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
        }else {
            return ServerResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, UserInfo userInfo){
        //防止横向越权，要校验一下这个用户的旧密码，一定要指定是这个用户,因为我们会查询一个count(1)，如果不指定id，那么结果就是true了count>0
        int resultCount = userInfoRepository.countByPasswordAndId(MD5Util.MD5EncodeUtf8(passwordOld), userInfo.getId());
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        int updateCount = userInfoRepository.updatePasswordById(MD5Util.MD5EncodeUtf8(passwordNew), userInfo.getId());
        if (updateCount > 0){
            return ServerResponse.createBySuccessMessage("修改密码成功");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    public ServerResponse<UserInfo> updateInformation(UserInfo userInfo){
        try{
            //username是不能被更新的
            //email也要进行一个校验，检验新的email是不是已存在,并且存在的email如果相同的话，不能是当前用户的
            Long resultCount = userInfoRepository.countByEmail(userInfo.getEmail());
            UserInfo currentUser = userInfoRepository.findOne(userInfo.getId());
            if (resultCount > 0){
                if (StringUtils.equals(currentUser.getEmail(), userInfo.getEmail())){
                    return ServerResponse.createByErrorMessage("新旧邮箱不能重复");
                }
                return ServerResponse.createByErrorMessage("邮箱已经被占用");
            }
            currentUser.setEmail(userInfo.getEmail());
            currentUser.setAnswer(userInfo.getAnswer());
            currentUser.setQuestion(userInfo.getQuestion());
            currentUser.setQQ(userInfo.getQQ());
            userInfoRepository.save(currentUser);
            currentUser.setPassword("");
            return ServerResponse.createBySuccess("更新个人信息成功", currentUser);
        }catch (Exception e){
            return ServerResponse.createByErrorMessage("更新个人信息失败");
        }
    }

    public ServerResponse<UserInfo> getDetailUserInfo(Integer userId){
        UserInfo userInfo = userInfoRepository.findOne(userId);
        if(userInfo == null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        userInfo.setPassword("");
        return ServerResponse.createBySuccess(userInfo);
    }


    // backend
    // 校验是否是管理员
    public ServerResponse checkAdminRole(UserInfo userInfo){
        if (userInfo != null && userInfo.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
}
