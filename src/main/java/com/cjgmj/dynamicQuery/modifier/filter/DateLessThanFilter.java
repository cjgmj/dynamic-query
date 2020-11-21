package com.cjgmj.dynamicQuery.modifier.filter;

import java.time.LocalDate;

import com.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.cjgmj.dynamicQuery.predicate.DateLessThanPredicate;
import com.cjgmj.dynamicQuery.predicate.QueryPredicate;

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
