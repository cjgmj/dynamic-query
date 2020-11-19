package com.cjgmj.dynamicQuery.filter;

import com.cjgmj.dynamicQuery.filter.predicate.QueryPredicate;
import com.cjgmj.dynamicQuery.filter.predicate.TruePredicate;

public class TrueFieldFilter extends FieldFilter {

	public TrueFieldFilter(String field) {
		super(field, null);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new TruePredicate();
	}

}
