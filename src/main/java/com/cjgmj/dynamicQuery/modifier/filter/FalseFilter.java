package com.cjgmj.dynamicQuery.modifier.filter;

import com.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.cjgmj.dynamicQuery.predicate.FalsePredicate;
import com.cjgmj.dynamicQuery.predicate.QueryPredicate;

public class FalseFilter extends ValueFilter<Boolean> {

	public FalseFilter(String field) {
		super(field, Boolean.FALSE);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new FalsePredicate();
	}

}
