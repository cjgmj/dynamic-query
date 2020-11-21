package com.cjgmj.dynamicQuery.predicate;

import java.time.LocalDate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.cjgmj.dynamicQuery.modifier.filter.DateLessThanFilter;

public class DateLessThanPredicate implements QueryPredicate {

	@Override
	public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Expression<String> expression,
			ValueFilter<?> fieldFilter) {
		final DateLessThanFilter dateLessThanFieldFilter = (DateLessThanFilter) fieldFilter;

		if (dateLessThanFieldFilter.getOrEqual()) {
			return criteriaBuilder.lessThanOrEqualTo(expression.as(LocalDate.class),
					criteriaBuilder.literal(dateLessThanFieldFilter.getValue()));
		} else {
			return criteriaBuilder.lessThan(expression.as(LocalDate.class),
					criteriaBuilder.literal(dateLessThanFieldFilter.getValue()));
		}
	}
}
