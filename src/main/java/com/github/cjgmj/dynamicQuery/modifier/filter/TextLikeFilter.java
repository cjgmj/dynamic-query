package com.github.cjgmj.dynamicQuery.modifier.filter;

import com.github.cjgmj.dynamicQuery.predicate.QueryPredicate;
import com.github.cjgmj.dynamicQuery.predicate.TextLikePredicate;

public class TextLikeFilter extends TextFilter {

	public TextLikeFilter(String field, String value) {
		super(field, value);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new TextLikePredicate();
	}

}
