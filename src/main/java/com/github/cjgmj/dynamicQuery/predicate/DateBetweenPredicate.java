package com.github.cjgmj.dynamicquery.predicate;

import java.time.LocalDate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.github.cjgmj.dynamicquery.modifier.ValueFilter;
import com.github.cjgmj.dynamicquery.modifier.filter.DateBetweenFilter;

public class DateBetweenPredicate implements QueryPredicate {

	@Override
	public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Expression<String> expression,
			ValueFilter<?> valueFilter) {
		final DateBetweenFilter dateBetweenFieldFilter = (DateBetweenFilter) valueFilter;

		return criteriaBuilder.between(expression.as(LocalDate.class),
				criteriaBuilder.literal(dateBetweenFieldFilter.getValue()),
				criteriaBuilder.literal(dateBetweenFieldFilter.getNextValue()));
	}
}
