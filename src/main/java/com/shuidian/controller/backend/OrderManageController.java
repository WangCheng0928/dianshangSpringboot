package com.shuidian.controller.backend;

import com.github.pagehelper.PageInfo;
import com.shuidian.VO.OrderVo;
import com.shuidian.domain.UserInfo;
import com.shuidian.enums.Const;
import com.shuidian.enums.ResponseCode;
import com.shuidian.service.IOrderService;
import com.shuidian.service.IUserService;
import com.shuidian.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author: wangcheng
 * @Date: Created in 20:03 2018/5/3
 */
@RestController
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("/list")
    public ServerResponse<PageInfo> orderList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录, 请登录");
        }
        //校验一下是否是管理员
        if (iUserService.checkAdminRole(userInfo).isSuccess()){
            return iOrderService.manageList(pageNum, pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping("/detail")
    public ServerResponse<OrderVo> orderDetail(HttpSession session, Long orderNo){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录, 请登录");
        }
        //校验一下是否是管理员
        if (iUserService.checkAdminRole(userInfo).isSuccess()){
            return iOrderService.manageDetail(orderNo);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping("/search")
    public ServerResponse<PageInfo> search(HttpSession session, Long orderNo,
                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录, 请登录");
        }
        //校验一下是否是管理员
        if (iUserService.checkAdminRole(userInfo).isSuccess()){
            return iOrderService.manageSearch(orderNo, pageNum, pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping("/sendGoods")
    public ServerResponse<String> sendGoods(HttpSession session, Long orderNo){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录, 请登录");
        }
        //校验一下是否是管理员
        if (iUserService.checkAdminRole(userInfo).isSuccess()){
            return iOrderService.manageSendGoods(orderNo);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

}
