package com.cjgmj.dynamicQuery.filter;

import com.cjgmj.dynamicQuery.filter.predicate.QueryPredicate;
import com.cjgmj.dynamicQuery.filter.predicate.TextEqualPredicate;

public class TextEqualFieldFilter extends FieldFilter {

	public TextEqualFieldFilter(String field, String value) {
		super(field, value);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new TextEqualPredicate();
	}

}
