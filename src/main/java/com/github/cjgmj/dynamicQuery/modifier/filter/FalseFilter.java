package com.github.cjgmj.dynamicquery.modifier.filter;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.predicate.FalsePredicate;
import com.github.cjgmj.dynamicquery.predicate.QueryPredicate;

public class FalseFilter extends ValueFilter<Boolean> {

	public FalseFilter(String field) {
		super(field, Boolean.FALSE);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new FalsePredicate();
	}

}
