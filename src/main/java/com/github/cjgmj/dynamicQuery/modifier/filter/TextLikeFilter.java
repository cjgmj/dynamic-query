package com.github.cjgmj.dynamicquery.modifier.filter;

import com.github.cjgmj.dynamicquery.predicate.QueryPredicate;
import com.github.cjgmj.dynamicquery.predicate.TextLikePredicate;

public class TextLikeFilter extends TextFilter {

	public TextLikeFilter(String field, String value) {
		super(field, value);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new TextLikePredicate();
	}

}
