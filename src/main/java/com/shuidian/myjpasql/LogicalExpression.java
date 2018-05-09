package com.shuidian.myjpasql;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangcheng
 * @Date: Created in 17:11 2018/4/11
 */
public class LogicalExpression implements Criterion {

    // 逻辑表达式中包含的表达式
    private Criterion[] criterion;
    // 计算符
    private Operator operator;

    public LogicalExpression(Criterion[] criterions, Operator operator) {
        this.criterion = criterions;
        this.operator = operator;
    }

    @Override
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> predicateList = new ArrayList<>();
        for (int i = 0; i < this.criterion.length; i++){
            predicateList.add(this.criterion[i].toPredicate(root, query, builder));
        }
        switch (operator){
            case OR:
                return builder.or(predicateList.toArray(new Predicate[predicateList.size()]));
            default:
                return null;
        }
    }
}
