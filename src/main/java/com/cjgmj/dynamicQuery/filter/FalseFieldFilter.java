package com.cjgmj.dynamicQuery.filter;

import com.cjgmj.dynamicQuery.filter.predicate.FalsePredicate;
import com.cjgmj.dynamicQuery.filter.predicate.QueryPredicate;

public class FalseFieldFilter extends FieldFilter {

	public FalseFieldFilter(String field) {
		super(field, null);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new FalsePredicate();
	}

}
