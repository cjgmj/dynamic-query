package com.cjgmj.dynamicQuery.predicate;

import java.time.LocalDate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.filter.DateGreaterThanFieldFilter;
import com.cjgmj.dynamicQuery.filter.FieldFilter;

public class DateGreaterThanPredicate implements QueryPredicate {

	@Override
	public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Expression<String> expression,
			FieldFilter<?> fieldFilter) {
		final DateGreaterThanFieldFilter dateGreaterThanFieldFilter = (DateGreaterThanFieldFilter) fieldFilter;

		if (dateGreaterThanFieldFilter.getOrEqual()) {
			return criteriaBuilder.greaterThanOrEqualTo(expression.as(LocalDate.class),
					criteriaBuilder.literal(dateGreaterThanFieldFilter.getValue()));
		} else {
			return criteriaBuilder.greaterThan(expression.as(LocalDate.class),
					criteriaBuilder.literal(dateGreaterThanFieldFilter.getValue()));
		}
	}
}
