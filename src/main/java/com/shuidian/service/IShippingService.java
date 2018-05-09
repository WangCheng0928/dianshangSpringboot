package com.shuidian.service;

import com.github.pagehelper.PageInfo;
import com.shuidian.domain.Shipping;
import com.shuidian.util.ServerResponse;

/**
 * @Author: wangcheng
 * @Date: Created in 19:31 2018/4/23
 */
public interface IShippingService {

    ServerResponse add(Integer userId, Shipping shipping);
    ServerResponse<String> del(Integer userId, Integer shippingId);
    ServerResponse<String> update(Integer userId, Shipping shipping);
    ServerResponse<Shipping> select(Integer userId, Integer shippingId);
    ServerResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize);
}
