package com.cjgmj.dynamicQuery.modifier.filter;

import com.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.cjgmj.dynamicQuery.predicate.NullPredicate;
import com.cjgmj.dynamicQuery.predicate.QueryPredicate;

public class NullFilter extends ValueFilter<Void> {

	public NullFilter(String field) {
		super(field, null);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new NullPredicate();
	}

}
