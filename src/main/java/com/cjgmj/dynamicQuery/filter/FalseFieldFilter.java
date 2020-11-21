package com.cjgmj.dynamicQuery.filter;

import com.cjgmj.dynamicQuery.predicate.FalsePredicate;
import com.cjgmj.dynamicQuery.predicate.QueryPredicate;

public class FalseFieldFilter extends FieldFilter<Boolean> {

	public FalseFieldFilter(String field) {
		super(field, Boolean.FALSE);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new FalsePredicate();
	}

}
