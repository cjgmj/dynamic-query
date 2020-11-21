package com.cjgmj.dynamicQuery.modifier;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import lombok.Getter;

@Getter
public abstract class FieldOrder extends FieldReference {

	public FieldOrder(String field) {
		super(field);
	}

	public abstract Order getOrder(CriteriaBuilder criteriaBuilder, Root<?> root);

}
