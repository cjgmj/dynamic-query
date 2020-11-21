package com.cjgmj.dynamicQuery.filter;

import com.cjgmj.dynamicQuery.predicate.NotNullPredicate;
import com.cjgmj.dynamicQuery.predicate.QueryPredicate;

public class NotNullFieldFilter extends FieldFilter<Void> {

	public NotNullFieldFilter(String field) {
		super(field, null);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new NotNullPredicate();
	}

}
