package com.github.cjgmj.dynamicquery.predicate;

import java.time.LocalDate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.modifier.filter.DateLessThanFilter;

public class DateLessThanPredicate implements QueryPredicate {

	@Override
	public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Expression<String> expression,
			ValueFilter<?> valueFilter) {
		final DateLessThanFilter dateLessThanValueFilter = (DateLessThanFilter) valueFilter;

		if (dateLessThanValueFilter.getOrEqual()) {
			return criteriaBuilder.lessThanOrEqualTo(expression.as(LocalDate.class),
					criteriaBuilder.literal(dateLessThanValueFilter.getValue()));
		} else {
			return criteriaBuilder.lessThan(expression.as(LocalDate.class),
					criteriaBuilder.literal(dateLessThanValueFilter.getValue()));
		}
	}
}
