package com.shuidian.controller.user;

import com.shuidian.VO.CartVO;
import com.shuidian.domain.UserInfo;
import com.shuidian.enums.Const;
import com.shuidian.enums.ResponseCode;
import com.shuidian.service.ICartService;
import com.shuidian.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author: wangcheng
 * @Date: Created in 17:34 2018/4/22
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @RequestMapping("add")
    public ServerResponse<CartVO> add(HttpSession session, Integer count, Integer productId){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.add(userInfo.getId(), productId, count);
    }

    @RequestMapping("update")
    public ServerResponse<CartVO> update(HttpSession session, Integer count, Integer productId){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.update(userInfo.getId(), productId, count);
    }

    @RequestMapping("deleteProduct")
    public ServerResponse<CartVO> deleteProduct(HttpSession session, String productIds){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.deleteProduct(userInfo.getId(), productIds);
    }

    @RequestMapping("list")
    public ServerResponse<CartVO> list(HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.list(userInfo.getId());
    }

    //全选
    //全反选
    @RequestMapping("SelectAll")
    public ServerResponse<CartVO> SelectAll(HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectAllOrUnSelectAll(userInfo.getId(), Const.CartChecked.CHECKED);
    }

    @RequestMapping("UnSelectAll")
    public ServerResponse<CartVO> unSelectAll(HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectAllOrUnSelectAll(userInfo.getId(), Const.CartChecked.UNCHECKED);
    }

    // 单独选
    //单独反选
    @RequestMapping("Select")
    public ServerResponse<CartVO> Select(HttpSession session, Integer productId){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(userInfo.getId(), productId, Const.CartChecked.CHECKED);
    }

    @RequestMapping("unSelect")
    public ServerResponse<CartVO> unSelect(HttpSession session, Integer productId){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(userInfo.getId(), productId, Const.CartChecked.UNCHECKED);
    }

    @RequestMapping("getCartProductCount")
    public ServerResponse<Integer> getCartProductCount(HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRRENT_USER);
        if (userInfo == null){
            return ServerResponse.createBySuccess(0);
        }
        return iCartService.getCartProductCount(userInfo.getId());
    }
}
