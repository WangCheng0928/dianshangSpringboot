package com.shuidian.service;

import com.shuidian.VO.CartVO;
import com.shuidian.util.ServerResponse;

/**
 * @Author: wangcheng
 * @Date: Created in 9:58 2018/4/23
 */
public interface ICartService {
    ServerResponse<CartVO> add(Integer userId, Integer productId, Integer count);
    ServerResponse<CartVO> update(Integer userId, Integer productId, Integer count);
    ServerResponse<CartVO> deleteProduct(Integer userId, String productIds);
    ServerResponse<CartVO> list(Integer userId);
    ServerResponse<CartVO> selectAllOrUnSelectAll(Integer userId, Integer checked);
    ServerResponse<CartVO> selectOrUnSelect(Integer userId, Integer productId, Integer checked);
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
