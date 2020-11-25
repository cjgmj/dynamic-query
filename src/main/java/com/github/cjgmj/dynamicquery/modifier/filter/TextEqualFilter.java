package com.github.cjgmj.dynamicquery.modifier.filter;

import com.github.cjgmj.dynamicquery.predicate.QueryPredicate;
import com.github.cjgmj.dynamicquery.predicate.TextEqualPredicate;

public class TextEqualFilter extends TextFilter {

	public TextEqualFilter(String field, String value) {
		super(field, value);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new TextEqualPredicate();
	}

}
