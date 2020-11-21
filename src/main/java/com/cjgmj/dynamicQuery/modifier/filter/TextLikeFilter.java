package com.cjgmj.dynamicQuery.modifier.filter;

import com.cjgmj.dynamicQuery.predicate.QueryPredicate;
import com.cjgmj.dynamicQuery.predicate.TextLikePredicate;

public class TextLikeFilter extends TextFilter {

	public TextLikeFilter(String field, String value) {
		super(field, value);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new TextLikePredicate();
	}

}
