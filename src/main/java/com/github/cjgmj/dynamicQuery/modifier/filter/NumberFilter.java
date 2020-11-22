package com.github.cjgmj.dynamicQuery.modifier.filter;

import com.github.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.github.cjgmj.dynamicQuery.predicate.NumberPredicate;
import com.github.cjgmj.dynamicQuery.predicate.QueryPredicate;

public class NumberFilter extends ValueFilter<Number> {

	public NumberFilter(String field, Number value) {
		super(field, value);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new NumberPredicate();
	}

}
