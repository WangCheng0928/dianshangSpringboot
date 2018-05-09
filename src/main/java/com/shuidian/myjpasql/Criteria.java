package com.shuidian.myjpasql;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangcheng
 * @Date: Created in 11:43 2018/4/11
 */

// 创建一个自定义的Specification，添加add方法用来添加多个条件
public class Criteria<T> implements Specification<T> {
    private List<Criterion> criterionList = new ArrayList<>();

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (!criterionList.isEmpty()){
            List<Predicate> predicateList = new ArrayList<>();
            for (Criterion criterion : criterionList){
                predicateList.add(criterion.toPredicate(root, criteriaQuery, criteriaBuilder));
            }
            // 将所有条件用and联合起来
            if (predicateList.size() > 0){
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
//                return criteriaBuilder.or(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        }
        return criteriaBuilder.conjunction();
//        return criteriaBuilder.disjunction();
    }

    // 增加简单条件表达式
    public void add(Criterion criterion){
        if (criterion != null){
            criterionList.add(criterion);
        }
    }
}
