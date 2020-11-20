package com.cjgmj.dynamicQuery.filter;

import com.cjgmj.dynamicQuery.filter.predicate.QueryPredicate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class FieldFilter<T> {

	private String field;
	private T value;

	public abstract QueryPredicate getQueryPredicate();

}
