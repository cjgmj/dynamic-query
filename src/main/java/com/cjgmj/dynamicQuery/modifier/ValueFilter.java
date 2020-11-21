package com.cjgmj.dynamicQuery.modifier;

import com.cjgmj.dynamicQuery.predicate.QueryPredicate;

import lombok.Getter;

@Getter
public abstract class ValueFilter<T> extends FieldReference {
	private T value;

	public ValueFilter(String field, T value) {
		super(field);
		this.value = value;
	}

	public abstract QueryPredicate getQueryPredicate();

}
