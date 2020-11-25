package com.github.cjgmj.dynamicquery.modifier.filter;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.predicate.NotNullPredicate;
import com.github.cjgmj.dynamicquery.predicate.QueryPredicate;

public class NotNullFilter extends ValueFilter<Void> {

	public NotNullFilter(String field) {
		super(field, null);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new NotNullPredicate();
	}

}
