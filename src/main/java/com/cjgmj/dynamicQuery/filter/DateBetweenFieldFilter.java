package com.cjgmj.dynamicQuery.filter;

import java.time.LocalDate;

import com.cjgmj.dynamicQuery.predicate.DateBetweenPredicate;
import com.cjgmj.dynamicQuery.predicate.QueryPredicate;

import lombok.Getter;

@Getter
public class DateBetweenFieldFilter extends FieldFilter<LocalDate> {

	private LocalDate nextValue;

	public DateBetweenFieldFilter(String field, LocalDate value, LocalDate nextValue) {
		super(field, value);
		this.nextValue = nextValue;
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new DateBetweenPredicate();
	}

}
