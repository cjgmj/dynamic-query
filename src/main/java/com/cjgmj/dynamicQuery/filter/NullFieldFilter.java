package com.cjgmj.dynamicQuery.filter;

import com.cjgmj.dynamicQuery.filter.predicate.NullPredicate;
import com.cjgmj.dynamicQuery.filter.predicate.QueryPredicate;

public class NullFieldFilter extends FieldFilter<Void> {

	public NullFieldFilter(String field) {
		super(field, null);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new NullPredicate();
	}

}
