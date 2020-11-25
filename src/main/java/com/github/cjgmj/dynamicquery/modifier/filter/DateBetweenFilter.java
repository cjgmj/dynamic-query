package com.github.cjgmj.dynamicquery.modifier.filter;

import java.time.LocalDate;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.predicate.DateBetweenPredicate;
import com.github.cjgmj.dynamicquery.predicate.QueryPredicate;

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
