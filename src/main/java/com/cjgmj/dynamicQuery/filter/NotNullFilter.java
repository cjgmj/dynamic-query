package com.cjgmj.dynamicQuery.filter;

import com.cjgmj.dynamicQuery.filter.predicate.NotNullPredicate;
import com.cjgmj.dynamicQuery.filter.predicate.QueryPredicate;

public class NotNullFilter extends FieldFilter {

	public NotNullFilter(String field) {
		super(field, null);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new NotNullPredicate();
	}

}
