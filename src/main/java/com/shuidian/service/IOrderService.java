package com.shuidian.service;

import com.github.pagehelper.PageInfo;
import com.shuidian.VO.OrderVo;
import com.shuidian.util.ServerResponse;

import java.util.Map;

/**
 * @Author: wangcheng
 * @Date: Created in 17:35 2018/4/26
 */
public interface IOrderService {

    ServerResponse pay(Long orderNo, Integer userId, String path);
    ServerResponse alipayCallback(Map<String, String> params);
    ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);
    ServerResponse createOrder(Integer userId, Integer shippingId);
    ServerResponse<String> cancel(Integer userId, Long orderNo);
    ServerResponse getOrderCartProduct(Integer userId);
    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);
    ServerResponse<PageInfo> getOrderList(Integer userId, Integer pageNum, Integer pageSize);

    ServerResponse<PageInfo> manageList(Integer pageNum, Integer pageSize);
    ServerResponse<OrderVo> manageDetail(Long orderNo);
    ServerResponse<PageInfo> manageSearch(Long orderNo, Integer pageNum, Integer pageSize);
    ServerResponse<String> manageSendGoods(Long orderNo);
}
