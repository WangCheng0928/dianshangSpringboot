package com.shuidian.controller.user;

import com.github.pagehelper.PageInfo;
import com.shuidian.domain.Shipping;
import com.shuidian.domain.UserInfo;
import com.shuidian.enums.Const;
import com.shuidian.enums.ResponseCode;
import com.shuidian.service.IShippingService;
import com.shuidian.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author: wangcheng
 * @Date: Created in 19:30 2018/4/23
 */

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService;

    @RequestMapping("/add")
    public ServerResponse add(HttpSession session, Shipping shipping){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.add(userInfo.getId(), shipping);
    }

    @RequestMapping("/del")
    public ServerResponse del(HttpSession session, Integer shippingId){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.del(userInfo.getId(), shippingId);
    }

    @RequestMapping("/update")
    public ServerResponse update(HttpSession session, Shipping shipping){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.update(userInfo.getId(), shipping);
    }

    @RequestMapping("/select")
    public ServerResponse select(HttpSession session, Integer shippingId){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.select(userInfo.getId(), shippingId);
    }

    @RequestMapping("/list")
    public ServerResponse<PageInfo>List(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                        HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.list(userInfo.getId(), pageNum, pageSize);
    }
}
