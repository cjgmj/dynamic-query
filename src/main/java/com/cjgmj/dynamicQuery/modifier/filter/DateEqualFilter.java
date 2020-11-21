package com.cjgmj.dynamicQuery.modifier.filter;

import java.time.LocalDate;

import com.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.cjgmj.dynamicQuery.predicate.DateEqualPredicate;
import com.cjgmj.dynamicQuery.predicate.QueryPredicate;

public class DateEqualFilter extends ValueFilter<LocalDate> {

	public DateEqualFilter(String field, LocalDate value) {
		super(field, value);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new DateEqualPredicate();
	}

}
