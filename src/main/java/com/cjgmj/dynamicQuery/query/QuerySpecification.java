package com.cjgmj.dynamicQuery.query;

import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.cjgmj.dynamicQuery.filter.FieldFilter;

@Component
public class QuerySpecification<T> {

	public Specification<T> fieldFilter(List<FieldFilter> fieldFilters) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			final List<Predicate> predicatesList = Collections.emptyList();

			fieldFilters.forEach(filter -> {
				predicatesList.add(filter.getQueryPredicate().getPredicate(criteriaBuilder, root, filter));
			});

			return predicatesList.isEmpty() ? null
					: criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
		};
	}

}
