package com.github.cjgmj.dynamicquery.modifier.filter;

import java.time.LocalDate;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.predicate.DateLessThanPredicate;
import com.github.cjgmj.dynamicquery.predicate.QueryPredicate;

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
