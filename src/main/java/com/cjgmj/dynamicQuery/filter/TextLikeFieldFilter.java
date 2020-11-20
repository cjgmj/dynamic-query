package com.cjgmj.dynamicQuery.filter;

import com.cjgmj.dynamicQuery.filter.predicate.QueryPredicate;
import com.cjgmj.dynamicQuery.filter.predicate.TextLikePredicate;

public class TextLikeFieldFilter extends TextFieldFilter {

	public TextLikeFieldFilter(String field, String value) {
		super(field, value);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new TextLikePredicate();
	}

}
