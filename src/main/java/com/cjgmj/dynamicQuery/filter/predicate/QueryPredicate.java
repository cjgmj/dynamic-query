package com.cjgmj.dynamicQuery.filter.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.filter.FieldFilter;

@FunctionalInterface
public interface QueryPredicate {

	Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, Expression<String> expression,
			FieldFilter<?> fieldFilter);

	default Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, FieldFilter<?> fieldFilter) {
		Expression<String> expression = null;

		final String[] relatedAttribute = fieldFilter.getField().split("[.]");

		if (relatedAttribute.length == 1) {
			expression = root.get(fieldFilter.getField());
		} else {
			Join<Object, Object> join = root.join(relatedAttribute[0]);
			for (int i = 1; i < relatedAttribute.length - 1; i++) {
				join = join.join(relatedAttribute[i]);
			}
			expression = join.get(relatedAttribute[relatedAttribute.length - 1]);
		}

		return this.buildPredicate(criteriaBuilder, root, expression, fieldFilter);
	}

}
