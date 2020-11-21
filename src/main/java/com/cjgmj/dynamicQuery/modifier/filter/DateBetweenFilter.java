package com.cjgmj.dynamicQuery.modifier.filter;

import java.time.LocalDate;

import com.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.cjgmj.dynamicQuery.predicate.DateBetweenPredicate;
import com.cjgmj.dynamicQuery.predicate.QueryPredicate;

import lombok.Getter;

@Getter
public class DateBetweenFilter extends ValueFilter<LocalDate> {

	private LocalDate nextValue;

	public DateBetweenFilter(String field, LocalDate value, LocalDate nextValue) {
		super(field, value);
		this.nextValue = nextValue;
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new DateBetweenPredicate();
	}

}
