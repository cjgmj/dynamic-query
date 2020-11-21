package com.cjgmj.dynamicQuery.filter;

import com.cjgmj.dynamicQuery.predicate.QueryPredicate;
import com.cjgmj.dynamicQuery.predicate.TruePredicate;

public class TrueFieldFilter extends FieldFilter<Boolean> {

	public TrueFieldFilter(String field) {
		super(field, Boolean.TRUE);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new TruePredicate();
	}

}
