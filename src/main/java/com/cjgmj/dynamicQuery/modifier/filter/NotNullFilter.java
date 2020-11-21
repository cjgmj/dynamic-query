package com.cjgmj.dynamicQuery.modifier.filter;

import com.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.cjgmj.dynamicQuery.predicate.NotNullPredicate;
import com.cjgmj.dynamicQuery.predicate.QueryPredicate;

public class NotNullFilter extends ValueFilter<Void> {

	public NotNullFilter(String field) {
		super(field, null);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new NotNullPredicate();
	}

}
