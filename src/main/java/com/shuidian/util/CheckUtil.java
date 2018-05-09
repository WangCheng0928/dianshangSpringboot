//package com.shuidian.util;
//
//import com.shuidian.domain.UserInfo;
//import com.shuidian.enums.Const;
//import com.shuidian.repository.UserInfoRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * @Author: wangcheng
// * @Date: Created in 10:40 2018/3/28
// */
//public class CheckUtil {
//
//    @Autowired
//    private UserInfoRepository userInfoRepository;
//
//    public ServerResponse<String> checkRegister(UserInfo userInfo){
//        String username = userInfo.getUsername();
//        String email = userInfo.getEmail();
//        String phone = userInfo.getPhone();
//        if (StringUtils.isNotBlank(username)){
//            //开始校验
//            System.out.println(username.matches(Const.USERNAME));
//            if (username.matches(Const.USERNAME)){
//                Long resultCount = userInfoRepository.countByUsername(username);
//                if (resultCount > 0){
//                    return ServerResponse.createByErrorMessage("该用户已存在");
//                }else if (resultCount == 0){
//                    return ServerResponse.createBySuccess();
//                }
//            }else {
//                return ServerResponse.createByErrorMessage("用户名不合法");
//            }
//        }
//        if(StringUtils.isNotBlank(email)){
//            //开始校验
//            if (email.matches(Const.EMAIL)){
//                Long resultCount = userInfoRepository.countByEmail(email);
//                if (resultCount > 0){
//                    return ServerResponse.createByErrorMessage("该邮箱已存在");
//                }
//            }else {
//                return ServerResponse.createByErrorMessage("请输入正确的邮箱");
//            }
//        }
//        if (StringUtils.isNotBlank(phone)){
//            // 开始校验
//            if (phone.matches(Const.PHONE)){
//                Long resultCount = userInfoRepository.countByPhone(phone);
//                if (resultCount > 0){
//                    return ServerResponse.createByErrorMessage("该手机号已存在");
//                }
//            }else {
//                return ServerResponse.createByErrorMessage("请输入正确的手机号");
//            }
//        }
//        return ServerResponse.createBySuccess();
//    }
//
//    public ServerResponse<String> checkLogin(String str){
//        if (StringUtils.isNotBlank(str)){
//            //开始校验
//            if (str.matches(Const.USERNAME)){
//                Long resultCount = userInfoRepository.countByUsername(str);
//                if (resultCount==0){
//                    return ServerResponse.createByErrorMessage("该用户不存在");
//                }
//            } else if (str.matches(Const.PHONE)){
//                Long resultCount = userInfoRepository.countByPhone(str);
//                if (resultCount == 0){
//                    return ServerResponse.createByErrorMessage("该手机号不存在");
//                }
//            }else if (str.matches(Const.EMAIL)){
//                Long resultCount = userInfoRepository.countByEmail(str);
//                if (resultCount==0){
//                    return ServerResponse.createByErrorMessage("该邮箱不存在");
//                }
//            }else {
//                return ServerResponse.createByErrorMessage("用户名格式不正确");
//            }
//        }
//        return ServerResponse.createBySuccess();
//    }
//}
