package com.github.cjgmj.dynamicquery.modifier.filter;

import java.time.LocalDate;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.predicate.DateEqualPredicate;
import com.github.cjgmj.dynamicquery.predicate.QueryPredicate;

public class DateEqualFilter extends ValueFilter<LocalDate> {

	public DateEqualFilter(String field, LocalDate value) {
		super(field, value);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new DateEqualPredicate();
	}

}
