package com.github.cjgmj.dynamicQuery.modifier.filter;

import com.github.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.github.cjgmj.dynamicQuery.predicate.NotNullPredicate;
import com.github.cjgmj.dynamicQuery.predicate.QueryPredicate;

public class NotNullFilter extends ValueFilter<Void> {

	public NotNullFilter(String field) {
		super(field, null);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new NotNullPredicate();
	}

}
