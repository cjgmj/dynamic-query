package com.cjgmj.dynamicQuery.predicate;

import java.time.LocalDate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.filter.DateLessThanFieldFilter;
import com.cjgmj.dynamicQuery.filter.FieldFilter;

public class DateLessThanPredicate implements QueryPredicate {

	@Override
	public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Expression<String> expression,
			FieldFilter<?> fieldFilter) {
		final DateLessThanFieldFilter dateLessThanFieldFilter = (DateLessThanFieldFilter) fieldFilter;

		if (dateLessThanFieldFilter.getOrEqual()) {
			return criteriaBuilder.lessThanOrEqualTo(expression.as(LocalDate.class),
					criteriaBuilder.literal(dateLessThanFieldFilter.getValue()));
		} else {
			return criteriaBuilder.lessThan(expression.as(LocalDate.class),
					criteriaBuilder.literal(dateLessThanFieldFilter.getValue()));
		}
	}
}
