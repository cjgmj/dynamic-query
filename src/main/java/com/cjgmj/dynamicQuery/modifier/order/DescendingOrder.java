package com.cjgmj.dynamicQuery.modifier.order;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.modifier.FieldOrder;

public class DescendingOrder extends FieldOrder {

	public DescendingOrder(String field) {
		super(field);
	}

	@Override
	public Order getOrder(CriteriaBuilder criteriaBuilder, Root<?> root) {
		return criteriaBuilder.desc(this.getExpression(root));
	}

}
