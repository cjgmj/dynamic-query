package com.cjgmj.dynamicQuery.modifier.order;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.modifier.FieldOrder;

public class AscendingOrder extends FieldOrder {

	public AscendingOrder(String field) {
		super(field);
	}

	@Override
	public Order getOrder(CriteriaBuilder criteriaBuilder, Root<?> root) {
		return criteriaBuilder.asc(this.getExpression(root));
	}

}
