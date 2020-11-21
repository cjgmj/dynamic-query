package com.cjgmj.dynamicQuery.modifier.filter;

import com.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.cjgmj.dynamicQuery.predicate.QueryPredicate;
import com.cjgmj.dynamicQuery.predicate.TruePredicate;

public class TrueFilter extends ValueFilter<Boolean> {

	public TrueFilter(String field) {
		super(field, Boolean.TRUE);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new TruePredicate();
	}

}
