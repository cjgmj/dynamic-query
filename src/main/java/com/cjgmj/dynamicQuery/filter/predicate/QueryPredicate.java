package com.cjgmj.dynamicQuery.filter.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cjgmj.dynamicQuery.filter.FieldFilter;

@FunctionalInterface
public interface QueryPredicate {

	Predicate getPredicate(CriteriaBuilder cb, Root<?> root, FieldFilter filter);

}
