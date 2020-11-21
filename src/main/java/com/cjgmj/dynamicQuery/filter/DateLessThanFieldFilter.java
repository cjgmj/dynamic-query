package com.cjgmj.dynamicQuery.filter;

import java.time.LocalDate;

import com.cjgmj.dynamicQuery.predicate.DateLessThanPredicate;
import com.cjgmj.dynamicQuery.predicate.QueryPredicate;

import lombok.Getter;

@Getter
public class DateLessThanFieldFilter extends FieldFilter<LocalDate> {

	private Boolean orEqual;

	public DateLessThanFieldFilter(String field, LocalDate value) {
		super(field, value);
		this.orEqual = Boolean.FALSE;
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new DateLessThanPredicate();
	}

	public DateLessThanFieldFilter orEqual() {
		this.orEqual = Boolean.TRUE;

		return this;
	}

}
