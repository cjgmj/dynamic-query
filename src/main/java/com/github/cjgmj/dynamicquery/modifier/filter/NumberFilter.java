package com.github.cjgmj.dynamicquery.modifier.filter;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.predicate.NumberPredicate;
import com.github.cjgmj.dynamicquery.predicate.QueryPredicate;

public class NumberFilter extends ValueFilter<Number> {

	public NumberFilter(String field, Number value) {
		super(field, value);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new NumberPredicate();
	}

}
