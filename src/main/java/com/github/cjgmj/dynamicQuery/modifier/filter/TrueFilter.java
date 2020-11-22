package com.github.cjgmj.dynamicquery.modifier.filter;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.predicate.QueryPredicate;
import com.github.cjgmj.dynamicquery.predicate.TruePredicate;

public class TrueFilter extends ValueFilter<Boolean> {

	public TrueFilter(String field) {
		super(field, Boolean.TRUE);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new TruePredicate();
	}

}
