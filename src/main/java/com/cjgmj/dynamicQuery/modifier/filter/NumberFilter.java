package com.cjgmj.dynamicQuery.modifier.filter;

import com.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.cjgmj.dynamicQuery.predicate.NumberPredicate;
import com.cjgmj.dynamicQuery.predicate.QueryPredicate;

public class NumberFilter extends ValueFilter<Number> {

	public NumberFilter(String field, Number value) {
		super(field, value);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new NumberPredicate();
	}

}
