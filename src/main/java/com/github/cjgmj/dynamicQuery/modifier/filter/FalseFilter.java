package com.github.cjgmj.dynamicQuery.modifier.filter;

import com.github.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.github.cjgmj.dynamicQuery.predicate.FalsePredicate;
import com.github.cjgmj.dynamicQuery.predicate.QueryPredicate;

public class FalseFilter extends ValueFilter<Boolean> {

	public FalseFilter(String field) {
		super(field, Boolean.FALSE);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new FalsePredicate();
	}

}
