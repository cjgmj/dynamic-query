package com.github.cjgmj.dynamicQuery.modifier.filter;

import com.github.cjgmj.dynamicQuery.predicate.QueryPredicate;
import com.github.cjgmj.dynamicQuery.predicate.TextEqualPredicate;

public class TextEqualFilter extends TextFilter {

	public TextEqualFilter(String field, String value) {
		super(field, value);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new TextEqualPredicate();
	}

}