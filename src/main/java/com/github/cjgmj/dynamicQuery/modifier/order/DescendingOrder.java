package com.github.cjgmj.dynamicquery.modifier.order;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import com.github.cjgmj.dynamicquery.modifier.FieldOrder;

public class DescendingOrder extends FieldOrder {

	public DescendingOrder(String field) {
		super(field);
	}

	@Override
	public Order getOrder(CriteriaBuilder criteriaBuilder, Root<?> root) {
		return criteriaBuilder.desc(this.getExpression(root));
	}

}
