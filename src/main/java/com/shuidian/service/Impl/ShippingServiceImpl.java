package com.shuidian.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.shuidian.domain.Shipping;
import com.shuidian.repository.ShippingRepository;
import com.shuidian.service.IShippingService;
import com.shuidian.util.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangcheng
 * @Date: Created in 19:31 2018/4/23
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    public ServerResponse add(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        shipping.setCreateTime(new Date());
        Shipping CurrentShipping = shippingRepository.save(shipping);
        Map result = Maps.newHashMap();
        result.put("shippingId", CurrentShipping.getId());
        return ServerResponse.createBySuccess("新建地址成功", result);
    }

    public ServerResponse<String> del(Integer userId, Integer shippingId){
        int resultCount = shippingRepository.deleteByIdAndUserId(shippingId, userId);
        if (resultCount > 0){
            return ServerResponse.createBySuccessMessage("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");
    }

    //待更改
    public ServerResponse<String> update(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        Shipping resultCount = shippingRepository.save(shipping);
        if (resultCount != null){
            return ServerResponse.createBySuccessMessage("更新地址成功");
        }
        return ServerResponse.createByErrorMessage("更新地址失败");
    }

    public ServerResponse<Shipping> select(Integer userId, Integer shippingId){
        Shipping shipping = shippingRepository.findByIdAndUserId(shippingId, userId);
        if (shipping == null){
            return ServerResponse.createByErrorMessage("无法查询到该地址");
        }
        return ServerResponse.createBySuccess("查询地址成功", shipping);
    }

    public ServerResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingRepository.findByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
