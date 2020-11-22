package com.github.cjgmj.dynamicquery.modifier.filter;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.predicate.NullPredicate;
import com.github.cjgmj.dynamicquery.predicate.QueryPredicate;

public class NullFilter extends ValueFilter<Void> {

	public NullFilter(String field) {
		super(field, null);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new NullPredicate();
	}

}
