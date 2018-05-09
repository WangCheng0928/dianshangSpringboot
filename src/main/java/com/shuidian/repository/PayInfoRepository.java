package com.shuidian.repository;

import com.shuidian.domain.PayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: wangcheng
 * @Date: Created in 16:21 2018/5/3
 */
public interface PayInfoRepository extends JpaRepository<PayInfo, Integer> {
}
