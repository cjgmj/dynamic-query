package com.cjgmj.dynamicQuery.filter;

import com.cjgmj.dynamicQuery.predicate.QueryPredicate;
import com.cjgmj.dynamicQuery.predicate.TextEqualPredicate;

public class TextEqualFieldFilter extends TextFieldFilter {

	public TextEqualFieldFilter(String field, String value) {
		super(field, value);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new TextEqualPredicate();
	}

}
