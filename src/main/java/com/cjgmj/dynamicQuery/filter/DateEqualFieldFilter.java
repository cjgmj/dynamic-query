package com.cjgmj.dynamicQuery.filter;

import java.time.LocalDate;

import com.cjgmj.dynamicQuery.predicate.DateEqualPredicate;
import com.cjgmj.dynamicQuery.predicate.QueryPredicate;

public class DateEqualFieldFilter extends FieldFilter<LocalDate> {

	public DateEqualFieldFilter(String field, LocalDate value) {
		super(field, value);
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new DateEqualPredicate();
	}

}
