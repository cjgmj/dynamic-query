package com.github.cjgmj.dynamicQuery.modifier.filter;

import java.time.LocalDate;

import com.github.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.github.cjgmj.dynamicQuery.predicate.DateEqualPredicate;
import com.github.cjgmj.dynamicQuery.predicate.QueryPredicate;

public class DateEqualFilter extends ValueFilter<LocalDate> {

	public DateEqualFilter(String field, LocalDate value) {
		super(field, value);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new DateEqualPredicate();
	}

}
