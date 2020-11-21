package com.cjgmj.dynamicQuery.predicate;

import java.time.LocalDate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.modifier.ValueFilter;
import com.cjgmj.dynamicQuery.modifier.filter.DateBetweenFilter;

public class DateBetweenPredicate implements QueryPredicate {

	@Override
	public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Expression<String> expression,
			ValueFilter<?> fieldFilter) {
		final DateBetweenFilter dateBetweenFieldFilter = (DateBetweenFilter) fieldFilter;

		return criteriaBuilder.between(expression.as(LocalDate.class),
				criteriaBuilder.literal(dateBetweenFieldFilter.getValue()),
				criteriaBuilder.literal(dateBetweenFieldFilter.getNextValue()));
	}
}