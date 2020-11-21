package com.cjgmj.dynamicQuery.filter;

import com.cjgmj.dynamicQuery.predicate.NumberPredicate;
import com.cjgmj.dynamicQuery.predicate.QueryPredicate;

public class NumberFieldFilter extends FieldFilter<Number> {

	public NumberFieldFilter(String field, Number value) {
		super(field, value);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new NumberPredicate();
	}

}
