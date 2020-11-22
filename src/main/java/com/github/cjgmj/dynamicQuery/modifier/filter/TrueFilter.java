package com.github.cjgmj.dynamicQuery.modifier.filter;

import com.github.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.github.cjgmj.dynamicQuery.predicate.QueryPredicate;
import com.github.cjgmj.dynamicQuery.predicate.TruePredicate;

public class TrueFilter extends ValueFilter<Boolean> {

	public TrueFilter(String field) {
		super(field, Boolean.TRUE);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new TruePredicate();
	}

}
