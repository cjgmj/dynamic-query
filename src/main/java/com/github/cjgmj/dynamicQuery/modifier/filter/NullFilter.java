package com.github.cjgmj.dynamicQuery.modifier.filter;

import com.github.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.github.cjgmj.dynamicQuery.predicate.NullPredicate;
import com.github.cjgmj.dynamicQuery.predicate.QueryPredicate;

public class NullFilter extends ValueFilter<Void> {

	public NullFilter(String field) {
		super(field, null);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new NullPredicate();
	}

}
