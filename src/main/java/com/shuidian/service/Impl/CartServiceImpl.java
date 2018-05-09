package com.shuidian.service.Impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.shuidian.VO.CartProductVO;
import com.shuidian.VO.CartVO;
import com.shuidian.domain.Cart;
import com.shuidian.domain.Product;
import com.shuidian.enums.Const;
import com.shuidian.enums.ResponseCode;
import com.shuidian.repository.CartRepository;
import com.shuidian.repository.ProductRepository;
import com.shuidian.service.ICartService;
import com.shuidian.util.BigDecimalUtil;
import com.shuidian.util.PropertiesUtil;
import com.shuidian.util.ServerResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: wangcheng
 * @Date: Created in 10:00 2018/4/23
 */
@Service("iCartService")
public class CartServiceImpl implements ICartService{

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public ServerResponse<CartVO> add(Integer userId, Integer productId, Integer count){
        if (productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGA_ARGUMENT.getCode(), ResponseCode.ILLEGA_ARGUMENT.getDesc());
        }
        Cart cart = cartRepository.findByUserIdAndProductId(userId, productId);
        if (cart == null){
            //这个产品不在这个购物车里，需要新增一个这个产品的记录
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.CartChecked.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            cartRepository.save(cartItem);
        }else {
            // 这个产品已经在购物车里了
            // 如果产品已存在。数量相加
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
//            cartRepository.updateCountByCartId(count, cart.getId());
            cartRepository.save(cart);
        }
        return this.list(userId);
    }

    private CartVO getCartVOLimit(Integer userId){
        CartVO cartVO = new CartVO();
        List<Cart> cartList = cartRepository.findByUserId(userId);
        List<CartProductVO> cartProductVOList = Lists.newArrayList();
        BigDecimal cartTotalPrice = new BigDecimal("0");
        if (CollectionUtils.isNotEmpty(cartList)){
            for (Cart cartItem : cartList){
                CartProductVO cartProductVO = new CartProductVO();
                cartProductVO.setId(cartItem.getId());
                cartProductVO.setUserId(userId);
                cartProductVO.setProductId(cartItem.getProductId());
                Product product = productRepository.findOne(cartItem.getProductId());
                if (product != null){
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductSubtitle(product.getSubtitle());
                    cartProductVO.setProductStatus(product.getStatus());
                    cartProductVO.setProductPrice(product.getPrice());
                    cartProductVO.setProductStock(product.getStock());
                    // 判断库存
                    int buyLimitCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()){
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVO.setLimitQuantity(Const.CartChecked.LIMIT_COUNT_SUCCESS);
                    }else {
                        buyLimitCount = product.getStock();
                        cartProductVO.setLimitQuantity(Const.CartChecked.LIMIT_COUNT_FAILURE);
                        //购物车中更新有效库存
                        cartRepository.updateCountByCartId(buyLimitCount, cartItem.getId());
                    }
                    cartProductVO.setQuantity(buyLimitCount);
                    // 计算总价
                    cartProductVO.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVO.getQuantity()));
                    cartProductVO.setProductChecked(cartItem.getChecked());
                    if (cartItem.getChecked() == Const.CartChecked.CHECKED){
                        // 如果已经勾选，增加到整个的购物车总价中
                        cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVO.getProductTotalPrice().doubleValue());
                    }
                    cartProductVOList.add(cartProductVO);
                }
            }
        }
        cartVO.setCartTotalPrice(cartTotalPrice);
        cartVO.setCartProductVOList(cartProductVOList);
        cartVO.setAllChecked(this.getAllCheckedStatus(userId));
        cartVO.setImageHost(PropertiesUtil.getProperty(Const.FTP_PREFIX_KEY));
        return cartVO;
    }

    private boolean getAllCheckedStatus(Integer userId){
        if (userId == null){
            return false;
        }
        return cartRepository.findCartProductCheckedStatusByUserId(userId) == 0;
    }

    public ServerResponse<CartVO> update(Integer userId, Integer productId, Integer count){
        if (productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGA_ARGUMENT.getCode(), ResponseCode.ILLEGA_ARGUMENT.getDesc());
        }
        Cart cart = cartRepository.findByUserIdAndProductId(userId, productId);
        if (cart != null){
            cart.setQuantity(count);
        }
        cartRepository.save(cart);
        return this.list(userId);
    }

    public ServerResponse<CartVO> deleteProduct(Integer userId, String productIds){
        List<String> productIdList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productIdList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGA_ARGUMENT.getCode(), ResponseCode.ILLEGA_ARGUMENT.getDesc());
        }
        for (String productIdItem : productIdList){
            Integer productId = Integer.parseInt(productIdItem);
            cartRepository.deleteByProductIdAndUserId(productId, userId);
        }
        return this.list(userId);
    }

    public ServerResponse<CartVO> list(Integer userId){
        CartVO cartVO = this.getCartVOLimit(userId);
        return ServerResponse.createBySuccess(cartVO);
    }

    public ServerResponse<CartVO> selectAllOrUnSelectAll(Integer userId, Integer checked){
        cartRepository.CheckedAllOrUnCheckedAllByUserId(checked, userId);
        return this.list(userId);
    }

    public ServerResponse<CartVO> selectOrUnSelect(Integer userId, Integer productId, Integer checked){
        cartRepository.CheckedOrUnCheckedByUserIdAndProductId(checked, userId, productId);
        return this.list(userId);
    }

    //因为不知道怎么用sum查询，所以直接循环相加
    public ServerResponse<Integer> getCartProductCount(Integer userId){
        if (userId == null){
            return ServerResponse.createBySuccess(0);
        }
        CartVO cartVO = this.getCartVOLimit(userId);
        List<CartProductVO> cartProductVOList = cartVO.getCartProductVOList();
        Integer result = 0;
        for (CartProductVO cartProductVO : cartProductVOList){
            result += cartProductVO.getQuantity();
        }
        return ServerResponse.createBySuccess(result);
    }
}
