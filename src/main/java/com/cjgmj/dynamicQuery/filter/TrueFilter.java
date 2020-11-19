package com.cjgmj.dynamicQuery.filter;

import com.cjgmj.dynamicQuery.filter.predicate.QueryPredicate;
import com.cjgmj.dynamicQuery.filter.predicate.TruePredicate;

public class TrueFilter extends FieldFilter {

	public TrueFilter(String field) {
		super(field, null);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new TruePredicate();
	}

}
