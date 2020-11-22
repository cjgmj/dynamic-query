package com.github.cjgmj.dynamicQuery.modifier.filter;

import java.time.LocalDate;

import com.github.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.github.cjgmj.dynamicQuery.predicate.DateLessThanPredicate;
import com.github.cjgmj.dynamicQuery.predicate.QueryPredicate;

import lombok.Getter;

@Getter
public class DateLessThanFilter extends ValueFilter<LocalDate> {

	private Boolean orEqual;

	public DateLessThanFilter(String field, LocalDate value) {
		super(field, value);
		this.orEqual = Boolean.FALSE;
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new DateLessThanPredicate();
	}

	public DateLessThanFilter orEqual() {
		this.orEqual = Boolean.TRUE;

		return this;
	}

}
