package com.github.cjgmj.dynamicQuery.modifier.filter;

import java.time.LocalDate;

import com.github.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.github.cjgmj.dynamicQuery.predicate.DateGreaterThanPredicate;
import com.github.cjgmj.dynamicQuery.predicate.QueryPredicate;

import lombok.Getter;

@Getter
public class DateGreaterThanFilter extends ValueFilter<LocalDate> {

	private Boolean orEqual;

	public DateGreaterThanFilter(String field, LocalDate value) {
		super(field, value);
		this.orEqual = Boolean.FALSE;
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new DateGreaterThanPredicate();
	}

	public DateGreaterThanFilter orEqual() {
		this.orEqual = Boolean.TRUE;

		return this;
	}

}
