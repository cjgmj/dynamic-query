package com.cjgmj.dynamicQuery.filter;

import java.time.LocalDate;

import com.cjgmj.dynamicQuery.predicate.DateGreaterThanPredicate;
import com.cjgmj.dynamicQuery.predicate.QueryPredicate;

import lombok.Getter;

@Getter
public class DateGreaterThanFieldFilter extends FieldFilter<LocalDate> {

	private Boolean orEqual;

	public DateGreaterThanFieldFilter(String field, LocalDate value) {
		super(field, value);
		this.orEqual = Boolean.FALSE;
	}

	@Override
	public QueryPredicate getQueryPredicate() {
		return new DateGreaterThanPredicate();
	}

	public DateGreaterThanFieldFilter orEqual() {
		this.orEqual = Boolean.TRUE;

		return this;
	}

}
